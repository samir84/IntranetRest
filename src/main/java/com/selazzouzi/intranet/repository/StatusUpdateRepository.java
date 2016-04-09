package com.selazzouzi.intranet.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.selazzouzi.intranet.model.StatusUpdate;

public interface StatusUpdateRepository extends CrudRepository<StatusUpdate, Long> {

	List<StatusUpdate> findByPostedBy(String postedBy);
}
