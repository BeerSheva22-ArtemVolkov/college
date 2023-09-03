//package telran.spring.college.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class filter {
//
//	@Bean
//	SecurityFilterChain configure(HttpSecurity http) throws Exception { // SecurityFilterChain - запросы приходят на
//		// него в первую очередь
//		return http.csrf(custom -> custom.disable())
//				.authorizeHttpRequests(custom -> custom.anyRequest().permitAll()).build();
////		return http.httpBasic(Customizer.withDefaults() 
////				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
//	}
//}
