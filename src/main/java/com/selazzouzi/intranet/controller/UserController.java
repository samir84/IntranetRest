package com.selazzouzi.intranet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.selazzouzi.intranet.service.IUserService;
import com.selazzouzi.intranet.model.User;

@RestController
public class UserController {

	@Autowired
    IUserService userService;  //Service which will do all data retrieval/manipulation work
 
     
    /*
     * Retrieve all users
     */
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAll();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
 
 
    /*
     * Retrive single user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        System.out.println("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
 
     
     
    /*
     * Create User
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user,UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getEmail());
 
        if (userService.findById(user.getId())!= null) {
            System.out.println("A User with email " + user.getEmail() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        userService.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    /*
     * Update user
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        System.out.println("Updating User "+user.getEmail());
         
        User currentUser = userService.update(user);
         
        if (currentUser==null) {
            System.out.println("User with email " + user.getEmail() + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
   
        
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
 
    /*
     * Delete user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting User with id " + id);
 
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        userService.delete(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
 
    /*
     * Search a user based on properties
     */
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<User> search(@RequestParam(value = "search") String search) {
        
    	return userService.searchUser(search);
    }
 

}
