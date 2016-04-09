package com.selazzouzi.intranet.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "absence")
public class Absence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "absence_id")
	private Long id;
	@Column(name = "absence_type")
	private String type;
	@Temporal(TemporalType.DATE)
	@Column(name = "EVENTDATE", nullable = false)
	@NotNull
	private Date eventDate;
	@Column(name = "HOURS", nullable = false)
	@NotNull
	private Integer hours;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Profile.class)
	@JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
	private Profile profile;

}
