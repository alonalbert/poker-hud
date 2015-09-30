package com.karamba.android.pokerhud;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, OnDismissCallback {

  private View addPlayerView;
  private MyAdapter adapter;
  private static int id = 0;
  private DatabaseOpenHelper db;
  private int maxHand;
  private int currentHand;
  private View prevView;
  private View nextView;
  private TextView handNumView;

  public MainActivityFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    final View root = inflater.inflate(R.layout.fragment_main, container, false);

    final DynamicListView listView = (DynamicListView) root.findViewById(R.id.list);
    listView.enableDragAndDrop();
    listView.enableSwipeToDismiss(this);
    listView.setDraggableManager(new TouchViewDraggableManager(R.id.grip));
    adapter = new MyAdapter(getContext());
    listView.setAdapter(adapter);

    handNumView = (TextView) root.findViewById(R.id.hand_num);

    prevView = root.findViewById(R.id.previous_hand);
    nextView = root.findViewById(R.id.next_hand);
    addPlayerView = root.findViewById(R.id.add_player);

    prevView.setOnClickListener(this);
    nextView.setOnClickListener(this);
    addPlayerView.setOnClickListener(this);

    db = DatabaseOpenHelper.getInstance(getContext());

    maxHand = db.getLastHandNumber();
    currentHand = maxHand + 1;
    loadHands();
    return root;
  }

  private void loadHands() {
    final List<Player> players = db.getPlayers(currentHand > maxHand ? maxHand : currentHand);
    adapter.clear();
    for (Player  player : players) {
      adapter.add(player);
    }
    handNumView.setText(getString(R.string.hand_number, currentHand));
  }

  @Override
  public void onClick(View view) {
    if (view == addPlayerView) {
    } else if (view == prevView) {
      if (currentHand > 1) {
        currentHand--;
        loadHands();
      }
    } else if (view == nextView) {
      if (currentHand <= maxHand) {
        currentHand++;
        loadHands();
      }
    }
  }

  @Override
  public void onDismiss(ViewGroup parent, int[] positions) {
    for (int pos : positions) {
      adapter.remove(pos);
    }
  }

  private class MyAdapter extends ArrayAdapter<Player> {

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

      final int action = currentHand > maxHand ? 0 : player.getAction();
      ((ToggleButton) view.findViewById(R.id.raise)).setChecked(action == PokerHudConstants.RAISE);
      ((ToggleButton) view.findViewById(R.id.call)).setChecked(action == PokerHudConstants.CALLED);

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
  }

}
