package it.academy.DAO;

import it.academy.models.User;

public interface UserDAO extends DAO<User, Long> {
    User getUserByEmail(String email);

    Boolean existUserByEmail(String email);
}
