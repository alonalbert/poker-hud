package com.karamba.android.pokerhud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity implements View.OnClickListener, OnDismissCallback {

  private static final int ACTIVITY_ADD_PLAYER = 0;

  private View addPlayerView;
  private MyAdapter adapter;
  private static int id = 0;
  private DatabaseOpenHelper db;
  private int maxHand;
  private int currentHand;
  private View prevView;
  private View nextView;
  private TextView handNumView;
  private boolean modified;
  @Override


  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    final DynamicListView listView = (DynamicListView) findViewById(R.id.list);
    listView.enableDragAndDrop();
    listView.enableSwipeToDismiss(this);
    listView.setDraggableManager(new TouchViewDraggableManager(R.id.grip));
    adapter = new MyAdapter(this);
    listView.setAdapter(adapter);

    handNumView = (TextView) findViewById(R.id.hand_num);

    prevView = findViewById(R.id.previous_hand);
    nextView = findViewById(R.id.next_hand);
    addPlayerView = findViewById(R.id.add_player);

    prevView.setOnClickListener(this);
    nextView.setOnClickListener(this);
    addPlayerView.setOnClickListener(this);

    db = DatabaseOpenHelper.getInstance(this);

    maxHand = db.getLastHandNumber();
    currentHand = maxHand + 1;
    loadHands();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) {
      return;
    }
    final int playerId = data.getIntExtra("player", 0);

    final Player player = db.getPlayer(playerId, currentHand);
    if (player != null) {
      adapter.add(player);
    }
  }

  private void loadHands() {
    final List<Player> players = db.getPlayers(currentHand > maxHand ? maxHand : currentHand);
    adapter.clear();
    for (Player player : players) {
      if (currentHand > maxHand) {
        player.setAction(PokerHudConstants.FOLD);
      }
      adapter.add(player);
    }
    handNumView.setText(getString(R.string.hand_number, currentHand));
    modified = false;
  }

  @Override
  public void onClick(View view) {
    if (view == addPlayerView) {
      addPlayer();
    } else if (view == prevView) {
      if (currentHand > 1) {
        currentHand--;
        loadHands();
      }
    } else if (view == nextView) {
      if (currentHand <= maxHand) {
        currentHand++;
        loadHands();
      } else if (modified) {
        insertHand();
        maxHand++;
        currentHand++;
        loadHands();
      }
    }
  }

  private void addPlayer() {
    final Set<Integer> playerIds = new HashSet<>();
    for (Player players : adapter.getItems()) {
      playerIds.add(players.getId());
    }
    final Intent intent = new Intent(this, PlayersActivity.class);
    intent.putExtra(PlayersActivity.EXCLUDE_PLAYERS, TextUtils.join(",", playerIds));
    startActivityForResult(intent, ACTIVITY_ADD_PLAYER);
  }

  private void insertHand() {
    if (modified) {
      db.insertHand(currentHand, adapter.getItems());
    }
  }

  @Override
  public void onDismiss(ViewGroup parent, int[] positions) {
    for (int pos : positions) {
      adapter.remove(pos);
    }
  }

  private class MyAdapter extends ArrayAdapter<Player> implements CompoundButton.OnCheckedChangeListener {

    private final Context context;

    public MyAdapter(Context context) {
      this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
      if (view == null) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
      }

      final Player player = getItem(position);
      ((TextView) view.findViewById(R.id.seat)).setText(String.valueOf(position + 1));
      ((TextView) view.findViewById(R.id.name)).setText(player.getName());
      ((TextView) view.findViewById(R.id.hands)).setText(String.valueOf(player.getHands()));
      ((TextView) view.findViewById(R.id.vpip)).setText(String.valueOf(player.getVpip()));
      ((TextView) view.findViewById(R.id.pfr)).setText(String.valueOf(player.getPfr()));

      final int action = player.getAction();
      final ToggleButton raiseView = (ToggleButton) view.findViewById(R.id.raise);
      final ToggleButton callView = (ToggleButton) view.findViewById(R.id.call);
      raiseView.setChecked(action == PokerHudConstants.RAISE);
      raiseView.setOnCheckedChangeListener(this);
      raiseView.setTag(R.id.tag_player, player);
      raiseView.setTag(R.id.tag_other_button, callView);
      callView.setChecked(action == PokerHudConstants.CALLED);
      callView.setOnCheckedChangeListener(this);
      callView.setTag(R.id.tag_player, player);
      callView.setTag(R.id.tag_other_button, raiseView);

      return view;
    }


    @Override
    public boolean hasStableIds() {
      return true;
    }

    @Override
    public long getItemId(final int position) {
      return getItem(position).hashCode();
    }

    @Override
    public void onCheckedChanged(CompoundButton view, boolean b) {
      final Player player = (Player) view.getTag(R.id.tag_player);
      if (view.getId() == R.id.raise) {
        player.setAction(b ? PokerHudConstants.RAISE : PokerHudConstants.FOLD);
      } else {
        player.setAction(b ? PokerHudConstants.CALLED : PokerHudConstants.FOLD);
      }
      if (b) {
        ((ToggleButton) view.getTag(R.id.tag_other_button)).setChecked(false);
      }
      modified = true;
    }
  }


  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
