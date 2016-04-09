package com.selazzouzi.intranet.service;

import java.util.List;

import com.selazzouzi.intranet.model.Image;

public interface ImageService {

	public List<Image> list();
	public Image create(Image image);
	public Image get(Long id);
	public void delete(Image image);
}
