package io.agileintelligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.agileintelligence.ppmtool.Exception.UsernotfoundException;
import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User newUser) {
		try {
			newUser.setConfirmPassword("");
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setUsername(newUser.getUsername());
			return userrepository.save(newUser);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("catch comming");
			throw new UsernotfoundException("user" + newUser.getFullName() + "already exits");
		}

	}
}
