package it.academy.utilities;

import it.academy.DTO.request.*;
import it.academy.DTO.response.*;
import it.academy.enums.RoleEnum;
import it.academy.models.*;
import it.academy.models.embedded.Address;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Converter {

    public static Product convertProdDTOToEntity(ProductDTO productDTO){
        return Product.builder()
                .id(productDTO.getId())
                .categoryId(null)
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .name(productDTO.getName())
                .imageLink(productDTO.getImageLink())
                .build();
    }
    public static ProductDTO convertProdEntityToDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .categoryId(product.getId())
                .description(product.getDescription())
                .imageLink(product.getImageLink())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public static OrderItem convertOrderItemDTOToEntity(OrderItemDTO orderItemDTO){
        return OrderItem.builder()
                .orderItemPK(null)
                .count(orderItemDTO.getCount())
                .price(orderItemDTO.getPrice())
                .build();
    }

    public static OrderItemProductDTO convertOrderItemAndProductEntitiesToDTO(OrderItem orderItem, Product product) {
        return OrderItemProductDTO.builder()
                .productId(product.getId())
                .image_link(product.getImageLink())
                .name(product.getName())
                .count(orderItem.getCount())
                .price(orderItem.getPrice())
                .build();
    }

    public static OrderDTO convertOrderEntityToDTO(Order order, Long countOfItems){
        User user = order.getUserId();
        return OrderDTO.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .date(order.getCreatedAt())
                .countOfItems(countOfItems)
                .orderUserDTO(OrderUserDTO.builder()
                        .id(user.getId())
                        .street(user.getAddress().getStreet())
                        .building(user.getAddress().getBuilding())
                        .city(user.getAddress().getCity())
                        .email(user.getEmail())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .build();
    }

    public static ProductsDTO convertProdListToDTO(List<Product> products, Long count){
        List<ProductDTO> productDTOList = products.stream()
                .map(Converter::convertProdEntityToDTO)
                .collect(Collectors.toList());
        return new ProductsDTO(productDTOList, count);
    }

    public static CategoryDTO convertCategoryEntityToDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public static Category convertCategoryDTOToEntity(CategoryDTO categoryDTO){
        return Category.builder()
                .id(categoryDTO.getId())
                .categoryName(categoryDTO.getCategoryName())
                .build();
    }
    public static CategoriesDTO convertCategoriesListToDTO(List<Category> categories, Long count){
        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(Converter::convertCategoryEntityToDTO)
                .collect(Collectors.toList());
        return CategoriesDTO.builder()
                .categoryDTOList(categoryDTOList)
                .countOfCategories(count)
                .build();
    }

    public static User convertRegUserDTOToEntity(RegUserDTO regUserDTO){
        return User.builder()
                .email(regUserDTO.getEmail())
                .name(regUserDTO.getName())
                .address(Address.builder()
                        .city(regUserDTO.getCity())
                        .street(regUserDTO.getStreet())
                        .building(regUserDTO.getBuilding())
                        .build())
                .password(regUserDTO.getPassword())
                .phoneNumber(regUserDTO.getPhoneNumber())
                .surname(regUserDTO.getSurname())
                .build();
    }

    public static void updateUserByDTO(User user, UpdateUserDTO dto){
        Address address = Address.builder()
                .building(dto.getBuilding())
                .street(dto.getStreet())
                .city(dto.getCity())
                .build();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setAddress(address);
    }

    public static UserDTO convertUserEntityToDTO(User user){
        List<String> roles = user.getRoleSet().stream()
                .map(r -> r.getRole().name())
                .collect(Collectors.toList());
        return UserDTO.builder()
                .id(user.getId())
                .street(user.getAddress().getStreet())
                .building(user.getAddress().getBuilding())
                .city(user.getAddress().getCity())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .roles(roles)
                .build();
    }

    public static CartItemDTO convertListCartItemEntityToListDTO(CartItem cartItemList){
        return CartItemDTO.builder()
                .count(cartItemList.getQuantity())
                .productId(cartItemList.getCartItemPK().getProductId().getId())
                .imageLink(cartItemList.getCartItemPK().getProductId().getImageLink())
                .name(cartItemList.getCartItemPK().getProductId().getName())
                .price(cartItemList.getCartItemPK().getProductId().getPrice())
                .build();
    }

    public static ReviewDTO convertReviewEntityToDTO(Review entity){
        return ReviewDTO.builder()
                .userId(entity.getReviewPK().getUserId().getId())
                .description(entity.getDescription())
                .name(entity.getReviewPK().getUserId().getName())
                .surname(entity.getReviewPK().getUserId().getSurname())
                .rating(entity.getRating())
                .build();
    }
    public static ReviewsDTO convertListReviewEntityToDTO(List<Review> reviewList, Integer count){
        List<ReviewDTO> list = reviewList.stream()
                .map(Converter::convertReviewEntityToDTO)
                .collect(Collectors.toList());
        return new ReviewsDTO(list, count);
    }
}