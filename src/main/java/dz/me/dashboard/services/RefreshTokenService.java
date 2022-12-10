package dz.me.dashboard.services;

import java.util.Optional;

import dz.me.dashboard.entities.RefreshToken;
import dz.me.dashboard.models.AccessTokenModel;
import dz.me.dashboard.models.RefreshTokenRequest;

public interface RefreshTokenService {

	public Optional<RefreshToken> findById(String token);

	public Optional<RefreshToken> findByUsername(String username);

	public AccessTokenModel createAccessToken(RefreshTokenRequest refreshToken);

}
