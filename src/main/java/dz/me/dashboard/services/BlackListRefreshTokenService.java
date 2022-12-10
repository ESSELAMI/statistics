package dz.me.dashboard.services;

import java.util.Date;
import java.util.Optional;

import dz.me.dashboard.entities.BlackListRefreshToken;
import dz.me.dashboard.models.Token;

public interface BlackListRefreshTokenService {

	public Optional<BlackListRefreshToken> findById(String token);

	public BlackListRefreshToken save(Token blackListRefreshToken, Token AccesToken, Date generatedOn);

}
