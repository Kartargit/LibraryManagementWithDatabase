package com.example.Library_Management_System.Controllers;

import com.example.Library_Management_System.Models.Author;
import com.example.Library_Management_System.RequestDtos.UpdateNameAndPenNameRequestDto;
import com.example.Library_Management_System.Services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorServiceObj ;
    @PostMapping("/addAuthor")
    public ResponseEntity addAuthor(@RequestBody Author author){
        try {
            String response = authorServiceObj.addAuthor(author);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("Author could not get added {}",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
    @PutMapping("/updateNameAndPenName")
    public ResponseEntity updateNameAndPenNameOfAuthor(@RequestBody UpdateNameAndPenNameRequestDto requestDto){
        try {
            String response = authorServiceObj.updateNameAndPenName(requestDto);
            return new ResponseEntity(response,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            log.error("Failed to update name and penName {}",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAuthorsByAge")
    public List<Author> getAuthorsByAgeGreater(@RequestParam("age")Integer age){
        return authorServiceObj.getAuthorByAgeGreaterThan(age);
    }
}
