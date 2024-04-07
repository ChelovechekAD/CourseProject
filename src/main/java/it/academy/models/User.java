package it.academy.models;

import it.academy.models.embedded.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false)
    private String name;
    @Column (nullable = false)
    private String surname;
    @Column (nullable = false)
    private String email;
    @Column (nullable = false)
    private String password;
    @Column
    @Embedded
    private Address address;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "roles", nullable = false)
    @ManyToMany
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roleSet = new HashSet<>();

}
