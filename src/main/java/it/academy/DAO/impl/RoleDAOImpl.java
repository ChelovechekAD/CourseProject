package it.academy.DAO.impl;

import it.academy.DAO.RoleDAO;
import it.academy.enums.RoleEnum;
import it.academy.models.Role;
import it.academy.models.Role_;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RoleDAOImpl extends DAOImpl<Role, Short> implements RoleDAO {
    public RoleDAOImpl() {
        super(Role.class);
    }

    public RoleDAOImpl(TransactionHelper transactionHelper) {
        super(Role.class, transactionHelper);
    }

    @Override
    public Role getByRoleName(RoleEnum roleEnum) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Role> cq = cb.createQuery(Role.class);
        Root<Role> root = cq.from(Role.class);
        cq.select(root)
                .where(cb.equal(root.get(Role_.ROLE), roleEnum));
        return transactionHelper.entityManager()
                .createQuery(cq)
                .getSingleResult();
    }
}
