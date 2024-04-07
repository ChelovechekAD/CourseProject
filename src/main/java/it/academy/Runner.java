package it.academy;

import it.academy.DAO.CartItemDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.CartItemDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.UpdateUserDTO;
import it.academy.services.UserService;
import it.academy.services.impl.UserServiceImpl;
import it.academy.utilities.HibernateUtil;

public class Runner {

    public static void main(String[] args){

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
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        cartItemDAO.getAllByUserId(1L).forEach(System.out::println);
        //userDAO.delete(1L);

    }

}
