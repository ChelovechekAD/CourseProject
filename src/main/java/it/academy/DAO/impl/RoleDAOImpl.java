package it.academy.DAO.impl;

import it.academy.DAO.RoleDAO;
import it.academy.enums.RoleEnum;
import it.academy.models.Role;

public class RoleDAOImpl extends DAOImpl<Role, Short> implements RoleDAO {
    public RoleDAOImpl() {
        super(Role.class);
    }

    @Override
    public Role getByRoleName(RoleEnum roleEnum) {
        return transactionHelper.entityManager()
                .createQuery("select r from Role r where role = :roleName", Role.class)
                .setParameter("roleName", roleEnum)
                .getSingleResult();
    }
}
