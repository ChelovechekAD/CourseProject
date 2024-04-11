package it.academy.DAO.impl;

import it.academy.DAO.RefreshTokenDAO;
import it.academy.exceptions.TokenNotFound;
import it.academy.models.RefreshToken;
import it.academy.utilities.Constants;
import it.academy.utilities.TransactionHelper;

public class RefreshTokenDAOImpl extends DAOImpl<RefreshToken, Long> implements RefreshTokenDAO {

    public RefreshTokenDAOImpl() {
        super(RefreshToken.class);
    }

    public RefreshTokenDAOImpl(TransactionHelper transactionHelper) {
        super(RefreshToken.class, transactionHelper);
    }


    @Override
    public RefreshToken getTokenByEmail(String email) {
        String param = Constants.SELECT_FROM_REFRESH_TOKEN_WHERE_EMAIL.substring(
                Constants.SELECT_FROM_REFRESH_TOKEN_WHERE_EMAIL.lastIndexOf(":") + 1);
        return transactionHelper.entityManager()
                .createQuery(Constants.SELECT_FROM_REFRESH_TOKEN_WHERE_EMAIL, RefreshToken.class)
                .setParameter(param, email)
                .getSingleResult();
    }

    @Override
    public Boolean existTokenByEmail(String email) {
        String param = Constants.SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_EMAIL.substring(
                Constants.SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_EMAIL.lastIndexOf(":") + 1);
        return transactionHelper.entityManager()
                .createQuery(Constants.SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_EMAIL, Long.class)
                .setParameter(param, email)
                .getSingleResult() != 0;
    }

    @Override
    public Boolean existTokenByValue(String refreshTokenValue) {
        String param = Constants.SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_REFRESH_TOKEN.substring(
                Constants.SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_REFRESH_TOKEN.lastIndexOf(":") + 1);
        return transactionHelper.entityManager()
                .createQuery(Constants.SELECT_COUNT_FROM_REFRESH_TOKEN_WHERE_REFRESH_TOKEN, Long.class)
                .setParameter(param, refreshTokenValue)
                .getSingleResult() != 0;
    }

    @Override
    public Boolean deleteTokenByValue(String refreshTokenValue) {
        int rowAffected = transactionHelper.entityManager()
                .createQuery(Constants.DELETE_FROM_REFRESH_TOKEN_WHERE_REFRESH_TOKEN)
                .executeUpdate();
        if (rowAffected == 0) {
            throw new TokenNotFound();
        }
        return true;
    }
}
