package it.academy;

import it.academy.DAO.RoleDAO;
import it.academy.DAO.impl.RoleDAOImpl;
import it.academy.enums.RoleEnum;
import it.academy.models.Role;
import it.academy.utilities.HibernateUtil;
import it.academy.utilities.TransactionHelper;

public class Runner {

    public static void main(String[] args){

        TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();
        HibernateUtil.getEntityManager();
//        UserDAO userDAO = new UserDAOImpl();
//        UserService userService = new UserServiceImpl();
//        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder()
//                .id(1L)
//                .city("1234")
//                .building("123")
//                .name("asdasd")
//                .street("123asd")
//                .surname("asdfa")
//                .build();
//        userService.updateUser(updateUserDTO);
        /*CartItemDAO cartItemDAO = new CartItemDAOImpl();
        cartItemDAO.getAllByUserId(1L).forEach(System.out::println);*/
        //userDAO.delete(1L);

        RoleDAO roleDAO = new RoleDAOImpl();
        Role role = Role.builder()
                .role(RoleEnum.DEFAULT_USER)
                .build();
        transactionHelper.transaction(() -> roleDAO.create(role));
    }

}
