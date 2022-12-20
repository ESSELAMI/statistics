package dz.me.dashboard.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import dz.me.dashboard.entities.RefreshToken;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

	Optional<RefreshToken> findByUsername(String username);

}
