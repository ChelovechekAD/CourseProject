package it.academy;

import it.academy.DAO.CategoryDAO;
import it.academy.DAO.ProductDAO;
import it.academy.DAO.RoleDAO;
import it.academy.DAO.impl.CategoryDAOImpl;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DAO.impl.RoleDAOImpl;
import it.academy.enums.RoleEnum;
import it.academy.models.Category;
import it.academy.models.Product;
import it.academy.models.Role;
import it.academy.utilities.HibernateUtil;
import it.academy.utilities.TransactionHelper;

import java.util.stream.IntStream;

public class Runner {

    public static void main(String[] args){

        TransactionHelper transactionHelper = new TransactionHelper();
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

        /*ProductDAO productDAO = new ProductDAOImpl(transactionHelper);

        CategoryDAO categoryDAO = new CategoryDAOImpl(transactionHelper);

        IntStream.range(0, 120)
                .forEach(i->{
                    Runnable runnable = () -> {
                        Product product = productDAO.get(1L);
                        Product product1 = Product.builder()
                                .price(product.getPrice())
                                .name("Стол белый " + i)
                                .categoryId(product.getCategoryId())
                                .description(product.getDescription())
                                .price(product.getPrice() + 100)
                                .imageLink(product.getImageLink())
                                .rating(product.getRating())
                                .build();
                        System.out.println(productDAO.create(product1));
                    };
                    transactionHelper.transaction(runnable);
                });
*/
        RoleDAO roleDAO = new RoleDAOImpl();
        Role role = Role.builder()
                .role(RoleEnum.ADMIN)
                .build();
        Runnable runnable = () -> {
            roleDAO.create(role);
            System.out.println(123);
        };

        transactionHelper.transaction(runnable);
    }

}
