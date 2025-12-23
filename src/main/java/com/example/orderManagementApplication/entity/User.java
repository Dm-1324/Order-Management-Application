package com.example.orderManagementApplication.entity;

import com.example.orderManagementApplication.entity.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Email cannot be empty")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Status cannot be empty")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

}
