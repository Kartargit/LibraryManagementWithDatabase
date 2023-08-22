package com.example.Library_Management_System.Models;

import com.example.Library_Management_System.Enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonIgnore
    private Author author;

    public Book(String title, Integer price, boolean available, Genre genre, Date publicationDate) {
        this.title = title;
        this.genre = genre;
        this.isAvailable = available;
        this.price = price;
        this.publicationDate = publicationDate;
    }
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<Transactions> transactionsList = new ArrayList<>();
}
