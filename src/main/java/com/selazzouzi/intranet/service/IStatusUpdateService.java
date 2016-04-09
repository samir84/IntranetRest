package com.selazzouzi.intranet.service;

import java.util.List;

import com.selazzouzi.intranet.model.StatusUpdate;

public interface IStatusUpdateService {

	List<StatusUpdate> findByPostedBy(String postedBy);
	void save(StatusUpdate stausUpdate);
	void delete(StatusUpdate stausUpdate);
	void update(StatusUpdate stausUpdate);
}
