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

		return "redirect:/swagger-ui.html";

	}
}
