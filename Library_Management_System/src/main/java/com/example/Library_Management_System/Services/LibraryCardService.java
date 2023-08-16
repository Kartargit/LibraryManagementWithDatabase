package com.example.Library_Management_System.Services;

import com.example.Library_Management_System.Models.LibraryCard;
import com.example.Library_Management_System.Models.Student;
import com.example.Library_Management_System.Repositories.LibraryCardRepository;
import com.example.Library_Management_System.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryCardService {
    @Autowired
    private LibraryCardRepository cardRepository;
    @Autowired
    private StudentRepository studentRepository;
    public String addLibraryCard(LibraryCard card){
        cardRepository.save(card);
        return "Card has been successfully added";
    }
    public String associateCardToStudent(Integer cardNo,Integer rollNo) throws Exception{
//        student exist or not validation first
        if (!studentRepository.existsById(rollNo)){
            throw new Exception("Invalid Id or rollNo");
        }
//        check card validation
        if(!cardRepository.existsById(cardNo)){
            throw new Exception("Invalid cardNo");
        }
//        update foreignKey variables
        Optional<Student> optional = studentRepository.findById(rollNo);
        Student studentObj = optional.get();

        Optional<LibraryCard> cardOptional = cardRepository.findById(cardNo);
        LibraryCard libraryCardObj = cardOptional.get();

        libraryCardObj.setStudent(studentObj);  //set the student object entity
        studentObj.setLibraryCard(libraryCardObj); //set the card object entity

        studentRepository.save(studentObj);

// this is optional in bi-direction mapping to sve child as it will be cascaded by parent
        //        cardRepository.save(libraryCardObj);
        return "Card has been issued to student";
    }
}
