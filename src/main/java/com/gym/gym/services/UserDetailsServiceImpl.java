package com.gym.gym.services;

import com.gym.gym.models.Role;
import com.gym.gym.models.User;
import com.gym.gym.repo.RoleRepository;
import com.gym.gym.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
     @Autowired
    private RoleRepository rolerepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user by email (or username)
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Convert roles to authorities (SimpleGrantedAuthority) with 'ROLE_' prefix
        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))  // Add 'ROLE_' prefix
                .collect(Collectors.toSet());

        // Return a UserDetails instance (using Spring Security's User class)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),  // Username (email)
                user.getPassword(),  // Password
                authorities  // Granted Authorities (Roles)
        );
    }

    // Save user to the database
    public void saveUser(User user) {
        // Validate the password field
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
       

        // Encrypt password before saving
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(rolerepo.findByName("USER"));
            user.setRoles(adminRoles);

        // Save user to the database
        userRepository.save(user);
    }

    // Expose the passwordEncoder (getter)
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    // Find user by ID
    public User findUser(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
