package com.selazzouzi.intranet.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.selazzouzi.intranet.service.IProfileService;
import com.selazzouzi.intranet.service.IStatusUpdateService;
import com.selazzouzi.intranet.model.Profile;
import com.selazzouzi.intranet.model.User;

@RestController
public class ProfileController {

	@Autowired
	private IProfileService profileService;

	// retrive alle profiles
	@RequestMapping(value = "/profiles/", method = RequestMethod.GET)
	public ResponseEntity<List<Profile>> listAlleUsers() {
		System.out.println("Get all profiles");
		List<Profile> profiles = profileService.findAlleProfiles();
		if (profiles == null) {
			return new ResponseEntity<List<Profile>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Profile>>(profiles, HttpStatus.OK);
	}

	/*
	 * Retrieve a profile by email (username)
	 */
	@RequestMapping(value = "/profile/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> getUser(@PathVariable("email") String email) {
		System.out.println("Fetching User with email " + email);
		Profile profile = profileService.findProfileByEmail(email);
		if (profile == null) {
			System.out.println("User with email " + email + " not found");
			return new ResponseEntity<Profile>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Profile>(profile, HttpStatus.OK);
	}

	/*
	 * Create a profile
	 */
	@RequestMapping(value = "/profile/create", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody Profile profile, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Profile " + profile.getId());

		if (profileService.findProfileByEmail(profile.getEmail()) != null) {
			System.out.println("A Profile with email " + profile.getEmail() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		profileService.save(profile);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/profile/{email}").buildAndExpand(profile.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	 /*
     * Update profile
     */
    @RequestMapping(value = "/profile/update", method = RequestMethod.POST)
    public ResponseEntity<Profile> updateUser(@RequestBody Profile profile) {
        System.out.println("Updating profile "+profile.getEmail());
        Profile currentProfile = profileService.update(profile);
        if (currentProfile==null) {
            System.out.println("Profile with email " + currentProfile.getEmail() + " not found");
            return new ResponseEntity<Profile>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Profile>(currentProfile, HttpStatus.OK);
    }

	/*
	 * Delete Profile
	 */
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		System.out.println("Fetching & Deleting Profie with id " + id);

		Profile profile = profileService.findById(id);
		if (profile == null) {
			System.out.println("Unable to delete profile with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		profileService.delete(profile);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	/*
     * Search a Profile based on properties
     */
    @RequestMapping(method = RequestMethod.GET, value = "/profiles")
    @ResponseBody
    public List<Profile> search(@RequestParam(value = "search") String search) {
        
    	Profile p = new Profile();
    	Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?)&");
        Matcher matcher = pattern.matcher(search + "&");
        while (matcher.find()) {
        	System.out.println("matcher group 3:");
        	System.out.println("matcher group 3:"+matcher.group(3));
            String firstName = matcher.group(3);
            String lastName = matcher.group(3);
            p.setFirstName(firstName);
        	p.setLastName(lastName);
        }
    	return profileService.searchProfile(p);
    }

}
