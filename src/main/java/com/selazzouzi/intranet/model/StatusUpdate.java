package com.selazzouzi.intranet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="status_update")
public class StatusUpdate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "status_update_id")
	private Long id;
	@Lob
	@Column(name="body", columnDefinition="TEXT")
	private String body;
	@Column(name="posted_by")
	private String postedBy;
	@Column(name="posted_to")
	private String postedTo;
	@Column(name="posted_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date postDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public String getPostedTo() {
		return postedTo;
	}
	public void setPostedTo(String postedTo) {
		this.postedTo = postedTo;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
}
