package com.kontociepok.springgradlehibernateh2;

import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringGradleHibernateH2Application implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringGradleHibernateH2Application.class, args);


	}
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User(1L, "wacek007","abcdef","Bartek","Ozon","1234", User.TypeUser.TEACHER);
		User user2 = new User(2L, "bond007","dcba","Wojtek","Wielki","4321", User.TypeUser.STUDENT);

		userRepository.save(user1);
		userRepository.save(user2);

	}

}
