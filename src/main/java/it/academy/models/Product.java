package it.academy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category categoryId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double rating;
    @Column(length = 1500, nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(name = "image_link")
    private String imageLink;


}
