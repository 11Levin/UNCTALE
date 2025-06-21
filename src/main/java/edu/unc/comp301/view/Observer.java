package edu.unc.comp301.view;

import edu.unc.comp301.model.piece.GameStats;

public interface Observer {
  void update(GameStats gameStats);
}
