package com.pocketbook.userservice.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, nullable = false, length = 30)
    private String userName;
    @Column(unique = true, nullable = false, length = 30)
    private String userEmail;
}
