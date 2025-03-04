package com.elbialy.reddit.repository;

import com.elbialy.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
