package com.elbialy.reddit.repository;

import com.elbialy.reddit.model.Post;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUser(Post post, User userNotFound);

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, Optional<User> userByUsername);
}
