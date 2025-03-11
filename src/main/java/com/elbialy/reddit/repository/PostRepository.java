package com.elbialy.reddit.repository;

import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.Subreddit;
import com.elbialy.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
