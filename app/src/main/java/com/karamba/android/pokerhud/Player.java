package com.karamba.android.pokerhud;

public class Player {
  private final String name;
  private final int id;
  private final int hands;
  private final int seat;
  private final int active;
  private final int called;
  private final int raised;
  private int action;

  public Player(String name, int id, int hands, int seat, int active, int called, int raised, int action) {
    this.name = name;
    this.id = id;
    this.hands = hands;
    this.seat = seat;
    this.active = active;
    this.called = called;
    this.raised = raised;
    this.action = action;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public int getHands() {
    return hands;
  }

  public int getSeat() {
    return seat;
  }

  public int getActive() {
    return active;
  }

  public int getCalled() {
    return called;
  }

  public int getRaised() {
    return raised;
  }

  public int getAction() {
    return action;
  }

  public int getVpip() {
    return (called + raised) * 100 / hands;
  }

  public int getPfr() {
    return raised * 100 / hands;
  }

  public void setAction(int action) {
    this.action = action;
  }
}
