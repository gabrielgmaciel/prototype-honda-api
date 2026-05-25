package com.prototype.honda.api.user.repository;

import com.prototype.honda.api.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("{$or: [{email: ?0},{name: {$regex: ?1,$options: 'i'}}]}")
    Page<User> findByEmailOrName(String email, String name, Pageable pageable);

}
