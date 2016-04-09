package com.selazzouzi.intranet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.selazzouzi.intranet.model.User;

public interface UserRepository  extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	public User findByEmail(String email);
	public List<User> findAll();
	
}
