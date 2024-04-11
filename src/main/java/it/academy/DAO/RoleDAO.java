package it.academy.DAO;

import it.academy.enums.RoleEnum;
import it.academy.models.Role;

public interface RoleDAO extends DAO<Role, Short> {

    Role getByRoleName(RoleEnum roleEnum);

}
