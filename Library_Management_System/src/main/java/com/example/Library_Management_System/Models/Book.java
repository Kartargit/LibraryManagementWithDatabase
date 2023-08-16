package com.example.Library_Management_System.Models;

import com.example.Library_Management_System.Enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(unique = true)
    private String title;
    private Integer price;
    private Boolean isAvailable;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private Date publicationDate;

    @ManyToOne
    @JoinColumn
    private Author author;

    public Book(String title, Integer price, boolean available, Genre genre, Date publicationDate) {

    }
}
