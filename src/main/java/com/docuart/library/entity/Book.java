package com.docuart.library.entity;

import com.docuart.library.enums.BookStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name")
    @NotNull
    @NotBlank
    @Max(30)
    private String bookName;

    @Column(name = "summary", length = 1000)
    @NotNull
    @NotBlank
    private String summary;

    @NotNull
    @NotBlank
    private String author;

    @NotNull
    @NotBlank
    private Integer quantity;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private BookStatusEnum status = BookStatusEnum.AKTIF;


    @Column(name = "creation_time", updatable = false)
    private LocalDateTime creationTime;
}
