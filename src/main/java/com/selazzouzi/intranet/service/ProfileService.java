package com.selazzouzi.intranet.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.selazzouzi.intranet.core.utils.UserSpecificationsBuilder;
import com.selazzouzi.intranet.model.Absence;
import com.selazzouzi.intranet.model.Address;
import com.selazzouzi.intranet.model.Education;
import com.selazzouzi.intranet.model.Image;
import com.selazzouzi.intranet.model.Profile;
import com.selazzouzi.intranet.model.Skill;
import com.selazzouzi.intranet.model.User;
import com.selazzouzi.intranet.model.WorkDetails;
import com.selazzouzi.intranet.repository.AbsenceRepository;
import com.selazzouzi.intranet.repository.AddressRepository;
import com.selazzouzi.intranet.repository.EducationRepository;
import com.selazzouzi.intranet.repository.ImageRepository;
import com.selazzouzi.intranet.repository.ProfileRepository;
import com.selazzouzi.intranet.repository.SkillRepository;
import com.selazzouzi.intranet.repository.WorkDetailsRepository;
import com.selazzouzi.intranet.specification.ProfileSpecification;

@Service
@Transactional
public class ProfileService implements IProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private AbsenceRepository absenceRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private EducationRepository educationRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private WorkDetailsRepository workDetailsRepository;

	@Override
	public Profile findProfileByFirstName(String firstName) {

		return profileRepository.findByFirstName(firstName);
	}

	@Override
	public Profile findProfileByLastName(String lastName) {
		// TODO Auto-generated method stub
		return profileRepository.findByLastName(lastName);
	}

	@Override
	public Profile findProfileByEmail(String email) {
		// TODO Auto-generated method stub
		return profileRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public Profile save(Profile profile) {
		List<Absence> absences = profile.getAbsences();
		absenceRepository.save(absences);
		Address address = profile.getAddress();
		if(address!= null){
			addressRepository.save(address);
		}
		List<Education> educations = profile.getEducations();
		educationRepository.save(educations);
		List<Image> fotos = profile.getProfilePhotos();
		imageRepository.save(fotos);
		List<Skill> skills = profile.getSkills();
		skillRepository.save(skills);
		List<WorkDetails> workDetails = profile.getWorkDetails();
		workDetailsRepository.save(workDetails);

		return profileRepository.save(profile);

	}

	@Override
	public Profile edit(Profile profile) {

		return profileRepository.save(profile);

	}

	@Override
	public void delete(Profile profile) {

		profileRepository.delete(profile);

	}

	@Override
	public List<Profile> findAlleProfiles() {
		// TODO Auto-generated method stub
		return (List<Profile>) profileRepository.findAll();
	}

	@Override
	public Profile findById(Long Id) {
		// TODO Auto-generated method stub
		return profileRepository.findOne(Id);
	}

	@Override
	public Profile update(Profile profile) {

		System.out.println("new User: " + profile.toString());
		Profile oldProfile = profileRepository.findOne(profile.getId());

		if (oldProfile != null) {
			
			List<Absence> absences = profile.getAbsences();
			absenceRepository.save(absences);
			Address address = profile.getAddress();
			if(address!= null){
				addressRepository.save(address);
			}
			
			List<Education> educations = profile.getEducations();
			educationRepository.save(educations);
			List<Image> fotos = profile.getProfilePhotos();
			imageRepository.save(fotos);
			List<Skill> skills = profile.getSkills();
			skillRepository.save(skills);
			List<WorkDetails> workDetails = profile.getWorkDetails();
			workDetailsRepository.save(workDetails);
			
			oldProfile.setAbout(profile.getAbout());
			oldProfile.setBefriended(profile.getBefriended());
			oldProfile.setBirthDate(profile.getBirthDate());
			oldProfile.setColleagues(profile.getColleagues());
			oldProfile.setEmail(profile.getEmail());
			oldProfile.setFirstName(profile.getFirstName());
			oldProfile.setLastName(profile.getLastName());
			oldProfile.setGendre(profile.getGendre());
			oldProfile.setLanguages(profile.getLanguages());
			oldProfile.setPhoneHome(profile.getPhoneHome());
			oldProfile.setPhoneMobile(profile.getPhoneMobile());
			oldProfile.setTitle(profile.getTitle());
			oldProfile.setAbsences(absences);
			oldProfile.setAddress(address);
			oldProfile.setEducations(educations);
			oldProfile.setProfilePhotos(fotos);
			oldProfile.setSkills(skills);
			oldProfile.setWorkDetails(workDetails);
			
			profileRepository.save(oldProfile);
			System.out.println("User Updated");
		}
		return oldProfile;
	}

	@Override
	public List<Profile> searchProfile(Profile filter) {

        Specification<Profile> spec = new ProfileSpecification(filter);
        return profileRepository.findAll(spec);
	}

	

}
