package com.example.Library_Management_System.Repositories;

import com.example.Library_Management_System.Enums.Genre;
import com.example.Library_Management_System.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findBooksByGenre(Genre genre);
}
