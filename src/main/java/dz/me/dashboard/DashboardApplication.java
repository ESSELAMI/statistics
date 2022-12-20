package dz.me.dashboard;

import java.util.Date;
import java.util.TimeZone;
import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
@SpringBootApplication
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

	@PostConstruct
	public void init() {
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+01:00"));
		System.out.println("-------------------------------------------------------------------------GMT in UTC: "
				+ new Date().toString());
		// TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		// System.out.println("-------------------------------------------------------------------------Date
		// in UTC: "
		// + new Date().toString());
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
