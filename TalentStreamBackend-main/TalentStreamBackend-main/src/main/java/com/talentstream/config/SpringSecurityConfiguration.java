package com.talentstream.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.talentstream.filter.JwtRequestFilter;
import com.talentstream.service.JobRecruiterService;
import com.talentstream.service.RegisterService;


@EnableWebSecurity
@Configuration

class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	//@Autowired
	//private UserDetailsService myUserDetailsService;
	
	@Autowired
	private RegisterService applicantresisterService;
	
	
	@Autowired
	private JobRecruiterService jobrecuriterService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
//	 @Autowired
//	    private JobRecruiterService jobRecruiterService;

	//@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jobrecuriterService).passwordEncoder(passwordEncoder());
		auth.userDetailsService(applicantresisterService).passwordEncoder(passwordEncoder());
	}

   /* @Bean
    PasswordEncoder passwordEncoder() {
	//return NoOpPasswordEncoder.getInstance();
	return new BCryptPasswordEncoder();
}*/
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
		
				.authorizeRequests()
				.antMatchers("/save-company-profiles","/getCompanyProfile/{id}","/saveJob","/viewJobs").hasAnyRole("JOBRECRUITER")
				//.antMatchers("/api/applicant/login-authenticate").hasAnyRole("JOBAPPLICANT")
			    .antMatchers("/recruiter-authenticate","/send-otp","/verify-otp/{email}","/recruiter-saveRecruiters","/api/applicant-register","/api/applicant-authenticate","/registration-send-otp").permitAll()
						.anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
}

	