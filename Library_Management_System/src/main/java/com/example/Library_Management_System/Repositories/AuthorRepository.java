package com.example.Library_Management_System.Repositories;

import com.example.Library_Management_System.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
//    custom manual query
    @Query(value = "select * from author where age >= :enteredAge",nativeQuery = true)
    List<Author> findAuthorByGreaterThanAge(Integer enteredAge);
}
