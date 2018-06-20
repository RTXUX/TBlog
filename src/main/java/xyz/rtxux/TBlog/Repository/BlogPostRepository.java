package xyz.rtxux.TBlog.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.rtxux.TBlog.Model.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    public Page<BlogPost> findAll(Pageable pageable);
}
