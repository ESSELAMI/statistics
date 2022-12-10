package dz.me.dashboard.restcontroller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import dz.me.dashboard.entities.BlackListRefreshToken;
import dz.me.dashboard.entities.Guichet;
import dz.me.dashboard.entities.RefreshToken;
import dz.me.dashboard.entities.TentativeAcces;
import dz.me.dashboard.filters.JwtUtils;
import dz.me.dashboard.models.LoginRequest;
import dz.me.dashboard.repositories.GroupServiceRepository;
import dz.me.dashboard.repositories.GuichetRepository;
import dz.me.dashboard.repositories.RefreshTokenRepository;
import dz.me.dashboard.repositories.RoleRepository;
import dz.me.dashboard.repositories.UserRepository;
import dz.me.dashboard.services.BlackListRefreshTokenService;
import dz.me.dashboard.services.GuichetService;
import dz.me.dashboard.services.TentativeAccesService;
import dz.me.dashboard.services.TicketService;
import dz.me.dashboard.services.UserService;
import dz.me.dashboard.utils.ResponseEntityUtils;
import dz.me.dashboard.utils.UtilsIP;

/**
 *
 * @author MEKRICHE TAREK
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth/login")
public class AuthenticateController {

	// Injections
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtDetailsService;
	private RefreshTokenRepository refreshTokenRepository;
	private BlackListRefreshTokenService blackListRefreshTokenService;
	private TentativeAccesService tentativeAccesService;
	private GuichetService guichetService;
	private UserService userService;

	// variables
	private String jwtRefreshToken;
	private String jwtAccessToken;

	/*
	 * @Autowired
	 * private ServiceRepository serviceRepository;
	 * 
	 * @Autowired
	 * private StructureRepository structureRepository;
	 * 
	 * @Autowired
	 * private WilayaRepository wilayaRepository;
	 */

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	GroupServiceRepository groupServiceRepository;

	@Autowired
	GuichetRepository guichetRepository;

	@Autowired
	TicketService ticketService;

	public AuthenticateController(TentativeAccesService tentativeAccesService,
			AuthenticationManager authenticationManager, JwtUtils jwtDetailsService,
			RefreshTokenRepository refreshTokenRepository, BlackListRefreshTokenService blackListRefreshTokenService,
			UserService userService, GuichetService guichetService) {
		super();
		this.tentativeAccesService = tentativeAccesService;
		this.authenticationManager = authenticationManager;
		this.jwtDetailsService = jwtDetailsService;
		this.refreshTokenRepository = refreshTokenRepository;
		this.blackListRefreshTokenService = blackListRefreshTokenService;
		this.userService = userService;
		this.guichetService = guichetService;
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest,
			HttpServletRequest request) {

		/*
		 * Optional<dz.me.filleattente.entities.User> u =
		 * userRepository.findByUsername("Admin");
		 * /* if (!u.isPresent()) {
		 * saveAdmin();
		 * }
		 */
		Authentication authResult;
		// recuperer les informations general : ip,tentative login,...

		String ip = UtilsIP.getClientIpAddr(request);

		// verifier l'acces avec l'@ip simultané

		try {
			tentativeAccesService.validerAcces(request);
		} catch (Exception e) {
			return ResponseEntityUtils.ExceptionResponseEntity(e.getMessage(),
					HttpStatus.FORBIDDEN.value());
		}

		// encode le password pour la verification
		try {

			authenticationRequest.setPassword(authenticationRequest.getPassword());

			authResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		} catch (Exception e) {

			// augmenter le nombres de tentative
			int tentative = tentativeAccesService.getNombreTentative(ip);
			tentative++;
			TentativeAcces tentativeAcces = new TentativeAcces(tentative, tentativeAccesService.sysdate(), ip);
			tentativeAccesService.addTentative(tentativeAcces);
			return ResponseEntityUtils.ExceptionResponseEntity("Utilisateur et mot de passe sont incorrects",
					HttpStatus.FORBIDDEN.value());
		}

		User user = (User) authResult.getPrincipal();

		Algorithm algorithmA = Algorithm.HMAC256(jwtDetailsService.getJwtSecret());
		Algorithm algorithmR = Algorithm.HMAC256(jwtDetailsService.getJwtRefreshSecret());
		dz.me.dashboard.entities.User userRepo = userService.findByUsername(user.getUsername()).get();

		// chercher si le guichet existe reelement dans le service;
		Optional<Guichet> guichet = guichetService.findByServiceAndGuichet(userRepo.getService(),
				authenticationRequest.getGuichet());

		if (!guichet.isPresent()) {
			// augmenter le nombres de tentative
			int tentative = tentativeAccesService.getNombreTentative(ip);
			tentative++;
			TentativeAcces tentativeAcces = new TentativeAcces(tentative, tentativeAccesService.sysdate(), ip);
			tentativeAccesService.addTentative(tentativeAcces);

			// Guichet Inexistant
			return ResponseEntityUtils.ExceptionResponseEntity(
					"Vous devez Contacter Votre Admin afin de creer le nouveau Guichet relié a ce service",
					HttpStatus.NOT_FOUND.value());
		}
		// genere le access token
		jwtAccessToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtDetailsService.getJwtExpirationMs()))
				.withIssuer(UtilsIP.getClientIpAddr(request))
				.withClaim("roles",
						user.getAuthorities().stream().map(ga -> ga.getAuthority())
								.collect(Collectors.toList()))
				.withClaim("service-id", userRepo.getService().getId().toString())
				.withClaim("user-id", userRepo.getId().toString())
				.withClaim("guichet-number", guichet.get().getGuichet())
				.withClaim("guichet-id", guichet.get().getId().toString())
				.withClaim("name", userRepo.getLastname() + " " + userRepo.getFirstname())

				.sign(algorithmA);

		/*
		 * Traitement refresh token s il n y a pas de refresh token generer un nouveau
		 * sinon si le refresh token est expiré ou dans le black liste generener un
		 * nouveau sinon recupere l'ancien refresh token
		 **/
		Optional<RefreshToken> RefreshToken = refreshTokenRepository.findById(user.getUsername());
		if (RefreshToken.isPresent()) {

			// genere le refresh token
			Optional<BlackListRefreshToken> blackListRefreshToken = blackListRefreshTokenService
					.findById(RefreshToken.get().getToken());

			if (RefreshToken.get().getExpired().before(new Date()) || blackListRefreshToken.isPresent()) {

				jwtRefreshToken = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(
								new Date(System.currentTimeMillis() + jwtDetailsService.getJwtRefreshExpirationMs()))
						.withIssuer(UtilsIP.getClientIpAddr(request))
						.sign(algorithmR);
				refreshTokenRepository.save(new RefreshToken(user.getUsername(), jwtRefreshToken,
						new Date(System.currentTimeMillis() + jwtDetailsService.getJwtRefreshExpirationMs()),
						new Date(System.currentTimeMillis()), UtilsIP.getClientIpAddr(request)));

			} else {

				jwtRefreshToken = RefreshToken.get().getToken();
			}

		} else {

			jwtRefreshToken = JWT.create().withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + jwtDetailsService.getJwtRefreshExpirationMs()))
					.withIssuer(UtilsIP.getClientIpAddr(request))
					.sign(algorithmR);

			refreshTokenRepository.save(new RefreshToken(user.getUsername(), jwtRefreshToken,
					new Date(System.currentTimeMillis() + jwtDetailsService.getJwtRefreshExpirationMs()),
					new Date(System.currentTimeMillis()), UtilsIP.getClientIpAddr(request)));
		}

		Map<String, String> idToken = new HashMap<>();
		idToken.put("access_token", jwtAccessToken);
		idToken.put("refresh_token", jwtRefreshToken);

		/* reinitialiser le nombre de tentative */

		TentativeAcces tentativeAcces = new TentativeAcces(0, tentativeAccesService.sysdate(), ip);
		tentativeAccesService.addTentative(tentativeAcces);
		ticketService.deleteOld();
		return ResponseEntity.ok(idToken);
	}

	/*
	 * private void saveAdmin() {
	 * Role role1;
	 * Optional<Role> role = roleRepository.findByName("ADMIN");
	 * if (!roleRepository.findByName("ADMIN").isPresent()) {
	 * role1 = new Role("ADMIN");
	 * } else {
	 * role1 = role.get();
	 * }
	 * roleRepository.save(role1);
	 * Wilaya wilaya = new Wilaya();
	 * wilaya.setCodeWilaya("100");
	 * wilaya.setWilaya("Administrateur");
	 * wilayaRepository.save(wilaya);
	 * Structure st = new Structure();
	 * st.setStructure("Administrateur");
	 * st.setWilaya(wilaya);
	 * 
	 * structureRepository.save(st);
	 * 
	 * GroupService groupService = new GroupService("Administrateur");
	 * groupService.setStructure(st);
	 * 
	 * groupServiceRepository.save(groupService);
	 * 
	 * Service service = new Service();
	 * service.setService("Administrateur");
	 * service.setServiceAr("Administrateur");
	 * service.setServiceLettre("A");
	 * service.setGroupService(groupService);
	 * 
	 * serviceRepository.save(service);
	 * 
	 * dz.me.filleattente.entities.User user = new
	 * dz.me.filleattente.entities.User();
	 * user.setEmail("Administrateur@gmail.com");
	 * user.setFirstname("Admin");
	 * user.setLastname("Admin");
	 * user.setPassword(
	 * "$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.");
	 * Set<Role> roles = new HashSet<>();
	 * roles.add(role1);
	 * user.setRoles(roles);
	 * user.setAccountNonExpired(true);
	 * user.setAccountNonLocked(true);
	 * user.setCredentialsNonExpired(true);
	 * user.setEnabled(true);
	 * user.setService(service);
	 * user.setUsername("Admin");
	 * 
	 * userRepository.save(user);
	 * 
	 * Guichet guichet = new Guichet();
	 * guichet.setGuichet(1);
	 * guichet.setName("Guichet 01");
	 * guichet.setService(service);
	 * guichetRepository.save(guichet);
	 * 
	 * }
	 */
}
