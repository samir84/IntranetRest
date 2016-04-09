package com.selazzouzi.intranet.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.selazzouzi.intranet.core.utils.RoleType;
import com.selazzouzi.intranet.core.utils.SearchCriteria;
import com.selazzouzi.intranet.core.utils.UserSpecificationsBuilder;
import com.selazzouzi.intranet.model.PasswordResetToken;
import com.selazzouzi.intranet.model.Role;
import com.selazzouzi.intranet.model.User;
import com.selazzouzi.intranet.repository.PasswordResetTokenRepository;
import com.selazzouzi.intranet.repository.RoleRepository;
import com.selazzouzi.intranet.repository.UserRepository;
import com.selazzouzi.intranet.web.error.UserAlreadyExistException;

@Service("userService")
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
	@Autowired
	private MessageSource messages;
	
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void save(User user) {
		// check if user already exist
		User oldUser = userRepository.findByEmail(user.getEmail());
		if (oldUser != null) {
			String message = messages.getMessage("message.regError",null,Locale.getDefault());
			throw new UserAlreadyExistException(message);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> userRoles = new HashSet<>();
		userRoles.add( getRoles().get(0));
		roleRepository.save(userRoles);
		user.setRoles(userRoles);
		userRepository.save(user);

	}
	@Override
	public void saveAdmin(User admin) {
		// check if user already exist
		User oldUser = userRepository.findByEmail(admin.getEmail());
		if (oldUser != null) {
			throw new UserAlreadyExistException();
		}
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add( getRoles().get(1));
		roleRepository.save(adminRoles);
		admin.setRoles(adminRoles);
		userRepository.save(admin);
	}
	@Override
	public User update(User user) {
		System.out.println("new User: "+user.toString());
		User oldUser = userRepository.findOne(user.getId());
		
		if(oldUser!= null){
			System.out.println("old User: "+oldUser.toString());
			oldUser.setId(user.getId());
			oldUser.setEmail(user.getEmail());
			oldUser.setFirstname(user.getFirstname());
			oldUser.setLastname(user.getLastname());
			oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(oldUser);
			System.out.println("User Updated");
		}
		return oldUser;
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);

	}

	@Override
	public User findByEmail(String email) {
		User user = null;
		try{
		System.out.println("find by email..");
		System.out.println("find by email: "+userRepository.findByEmail(email));
		user = userRepository.findByEmail(email);
		}catch(Exception ex){
			
			System.out.println("ex: "+ex.getMessage());
			ex.printStackTrace();
		}
		return user;
	}


	@Override
	public User findById(Long Id) {
		
		return userRepository.findOne(Id);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		
		return passwordTokenRepository.findByToken(token);
	}
	@Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

	@Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
	private List<Role> getRoles(){
		List<Role> roles = new ArrayList<Role>();
		Role roleUser = new Role(RoleType.USER.toString());
		Role roleAdmin = new Role(RoleType.ADMIN.toString());
		roles.add(roleUser);
		roles.add(roleAdmin);
		
		return roles;
	}

	//Search Users
	@Override
	public List<User> searchUser(String search){
		
		UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3),matcher.group(3),matcher.group(3));
        }
         
        Specification<User> spec = builder.build();
        return userRepository.findAll(spec);
	}


}
