package dz.me.dashboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.me.dashboard.entities.BlackListRefreshToken;

/**
 *
 * @author ABDELLATIF ESSELAMI
 */
public interface BlackListJwtRepository extends JpaRepository<BlackListRefreshToken, String> {

}
