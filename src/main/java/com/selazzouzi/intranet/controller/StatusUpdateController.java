/*package com.selazzouzi.intranet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.selazzouzi.intranet.model.StatusUpdate;
import com.selazzouzi.intranet.service.IStatusUpdateService;

@Controller
public class StatusUpdateController {

	@Autowired
	IStatusUpdateService statusUpdateService;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String postStatus(@RequestParam("userId") String userId, @RequestParam("content") String content,
			@ModelAttribute("stausUpdate") StatusUpdate stausUpdate, BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:/user?userId=userId&content=news";
		}
		if (!userId.isEmpty() && !content.isEmpty()  ){
			statusUpdateService.save(stausUpdate);
			return "redirect:/user?userId=userId&content=news";
		}else{
			return "redirect:/user?userId=userId&content=news";
		}
		
	}
}
*/