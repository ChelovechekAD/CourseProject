package it.academy.DAO;

import it.academy.models.User;

import java.util.UUID;

public interface UserDAO extends DAO<User, Long>{
    User getUserByEmail(String email);
    Boolean existUserByEmail(String email);
}
