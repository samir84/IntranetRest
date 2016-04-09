package com.selazzouzi.intranet.service;

import java.util.List;

import com.selazzouzi.intranet.model.PasswordResetToken;
import com.selazzouzi.intranet.model.Role;
import com.selazzouzi.intranet.model.User;

public interface IUserService {

	public List<User> findAll();
	public void save(User user);
	public User update (User user);
	public void delete(User user);
	public User findById(Long id);
	public User findByEmail(String email);
	public PasswordResetToken getPasswordResetToken(String token);
	void createPasswordResetTokenForUser(User user, String token);
	void changeUserPassword(User user, String password);
	void saveAdmin(User admin);
	List<User> searchUser(String search);
}