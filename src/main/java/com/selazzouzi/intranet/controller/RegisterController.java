/*package com.selazzouzi.intranet.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.selazzouzi.intranet.model.PasswordResetToken;
import com.selazzouzi.intranet.model.User;
import com.selazzouzi.intranet.service.IUserService;

@Controller
public class RegisterController {

	
	@Autowired
	private IUserService userService;
	@Autowired
	private MessageSource messages;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JavaMailSender mailSender;

	@Value( "${mail.support}" )
	private String mailSupport;

	@Value( "${support.email}" )
	private String supportEmail;
	
	@RequestMapping(value = "/registerPage")
	public String showRegisterPage(ModelMap model ) {
		
		return "register";

	}
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegister(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "message", required = false) String msgParam, Model model, Locale locale) {
		System.out.println("Do GET register..");
		if (msgParam != null && !msgParam.isEmpty()) {
			switch (msgParam) {
			case "invalidToken":
				model.addAttribute("message", messages.getMessage("auth.message.invalidToken", null, locale));

			case "expiredToken":

				model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
			case "resetPasswordEmail":
				model.addAttribute("message", messages.getMessage("message.resetPasswordEmail", null, locale));

			case "resetPasswordSucces":
				model.addAttribute("message", messages.getMessage("message.resetPasswordSuc", null, locale));
			}

			// return "register";
		}
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(@RequestParam(value="fragments" ,required=false) String fragments,
			@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		System.out.println("Do POST register..");
         if(fragments!=null){
        	 //needed to handle partial ajax
        	 return "register";
         }
		// System.out.println("FORM VALUES AFTER: " + user.toString());
		if (bindingResult.hasErrors()) {
			System.out.println("Returning error ..");
			for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					System.out.println(fieldError.getCode());
				}

				if (object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;
					System.out.println(objectError.getCode());
				}
			}
			return "redirect:/register?error=t";
		}

		System.out.println("Returning succes registerd page");

		model.addAttribute("user", user);
		userService.save(user);
		return "redirect:login";

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final long id,
			@RequestParam("token") final String token) {
		System.out.println("Show changePassword GET..");
		final PasswordResetToken passToken = userService.getPasswordResetToken(token);
		final User user = passToken.getUser();
		if ((passToken == null) || (user.getId() != id)) {
			final String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
			System.out.println("change password :" + message);
			return "redirect:/register?message=invalidToken";
		}

		final Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			final String message = messages.getMessage("auth.message.expired", null, locale);
			model.addAttribute("message", message);
			System.out.println("change password :" + message);
			return "redirect:/register?message=expiredToken";
		}

		final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
				userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return "redirect:/register?message=updatePassword";
	}

	@RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	public String savePassword( @RequestParam("password") final String password) {
		System.out.println("Show savePassword POST..");
		System.out.println("Show savePassword POST.."+bindingResult.getAllErrors());
		for (Object object : bindingResult.getAllErrors()) {
			if (object instanceof FieldError) {
				FieldError fieldError = (FieldError) object;
				System.out.println(fieldError.getCode());
			}

			if (object instanceof ObjectError) {
				ObjectError objectError = (ObjectError) object;
				System.out.println(objectError.getCode());
			}
		}
		final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.changeUserPassword(user, password);
		return "redirect:/register?message=resetPasswordSucces";
	}

	// Reset password
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String showResetPassword(){
		
		return "forgotPassword";
	}
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail)
			throws MessagingException {
		final User user = userService.findByEmail(userEmail);
		if (user == null) {
			try {
				throw new Exception("User not found.. handel later");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		final String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();

		constructMimeResetTokenEmail(userEmail,appUrl, request.getLocale(),token, user);

		return "redirect:/register?message=resetPasswordEmail";
	}

	private final void constructMimeResetTokenEmail(String userEmail ,final String contextPath, final Locale locale,
			final String token, final User user) {

		final String url = contextPath + "/changePassword?id=" + user.getId() + "&token=" + token;
		final String subject = messages.getMessage("message.resetPassword", null, locale);

		StringBuilder msgBody = new StringBuilder(subject);
		msgBody.append("\r\n").append("<a href='").append(url).append("'").append(">").append(subject).append("</a>");

		sendMail(userEmail,subject, msgBody.toString());
	}

	public void sendMail(String toEmail,String subject, String content) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			System.out.println("From address: " + mailSupport);
			helper.setFrom(mailSupport);
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(content, true);

			//FileSystemResource file = new FileSystemResource("C:\\log.txt");
			//helper.addAttachment(file.getFilename(), file);

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		mailSender.send(mimeMessage);
	}

}
*/