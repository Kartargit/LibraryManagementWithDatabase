package com.example.Library_Management_System.Controllers;

import com.example.Library_Management_System.Models.LibraryCard;
import com.example.Library_Management_System.Services.LibraryCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private LibraryCardService cardService;

    @PostMapping("/create")
    public String addCard(@RequestBody LibraryCard card){
        return cardService.addLibraryCard(card);
    }
    @PutMapping("/issueCardToStudent")
    public ResponseEntity issueCardToStudent(@RequestParam("cardId")Integer cardId,@RequestParam("rollNo")Integer rollNo){
        try {
            String result = cardService.associateCardToStudent(cardId,rollNo);
            return new ResponseEntity(result, HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("Error in associating card to student {}",e.getMessage());
            return new ResponseEntity(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
    }
}
