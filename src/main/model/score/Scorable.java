package main.model.score;

public interface Scorable {

    void updateScore(double emission);

    int getScore();

    void resetWeeklyScore();
}
