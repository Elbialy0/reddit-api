package com.elbialy.reddit.repository;

import com.elbialy.reddit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubRedditRepository extends JpaRepository<Subreddit,Long> {
    Optional<Subreddit> findSubRedditByName(String subredditName);
}
