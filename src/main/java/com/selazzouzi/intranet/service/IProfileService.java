package com.selazzouzi.intranet.service;

import java.util.List;

import com.selazzouzi.intranet.model.Profile;
import com.selazzouzi.intranet.model.User;

public interface IProfileService {

	 List<Profile> findAlleProfiles();
	 Profile findById(Long Id);
	 Profile findProfileByFirstName(String firstName);
	 Profile findProfileByLastName(String lastName);
	 Profile findProfileByEmail(String email);
	 Profile save(Profile profile);
	 Profile edit(Profile profile);
	 void delete(Profile profile);
	Profile update(Profile profile);
	List<Profile> searchProfile(Profile filter);
}
