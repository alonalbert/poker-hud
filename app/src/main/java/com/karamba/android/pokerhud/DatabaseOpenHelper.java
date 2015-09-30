package com.karamba.android.pokerhud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

  private static final String UPGRADE_METHOD_PREFIX = "upgradeFrom";

  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "pokerhud.db";
  private final Context context;

  private static DatabaseOpenHelper instance;

  public static DatabaseOpenHelper getInstance(Context context) {
    if (instance == null) {
      synchronized (DatabaseOpenHelper.class) {
        if (instance == null) {
          instance = new DatabaseOpenHelper(context.getApplicationContext());
        }
      }
    }
    return instance;
  }

  private DatabaseOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  public int getLastHandNumber() {
    try (SQLiteDatabase db = getReadableDatabase()){
      try (Cursor cursor = db.rawQuery("SELECT MAX(hand) FROM hands", null)) {
        if (cursor == null || !cursor.moveToNext()) {
          return 0;
        }
        return cursor.getInt(0);
      }
    }
  }

  public List<Player> getPlayers(int hand) {
    try (SQLiteDatabase db = getReadableDatabase()){
      final String handStr = String.valueOf(hand);
      final ArrayList<Player> players = new ArrayList<>();
      try (Cursor cursor = db.rawQuery(
          "SELECT " +
              "player, " +
              "name, " +
              "seat, " +
              "active, " +
              "act, " +
              "(SELECT COUNT(*) FROM hands WHERE hands.player = players._id AND active = 1 AND hand <= ?) AS hands, " +
              "(SELECT COUNT(*) FROM hands WHERE hands.player = players._id AND active = 1 AND act = 2 AND hand <= ?) AS raised, " +
              "(SELECT COUNT(*) FROM hands WHERE hands.player = players._id AND active = 1 AND act = 1 AND hand <= ?) AS called " +
              "FROM hands JOIN players ON player = players._id " +
              "WHERE hand=? " +
              "ORDER BY seat",
          new String[] {handStr, handStr, handStr, handStr})) {
        if (cursor != null) {
          while (cursor.moveToNext()) {
            final int playerId = cursor.getInt(0);
            final String playerName = cursor.getString(1);
            final int seat = cursor.getInt(2);
            final int active = cursor.getInt(3);
            final int action = cursor.getInt(4);
            final int hands = cursor.getInt(5);
            final int raised = cursor.getInt(6);
            final int called = cursor.getInt(7);

            players.add(new Player(playerName, playerId, hands, seat, active, called, raised, action));
          }
        }
        return players;
      }
    }
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final BufferedReader in = new BufferedReader(
        new InputStreamReader(context.getResources().openRawResource(R.raw.schema)));
    try {
      final StringBuilder sb = new StringBuilder();
      for (String line = in.readLine(); line != null; line = in.readLine()) {
        if (TextUtils.isEmpty(line.trim())) {
          db.execSQL(sb.toString());
          sb.setLength(0);
        } else {
          sb.append(line).append("\n");
        }
      }
      db.execSQL(sb.toString());
    } catch (IOException e) {
      LogUtils.e("Failed to read schema", e);
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        // ignore
      }
    }
  }

  /**
   * To add an upgrade path, create a method called upgradeFrom&lt;oldVersion&gt;
   * If the method returns void, the
   * version after the update will be oldVersion+1. If it returns int, the new version will be the
   * returned value.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    LogUtils.i("Upgrading database from version %d to %d", oldVersion, newVersion);
    checkUpgradeMethods();

    // Use reflection to find upgrade methods. Upgrade methods are named "upgradeFromXXX().
    // The method can return a version number for the resulting database or be a void method
    // if the resulting method is the old version + 1
    final Class<? extends DatabaseOpenHelper> thisClass = getClass();
    int from = oldVersion;
    while (true) {
      final int to;
      try {
        final Method upgradeMethod = thisClass.getMethod(
            UPGRADE_METHOD_PREFIX + from, SQLiteDatabase.class);
        if (upgradeMethod.getReturnType().equals(void.class)) {
          upgradeMethod.invoke(this, db);
          to = from + 1;
        } else {
          to = (Integer) upgradeMethod.invoke(this, db);
        }
      } catch (final InvocationTargetException e) {
        final Throwable cause = e.getCause();
        if (cause == null) {
          throw new IllegalStateException("Invocation failed with null cause.", e);
        }
        if (cause instanceof RuntimeException) {
          throw (RuntimeException) cause;
        }
        throw new IllegalStateException("Failed to invoke upgrade Method", cause);
      } catch (final IllegalAccessException e) {
        throw new IllegalStateException("Failed to invoke upgrade Method", e);
      } catch (final NoSuchMethodException e) {
        throw new IllegalStateException(
            "Missing upgrade from version: " + from, e);
      }
      LogUtils.i("Upgraded database from version %d to %d", from, to);
      if (to <= from) {
        throw new IllegalStateException("UpgradeFrom" + from + "() did not advance");
      }
      if (to > newVersion) {
        throw new IllegalStateException("UpgradeFrom" + from + "() returned an invalid version: "
            + to);
      }
      if (to == newVersion) {
        break;
      }
      from = to;
    }
  }

  private void checkUpgradeMethods() {
    final Method[] methods = getClass().getMethods();
    for (final Method method : methods) {
      final String name = method.getName();
      if (name.startsWith(UPGRADE_METHOD_PREFIX)) {
        final int version = Integer.parseInt(name.substring(UPGRADE_METHOD_PREFIX.length()));
        if (version >= DATABASE_VERSION) {
          throw new IllegalStateException(String.format(
              "Found an upgrade method for a version that does not exist. "
                  + "Did you forget to bump DATABASE_VERSION. %d >= %d",
              version,
              DATABASE_VERSION));
        }
        final Class<?> returnType = method.getReturnType();
        if (!returnType.equals(void.class) && !returnType.equals(int.class)) {
          throw new IllegalStateException(String.format(
              "method %s must return either int or void", name));
        }
      }
    }
  }
}