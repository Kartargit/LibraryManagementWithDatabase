package com.example.Library_Management_System.Controllers;

import com.example.Library_Management_System.Enums.Genre;
import com.example.Library_Management_System.RequestDtos.AddBookRequestDto;
import com.example.Library_Management_System.ResponseDtos.BookResponseDto;
import com.example.Library_Management_System.Services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addBook")
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping("/addBook")
    public ResponseEntity addBook(@RequestBody AddBookRequestDto requestDto){
        try {
            String response = bookService.addBook(requestDto);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("Failure {}",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getByGenre")
    public ResponseEntity getBookListByGenre(@RequestParam("genre")Genre genre){
        List<BookResponseDto> bookResponseDtoList = bookService.getBookListByGenre(genre);
        return new ResponseEntity(bookResponseDtoList,HttpStatus.OK);
    }
}
