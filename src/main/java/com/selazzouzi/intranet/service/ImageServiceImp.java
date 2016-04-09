package com.selazzouzi.intranet.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.selazzouzi.intranet.model.Image;
import com.selazzouzi.intranet.repository.ImageRepository;

@Service
@Transactional
public class ImageServiceImp implements ImageService {

	private static final Logger log = LoggerFactory.getLogger(ImageServiceImp.class);
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<Image> list() {
		
		log.debug("List of images");
        List<Image> images = (List<Image>) imageRepository.findAll();
        return images;
	}

	@Override
	public Image create(Image image) {
		
		log.debug("Creating image");
		imageRepository.save(image);
        return image;
	}

	@Override
	public Image get(Long id) {
		
		log.debug("Getting image {}", id);
        Image image = (Image) imageRepository.findOne(id);
        return image;
	}

	@Override
	public void delete(Image image) {
		
		log.debug("Deleting image {}", image.getName());
		imageRepository.delete(image);
		
	}

}
