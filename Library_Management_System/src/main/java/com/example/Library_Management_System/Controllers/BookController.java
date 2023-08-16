package com.example.Library_Management_System.Controllers;

import com.example.Library_Management_System.RequestDtos.AddBookRequestDto;
import com.example.Library_Management_System.Services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/addBook")
public class BookController {
    @Autowired
    private BookService bookService;
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
}
