package com.multipledb.postgres.repo;

import com.multipledb.postgres.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
