package com.selazzouzi.intranet.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.selazzouzi.intranet.model.StatusUpdate;
import com.selazzouzi.intranet.repository.StatusUpdateRepository;

@Service
@Transactional
public class StatusUpdateService implements IStatusUpdateService {

	@Autowired
	private StatusUpdateRepository statusUpdateRepository;

	@Override
	public void save(StatusUpdate stausUpdate) {
		statusUpdateRepository.save(stausUpdate);
		
	}

	@Override
	public void delete(StatusUpdate stausUpdate) {
		statusUpdateRepository.delete(stausUpdate);
		
	}

	@Override
	public void update(StatusUpdate stausUpdate) {
		statusUpdateRepository.save(stausUpdate);
		
	}

	@Override
	public List<StatusUpdate> findByPostedBy(String postedBy) {
		// TODO Auto-generated method stub
		return statusUpdateRepository.findByPostedBy(postedBy);
	}
	
	

}
