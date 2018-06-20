package xyz.rtxux.TBlog.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.rtxux.TBlog.Model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
