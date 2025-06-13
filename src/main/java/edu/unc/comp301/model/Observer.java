package edu.unc.comp301.model;

import edu.unc.comp301.model.piece.GameStats;

public interface Observer {
    void update(GameStats gameStats);
}
