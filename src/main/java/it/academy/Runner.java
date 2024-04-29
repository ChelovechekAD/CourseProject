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
import it.academy.utilities.TransactionHelper;

import java.util.stream.IntStream;

public class Runner {

    public static void main(String[] args) {

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

        CategoryDAO categoryDAO = new CategoryDAOImpl(transactionHelper);
        transactionHelper.transaction(() -> categoryDAO.create(Category.builder().categoryName("Стулья").build()));
        transactionHelper.transaction(() -> categoryDAO.create(Category.builder().categoryName("Столы").build()));
        transactionHelper.transaction(() -> categoryDAO.create(Category.builder().categoryName("Шкафы").build()));
        transactionHelper.transaction(() -> categoryDAO.create(Category.builder().categoryName("Диваны").build()));
        transactionHelper.transaction(() -> categoryDAO.create(Category.builder().categoryName("Двери").build()));

        
        
        ProductDAO productDAO = new ProductDAOImpl(transactionHelper);
        Category category = categoryDAO.get(2L);
       

        

        IntStream.range(0, 120)
                .forEach(i->{
                    Runnable runnable = () -> {
                        Product product1 = Product.builder()
                                .price(19329.9+i*10)
                                .name("Стул белый " + i)
                                .categoryId(category)
                                .description("Loreal parisssssh aga net no da disc est okey da nado bolshe")
                                .imageLink("chair-grey.jpg")
                                .rating(10D)
                                .build();
                        productDAO.create(product1);
                    };
                    transactionHelper.transaction(runnable);
                });



        RoleDAO roleDAO = new RoleDAOImpl(transactionHelper);
        Role role = Role.builder()
                .role(RoleEnum.ADMIN)
                .build();
        Runnable runnable = () -> {
            roleDAO.create(role);
        };

        transactionHelper.transaction(runnable);
    }

}
