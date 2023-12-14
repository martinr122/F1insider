package f1.f1insider.storage;

public class Comment {
    int id;
    String comment;
    int userId;
    int raceId;

    public Comment() {
    }

    public Comment(String comment, int userId, int raceId) {
        this.comment = comment;
        this.userId = userId;
        this.raceId = raceId;
    }

    public Comment(int id, String comment, int userId, int raceId) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.raceId = raceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", userId=" + userId +
                        ", raceId=" + raceId +
                        '}';
    }
}
