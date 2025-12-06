package main.model.score;

public class EcoScoreEngine implements Scorable {

    private int score = 0;

    public EcoScoreEngine() {}

    @Override
    public synchronized void updateScore(double emissionKg) {
        if (emissionKg <= 0.0) {
            score += 5;
        } else if (emissionKg < 1.0) {
            score += 3;
        } else if (emissionKg < 3.0) {
            score += 1;
        } else {
            score -= 1;
        }

        if (score < 0) score = 0;
    }

    @Override
    public synchronized int getScore() {
        return score;
    }

    @Override
    public synchronized void resetWeeklyScore() {
        score = 0;
    }
    public synchronized void setScore(int score) {
        this.score = Math.max(0, score);
    }


    public synchronized void resetScoreWeekly() {
        resetWeeklyScore();
    }
}

