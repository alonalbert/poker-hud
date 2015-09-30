package com.karamba.android.pokerhud;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PlayersActivity extends ListActivity {

  static final String EXCLUDE_PLAYERS = "exclude";

  private static final int DIALOG_ADD_PLAYER = 1;
  private DatabaseOpenHelper db;
  private SimpleCursorAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.players);

    db = DatabaseOpenHelper.getInstance(this);
    final String exclude = getIntent().getStringExtra(EXCLUDE_PLAYERS);
    final Cursor cursor = db.getPlayers(exclude);
    adapter = new SimpleCursorAdapter(
        this,
        android.R.layout.simple_list_item_1,
        cursor,
        new String[]{"name"},
        new int[]{android.R.id.text1});
    setListAdapter(adapter);

    final Button addPlayerButton = (Button) findViewById(R.id.add_player);
    addPlayerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showDialog(DIALOG_ADD_PLAYER);
      }
    });
  }

  public void onListItemClick(ListView parent, View view, int position, long id) {
    final Cursor cursor = (Cursor) parent.getItemAtPosition(position);
    final int player = cursor.getInt(cursor.getColumnIndex("_id"));
    returnResult(player);
  }

  private void returnResult(int player) {
    final Intent data = new Intent();
    data.putExtra("player", player);
    setResult(RESULT_OK, data);
    finish();
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    final Dialog dialog;

    switch (id) {
      case DIALOG_ADD_PLAYER:
        final LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.new_player_dialog, null);
        final EditText name = (EditText) view.findViewById(R.id.player_dialog_name);
        dialog = new AlertDialog.Builder(this)
            .setTitle(R.string.player_dialog_title)
            .setView(view)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                final int player = db.insertPlayer(name.getText().toString());
                returnResult(player);
              }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
              }
            })
            .create();
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
              dialog.getWindow().setSoftInputMode(
                  WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
          }
        });
        break;

      default:
        dialog = null;
        break;
    }

    return dialog;
  }

  @Override
  protected void onPrepareDialog(int id, Dialog dialog, final Bundle args) {
    final AlertDialog alertDialog = (AlertDialog) dialog;
    final EditText name = (EditText) alertDialog.findViewById(R.id.player_dialog_name);
    name.setText("");
  }


}