package com.example.Library_Management_System.ResponseDtos;

import com.example.Library_Management_System.Enums.Genre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
//    custom response variables which we are responding
    private String title;

    private Integer price;

    private Boolean isAvailable;

    private Genre genre;

    private Date publicationDate;

    private String authorName;
}
