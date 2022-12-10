package dz.me.dashboard.restcontroller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dz.me.dashboard.entities.GroupService;
import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.Role;
import dz.me.dashboard.entities.Service;
import dz.me.dashboard.entities.Structure;
import dz.me.dashboard.entities.Traitement;
import dz.me.dashboard.entities.User;
import dz.me.dashboard.entities.Wilaya;
import dz.me.dashboard.repositories.ArchiveTraitementRepository;
import dz.me.dashboard.repositories.GroupServiceRepository;
import dz.me.dashboard.repositories.GuichetRepository;
import dz.me.dashboard.repositories.RoleRepository;
import dz.me.dashboard.repositories.ServiceRepository;
import dz.me.dashboard.repositories.StructureRepository;
import dz.me.dashboard.repositories.TicketRepository;
import dz.me.dashboard.repositories.TraitementRepository;
import dz.me.dashboard.repositories.UserRepository;
import dz.me.dashboard.repositories.WilayaRepository;

@Controller
public class IndexController {

	@Autowired
	private GuichetRepository guichetRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private StructureRepository structureRepository;

	@Autowired
	private WilayaRepository wilayaRepository;

	@Autowired
	private ArchiveTraitementRepository archiveTraitementRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	GroupServiceRepository groupServiceRepository;

	@Autowired
	TraitementRepository traitementRepository;

	// ALTER DATABASE file_attente CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	// ALTER TABLE structure CONVERT TO CHARACTER SET utf8mb4 COLLATE
	// utf8mb4_unicode_ci;
	// ALTER TABLE service CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexOperation() {
		System.out.println("cc");
		archiveTraitementRepository.deleteAll();

		ticketRepository.deleteAll();

		archiveTraitementRepository.deleteAll();
		guichetRepository.deleteAll();

		archiveTraitementRepository.deleteAll();

		ticketRepository.deleteAll();

		userRepository.deleteAll();

		roleRepository.deleteAll();

		serviceRepository.deleteAll();

		groupServiceRepository.deleteAll();

		traitementRepository.deleteAll();

		structureRepository.deleteAll();

		wilayaRepository.deleteAll();

		Role role = new Role("ADMIN");

		roleRepository.save(role);
		Traitement t1 = new Traitement("Dépo Arret de travail", "CP1");
		Traitement t2 = new Traitement("Dépo Ordonnance(s)", "CP2");
		Traitement t3 = new Traitement("Attestation d'affilication", "CP3");
		Traitement t4 = new Traitement("Affiliation", "CP4");
		Traitement t5 = new Traitement("Ouverture des droits", "CP5");
		Traitement t6 = new Traitement("Demande carte Chifa", "CP6");
		Traitement t7 = new Traitement("delivrence carte hors-chifa", "CP7");
		Traitement t8 = new Traitement("Dépo dossier maternite", "CP8");
		Traitement t9 = new Traitement("Dépo capital deces", "CP9");

		Traitement t10 = new Traitement("Control a priori", "CM1");
		Traitement t11 = new Traitement("Control a posteriori", "CM2");
		Traitement t12 = new Traitement("Control Arret de travail", "CM3");
		Traitement t13 = new Traitement("Assures convoqué", "COM1");
		Traitement t14 = new Traitement("Information", "COM2");
		Traitement t15 = new Traitement("Absent(e)", "COM3");
		t1 = traitementRepository.save(t1);
		t2 = traitementRepository.save(t2);
		t3 = traitementRepository.save(t3);
		t4 = traitementRepository.save(t4);
		t5 = traitementRepository.save(t5);
		t6 = traitementRepository.save(t6);
		t7 = traitementRepository.save(t7);
		t8 = traitementRepository.save(t8);
		t9 = traitementRepository.save(t9);
		t10 = traitementRepository.save(t10);
		t11 = traitementRepository.save(t11);
		t12 = traitementRepository.save(t12);
		t13 = traitementRepository.save(t13);
		t14 = traitementRepository.save(t14);
		t15 = traitementRepository.save(t15);

		Wilaya wilaya = new Wilaya();
		wilaya.setCodeWilaya("25");
		wilaya.setWilaya("Constantine");
		wilayaRepository.save(wilaya);

		Structure st = new Structure();
		st.setStructure("SIEGE");
		st.setWilaya(wilaya);
		Structure st1 = new Structure();
		st1.setStructure("Structure Ali Mendjeli");
		st1.setWilaya(wilaya);

		structureRepository.save(st);
		structureRepository.save(st1);

		GroupService groupService = new GroupService("Group 1");
		groupService.setStructure(st);
		GroupService groupService1 = new GroupService("Group 2");
		groupService1.setStructure(st);
		groupServiceRepository.save(groupService);
		groupServiceRepository.save(groupService1);

		Service service = new Service();
		service.setService("Service des Prestations");
		service.setServiceAr("الأداءات");
		service.setServiceLettre("A");
		service.setGroupService(groupService);
		Service service1 = new Service();
		Set<Traitement> traitements = new HashSet<>();
		traitements.add(t1);
		traitements.add(t2);
		traitements.add(t3);
		traitements.add(t4);
		traitements.add(t5);
		traitements.add(t6);
		traitements.add(t7);
		traitements.add(t8);
		traitements.add(t9);
		traitements.add(t13);
		traitements.add(t14);
		traitements.add(t15);
		service.setTraitements(traitements);
		service1.setService("Service Control Medical");
		service1.setServiceAr("الرقابة الطبية");
		service1.setServiceLettre("B");
		service1.setGroupService(groupService);
		Set<Traitement> traitements1 = new HashSet<>();
		traitements1.add(t10);
		traitements1.add(t11);
		traitements1.add(t12);
		traitements1.add(t13);
		traitements1.add(t14);
		traitements1.add(t15);
		service1.setTraitements(traitements1);
		serviceRepository.save(service);
		serviceRepository.save(service1);

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
		user1.setFirstname("Abdelatif");
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

		Guichet guichet = new Guichet();
		guichet.setGuichet(1);
		guichet.setName("Guichet 01");
		guichet.setService(service);
		guichetRepository.save(guichet);
		Guichet guichet1 = new Guichet();
		guichet1.setGuichet(2);
		guichet1.setName("Guichet 02");
		guichet1.setService(service);

		guichetRepository.save(guichet1);

		Guichet guichet3 = new Guichet();
		guichet3.setGuichet(1);
		guichet3.setName("Guichet 01");
		guichet3.setService(service1);
		guichetRepository.save(guichet3);

		return "redirect:/swagger-ui.html";

	}
}
