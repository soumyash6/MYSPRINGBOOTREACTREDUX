package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userservice;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userservice.findByUsername(username);
        if (user == null)
            new UsernameNotFoundException("user not Found");
        System.out.println("user: ");
        System.out.println(user);
        return user;
    }

    @Transactional
    public User loadUserById(Long id) throws UsernameNotFoundException {
        User user = userservice.findById(id).get();
        if (user == null)
            new UsernameNotFoundException("user not Found");

        return user;
    }

}
