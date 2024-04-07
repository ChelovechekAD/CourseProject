package it.academy.models;

import it.academy.models.embedded.ReviewPK;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review implements Serializable {

    @EmbeddedId
    private ReviewPK reviewPK;
    @Column (nullable = false)
    private Double rating;
    @Column(length = 500)
    private String description;


}
