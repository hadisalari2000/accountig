package com.salari.accounting.repository;

import com.salari.accounting.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findUserById(Integer id);
    Optional<User> findUserByUserName(String userName);
}