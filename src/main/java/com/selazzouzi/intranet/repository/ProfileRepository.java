package com.selazzouzi.intranet.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.selazzouzi.intranet.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long>,JpaSpecificationExecutor<Profile> {

	Profile findByFirstName(String firstName);
	Profile findByLastName(String lastName);
	Profile findByEmail(String email);
}
