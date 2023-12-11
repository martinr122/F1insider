package f1.f1insider.storage;

import java.util.List;

public interface CommentDao {
    void add(Comment comment);

    List<Comment> allCommentByRace(int id);
}
