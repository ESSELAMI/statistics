package dz.me.dashboard.restcontroller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dz.me.dashboard.entities.Privilege;
import dz.me.dashboard.entities.Role;
import dz.me.dashboard.entities.Service;

import dz.me.dashboard.entities.User;
import dz.me.dashboard.repositories.PrivilegeRepository;
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
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	// ALTER DATABASE file_attente CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	// ALTER TABLE structure CONVERT TO CHARACTER SET utf8mb4 COLLATE
	// utf8mb4_unicode_ci;
	// ALTER TABLE service CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexOperation() {

		// userRepository.deleteAll();

		// roleRepository.deleteAll();

		// serviceRepository.deleteAll();
		// privilegeRepository.deleteAll();

		// roleRepository
		// .saveAll(List.of(new Role("ADMIN"), new Role("SUPERADMIN"), new Role("SDP"),
		// new Role("SDR"), new Role("SDF"), new Role("SDCM"), new Role("SUPERVISOR")));

		// serviceRepository.saveAllAndFlush(List.of(new Service("Service des
		// Prestations", "الأداءات", "P"),
		// new Service("Service de Contrôl Médical", "الرقابة الطبية", "C"),
		// new Service("Service de Recouvrement", "المالية", "F"),
		// new Service("Service Finance", "الأداءات", "P")));

		// userRepository.saveAll(List.of(
		// new User("esselami", "esselamia@cnas.dz",
		// "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.",
		// "ABDELLATIF",
		// "ESSELAMI"),
		// new User("dr_aindefla", "dr_aindefla@cnas.dz",
		// "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.", "AHMED",
		// "ESSELAMI"),
		// new User("sdf_aindefla", "sdf_aindefla@cnas.dz",
		// "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.", "SDF",
		// "AIN DEFLA"),
		// new User("sdr_aindefla", "sdr_aindefla@cnas.dz",
		// "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.", "SDR",
		// "AIN DEFLA"),
		// new User("sdp_aindefla", "sdp_aindefla@cnas.dz",
		// "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.", "SDP", "AIN
		// DEFLA"),
		// new User("cm_aindefla", "cm_aindefla@cnas.dz",
		// "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.", "CM", "AIN
		// DEFLA"),
		// new User("admin_aindefla", "ccaindefla@cnas.dz",
		// "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.", "AHMED",
		// "NEMAR")));

		// privilegeRepository.saveAll(List.of(
		// new Privilege("ACCESS-DASHBOARD-SCREEN"),
		// new Privilege("ACCESS-PROFILE-SCREEN"),
		// new Privilege("ACCESS-SDP-SCREEN"),
		// new Privilege("ACCESS-SDR-SCREEN"),
		// new Privilege("ACCESS-CM-SCREEN"),
		// new Privilege("ACCESS-SDF-SCREEN"),
		// new Privilege("ADD-SDR-STAT"),
		// new Privilege("UPDATE-SDR-STAT"),
		// new Privilege("DELETE-SDR-STAT"),
		// new Privilege("ADD-SDP-STAT"),
		// new Privilege("UPDATE-SDP-STAT"),
		// new Privilege("DELETE-SDP-STAT"),
		// new Privilege("ADD-CM-STAT"),
		// new Privilege("UPDATE-CM-STAT"),
		// new Privilege("DELETE-CM-STAT"),
		// new Privilege("ADD-SDF-STAT"),
		// new Privilege("UPDATE-SDF-STAT"),
		// new Privilege("DELETE-SDF-STAT")));

		// ));
		// List<String> listPrivs = new ArrayList<>();
		// listPrivs.add("ACCESS-DASHBOARD-SCREEN");
		// listPrivs.add("ACCESS-SDP-SCREEN");
		// listPrivs.add("ACCESS-SDR-SCREEN");
		// listPrivs.add("ACCESS-CM-SCREEN");
		// listPrivs.add("ACCESS-SDF-SCREEN");
		// listPrivs.add("ADD-SDR-STAT");
		// listPrivs.add("UPDATE-SDR-STAT");
		// listPrivs.add("DELETE-SDR-STAT");
		// listPrivs.add("ADD-SDP-STAT");
		// listPrivs.add("UPDATE-SDP-STAT");
		// listPrivs.add("DELETE-SDP-STAT");
		// listPrivs.add("ADD-CM-STAT");
		// listPrivs.add("UPDATE-CM-STAT");
		// listPrivs.add("DELETE-CM-STAT");
		// listPrivs.add("ADD-SDF-STAT");
		// listPrivs.add("UPDATE-SDF-STAT");
		// listPrivs.add("DELETE-SDF-STAT");

		// List<Role> ro = roleRepository.findByName("SDP");
		// List<Privilege> pr = privilegeRepository.findByName("DELETE-SDP-STAT");
		// System.out.println(ro);
		// System.out.println(pr);
		// //
		// ro.get(0).setPrivileges(privilegeRepository.findByName("ACCESS-DASHBOARD-SCREEN"));
		// ro.get(0).setPrivileges(pr);

		// roleRepository.saveAll(ro);

		// roleRepository.findByName("ADMIN").get(0).setPrivileges(privilegeRepository.findAll());
		// roleRepository.findByName("SUPERADMIN").get(0).setPrivileges(privilegeRepository.findAll());
		// roleRepository.findByName("SUPERVISOR").get(0)
		// .setPrivileges(privilegeRepository.findAll1(priv));

		// roleRepository.findByName("SDP").get(0).setPrivileges(privilegeRepository
		// .findAll1(List.of("ACCESS-SDP-SCREEN", "ADD-SDP-STAT", "UPDATE-SDP-STAT",
		// "DELETE-SDP-STAT")));
		// roleRepository.findByName("SDR").get(0).setPrivileges(privilegeRepository
		// .findAll1(List.of("ACCESS-SDR-SCREEN", "ADD-SDR-STAT", "UPDATE-SDR-STAT",
		// "DELETE-SDR-STAT")));
		// roleRepository.findByName("CM").get(0).setPrivileges(privilegeRepository
		// .findAll1(List.of("ACCESS-CM-SCREEN", "ADD-CM-STAT", "UPDATE-CM-STAT",
		// "DELETE-CM-STAT")));
		// roleRepository.findByName("SDF").get(0).setPrivileges(privilegeRepository
		// .findAll1(List.of("ACCESS-SDF-SCREEN", "ADD-SDF-STAT", "UPDATE-SDF-STAT",
		// "DELETE-SDF-STAT")));

		// User u = userRepository.findByUsername("dr_aindefla").get();
		// u.setRoles(ro);
		// userRepository.save(u);
		// userRepository.findByUsername("esselami").get().setRoles(roleRepository.findAll(List.of("SUPERADMIN")));
		// userRepository.findByUsername("admin_aindefla").get().setRoles(roleRepository.findAll(List.of("ADMIN")));
		// userRepository.findByUsername("dr_aindefla").get().setRoles(roleRepository.findAll(List.of("SUPERVISOR")));
		// userRepository.findByUsername("sdr_aindefla").get().setRoles(roleRepository.findAll(List.of("SDR")));
		// userRepository.findByUsername("sdp_aindefla").get().setRoles(roleRepository.findAll(List.of("SDP")));
		// userRepository.findByUsername("sdf_aindefla").get().setRoles(roleRepository.findAll(List.of("SDF")));
		// userRepository.findByUsername("cm_aindefla").get().setRoles(roleRepository.findAll(List.of("CM")));
		// List<User> users = new ArrayList<>();
		// users.add(userRepository.findByUsername("esselami").get().setRoles(roleRepository.findAll(List.of("SUPERADMIN"))));
		// UserRepository.saveAll(userRepository.findByUsername("esselami").get().setRoles(roleRepository.findAll(List.of("SUPERADMIN"))));
		// List<Role> roles = roleRepository.findByName("SDCM");
		// User u = userRepository.findByUsername("cm_aindefla").get();
		// u.setRoles(roles);
		// userRepository.save(u);
		for (User user : userRepository.findAll()) {
			for (Role role : user.getRoles()) {
				System.out.println(user.getUsername() + " ===== " + role.getName());
			}

		}

		return "redirect:/swagger-ui.html";

	}
}
