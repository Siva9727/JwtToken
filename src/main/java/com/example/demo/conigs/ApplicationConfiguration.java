package com.example.demo.conigs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.repository.UserRepository;

@Configuration
public class ApplicationConfiguration {
	
	private final UserRepository userRepository;
	
	public ApplicationConfiguration(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    // defines how to retrieve the user using the UserRepository that is injected.
    @Bean
    UserDetailsService userDetailsService() {
		
		return username -> userRepository.findByEmail(username)
				.orElseThrow(()-> new UsernameNotFoundException("user not found"));
	}
	
	// password encoder :
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
