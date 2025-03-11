package com.elbialy.reddit.repository;

import com.elbialy.reddit.model.Comment;
import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUser(User user);
}
