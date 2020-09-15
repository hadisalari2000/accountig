package com.salari.accounting.repository;

import com.salari.accounting.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Short>, JpaSpecificationExecutor<Role> {

    Optional<Role> findRoleById(Short id);
    Optional<Role> findRoleByTitle(String title);
    Optional<List<Role>> findAllByIdIsNotNull();

}
