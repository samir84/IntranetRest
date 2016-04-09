package com.selazzouzi.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selazzouzi.intranet.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {


}
