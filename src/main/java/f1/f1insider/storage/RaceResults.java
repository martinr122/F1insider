package storage;

public class RaceResults {

    private int id;
    private int position;
    private boolean finished;
    private String intervalToWinner;
    private String reason;

    public RaceResults() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getIntervalToWinner() {
        return intervalToWinner;
    }

    public void setIntervalToWinner(String intervalToWinner) {
        this.intervalToWinner = intervalToWinner;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
