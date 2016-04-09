package com.selazzouzi.intranet.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "profiles")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "profile_id")
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String title;
	
	private String about;
	@Column(name = "phone_home")
	private String PhoneHome;
	@Column(name = "phone_work")
	private String PhoneWork;
	@Column(name = "phone_mobile")
	private String phoneMobile;
	@Column(name = "email", unique = true)
	@Email
	private String email;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", referencedColumnName = "address_id" , nullable=true)
	private Address address;
	private String Gendre;
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection
	@CollectionTable(name = "languages", joinColumns = @JoinColumn(name = "profile_id"))
	@Column(name = "languages")
	private List<String> languages;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
	private List<Absence> absences;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
	private List<Image> profilePhotos;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
	private List<WorkDetails> workDetails;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "profile_education", joinColumns = {
			@JoinColumn(name = "profile_id", referencedColumnName = "profile_id") }, inverseJoinColumns = {

					@JoinColumn(name = "education_id", referencedColumnName = "education_id") })
	private List<Education> educations;
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "profile_skill", joinColumns = {
			@JoinColumn(name = "profile_id", referencedColumnName = "profile_id") }, inverseJoinColumns = {
					@JoinColumn(name = "skill_id", referencedColumnName = "skill_id") })
	private List<Skill> skills;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "profile_colleagues", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "colleague_id"))
	private List<Profile> colleagues;
	@JsonIgnore
	@ManyToMany(mappedBy = "colleagues")
	protected List<Profile> befriended;
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	public String getEmail() {
		return email;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public List<Profile> getColleagues() {
		return colleagues;
	}

	public void setColleagues(List<Profile> colleagues) {
		this.colleagues = colleagues;
	}

	public List<Profile> getBefriended() {
		return befriended;
	}

	public void setBefriended(List<Profile> befriended) {
		this.befriended = befriended;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Profile() {
		this.creationDate = new Date();
	}

	public String getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPhoneHome() {
		return PhoneHome;
	}

	public void setPhoneHome(String phoneHome) {
		PhoneHome = phoneHome;
	}

	public String getPhoneWork() {
		return PhoneWork;
	}

	public void setPhoneWork(String phoneWork) {
		PhoneWork = phoneWork;
	}

	public List<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	public String getGendre() {
		return Gendre;
	}

	public void setGendre(String gendre) {
		Gendre = gendre;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public List<Image> getProfilePhotos() {
		return profilePhotos;
	}

	public void setProfilePhotos(List<Image> profilePhotos) {
		this.profilePhotos = profilePhotos;
	}

	public List<WorkDetails> getWorkDetails() {
		return workDetails;
	}

	public void setWorkDetails(List<WorkDetails> workDetails) {
		this.workDetails = workDetails;
	}

	public List<Education> getEducations() {
		return educations;
	}

	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Profile [firstName=" + firstName + ", lastName=" + lastName + ", about=" + about + ", PhoneHome="
				+ PhoneHome + ", PhoneWork=" + PhoneWork + ", phoneMobile=" + phoneMobile + ", emailAddress=" + email
				+ ", address=" + "" + ", Gendre=" + Gendre + ", birthDate=" + birthDate + ", languages=" + languages
				+ ", absences=" + absences + ", profilePhotos=" + profilePhotos + ", workDetails=" + workDetails
				+ ", educations=" + educations + ", skills=" + skills + "]";
	}

}
