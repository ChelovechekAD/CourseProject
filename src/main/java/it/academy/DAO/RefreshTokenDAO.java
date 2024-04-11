package it.academy.DAO;

import it.academy.models.RefreshToken;

public interface RefreshTokenDAO extends DAO<RefreshToken, Long> {

    RefreshToken getTokenByEmail(String email);

    Boolean existTokenByEmail(String email);

    Boolean existTokenByValue(String refreshTokenValue);

    Boolean deleteTokenByValue(String refreshTokenValue);


}
