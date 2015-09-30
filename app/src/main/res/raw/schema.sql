CREATE TABLE players (
  _id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  UNIQUE (name)
  );

CREATE TABLE hands (
  player INTEGER,
  hand INTEGER,
  seat INTEGER,
  active INTEGER,
  act INTEGER,
  FOREIGN KEY(player) REFERENCES players(_id)
  );
