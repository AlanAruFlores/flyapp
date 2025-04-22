package com.flybook.librovuelo.model;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;
    private String refreshToken;

    private LocalDateTime tokenExpiration;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @OneToOne
    @Unique
    private User user;
}
