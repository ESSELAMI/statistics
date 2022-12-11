package dz.me.dashboard.restcontroller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dz.me.dashboard.entities.Role;
import dz.me.dashboard.entities.Service;

import dz.me.dashboard.entities.User;

import dz.me.dashboard.repositories.RoleRepository;
import dz.me.dashboard.repositories.ServiceRepository;

import dz.me.dashboard.repositories.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	// ALTER DATABASE file_attente CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	// ALTER TABLE structure CONVERT TO CHARACTER SET utf8mb4 COLLATE
	// utf8mb4_unicode_ci;
	// ALTER TABLE service CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexOperation() {

		userRepository.deleteAll();

		roleRepository.deleteAll();

		serviceRepository.deleteAll();

		Role role = new Role("ADMIN");

		roleRepository.save(role);

		Service service = new Service();
		service.setService("Service des Prestations");
		service.setServiceAr("الأداءات");
		service.setServiceLettre("A");

		Service service1 = new Service();

		service1.setService("Service Control Medical");
		service1.setServiceAr("الرقابة الطبية");
		service1.setServiceLettre("B");

		Service service2 = new Service();
		service2.setService("Service recouvrement");
		service2.setServiceAr("التحصيل");
		service2.setServiceLettre("C");

		Service service3 = new Service();
		service2.setService("Service Finance");
		service2.setServiceAr("المالية");
		service2.setServiceLettre("D");

		serviceRepository.save(service);
		serviceRepository.save(service1);
		serviceRepository.save(service2);
		serviceRepository.save(service3);

		User user = new User();
		user.setEmail("Tarek@gmail.com");
		user.setFirstname("Tarek");
		user.setLastname("Mekriche");
		user.setPassword(
				"$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setService(service);
		user.setUsername("tarek");
		user.setEmail("Tarek@gmail.com");
		User user1 = new User();
		user1.setFirstname("Abdellatif");
		user1.setLastname("Esselami");
		user1.setPassword(
				"$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.");
		roles.add(role);
		user1.setRoles(roles);
		user1.setAccountNonExpired(true);
		user1.setAccountNonLocked(true);
		user1.setCredentialsNonExpired(true);
		user1.setEnabled(true);
		user1.setService(service);
		user1.setUsername("abdelatif");
		user1.setEmail("abdelatif@gmail.com");

		User user2 = new User();
		user2.setFirstname("Abdelhak");
		user2.setLastname("Omerani");
		user2.setPassword(
				"$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.");
		roles.add(role);
		user2.setRoles(roles);
		user2.setAccountNonExpired(true);
		user2.setAccountNonLocked(true);
		user2.setCredentialsNonExpired(true);
		user2.setEnabled(true);
		user2.setService(service1);
		user2.setUsername("Abdelhak");
		user1.setEmail("Abdelhak@gmail.com");
		userRepository.save(user);
		userRepository.save(user1);
		userRepository.save(user2);

		return "redirect:/swagger-ui.html";

	}
}
