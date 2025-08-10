package com.example.Commentator.entity;


import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String username;
}
