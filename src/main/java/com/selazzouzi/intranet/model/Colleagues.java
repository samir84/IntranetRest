package com.selazzouzi.intranet.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Colleagues {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "colleagues_id")
	private Long id;
	
	@ManyToMany(mappedBy = "colleagues")
	private List<Profile> befriended;
}
