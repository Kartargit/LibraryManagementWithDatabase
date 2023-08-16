package com.example.Library_Management_System.RequestDtos;

import com.example.Library_Management_System.Enums.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class AddBookRequestDto {
    private String title;
    private boolean isAvailable;
    private Genre genre;
    private Date publicationDate;
    private Integer price;
    private Integer authorId;
}
