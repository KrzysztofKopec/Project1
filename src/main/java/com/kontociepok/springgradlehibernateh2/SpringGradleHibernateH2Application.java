package com.kontociepok.springgradlehibernateh2;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringGradleHibernateH2Application implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringGradleHibernateH2Application.class, args);


	}
	@Override
	public void run(String... args) {
		User user = new User("Tomek", "banan");
		User user1 = new User("Mietek", "orange");
		Course course = new Course("Informatyka","klub wew.");
		Course course1 = new Course("Matematyka","klub szkolny");

		userRepository.save(user);
		userRepository.save(user1);
		courseRepository.save(course);
		courseRepository.save(course1);

	}

}
