package main.model.score;

import java.util.ArrayList;
import java.util.List;

public class ScoreHistory {

    private static final int MAX_ENTRIES = 52;
    private final List<Integer> history;

    public ScoreHistory() {
        this.history = new ArrayList<>();
    }

    public void addScore(int score) {
        if (history.size() >= MAX_ENTRIES) {
            history.remove(0);
        }
        history.add(score);
    }

    public List<Integer> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public String toString() {
        return history.toString();
    }
}
