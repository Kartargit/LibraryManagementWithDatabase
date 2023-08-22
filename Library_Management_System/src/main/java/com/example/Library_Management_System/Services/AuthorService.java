package com.example.Library_Management_System.Services;

import com.example.Library_Management_System.Models.Author;
import com.example.Library_Management_System.Repositories.AuthorRepository;
import com.example.Library_Management_System.RequestDtos.UpdateNameAndPenNameRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public String addAuthor(Author author) throws Exception{
        if(author.getAuthorId()!=null){
            throw new Exception("Id should not be sent as parameter");
        }
        authorRepository.save(author);
        return "Author has been added to database";
    }
    public String updateNameAndPenName(UpdateNameAndPenNameRequestDto requestDto) throws Exception{
        Optional<Author> authorOptional = authorRepository.findById(requestDto.getAuthorId());
        if(!authorOptional.isPresent()){
            throw new Exception("Invalid authorId");
        }
        Author author = authorOptional.get();

        author.setName(requestDto.getNewName());
        author.setPenName(requestDto.getPenName());

        authorRepository.save(author);
        return "Author Name and PenName are updated";

    }
    public List<Author> getAuthorByAgeGreaterThan(Integer age){
        List<Author> authorListByAgeGreater = authorRepository.findAuthorByGreaterThanAge(age);
        return authorListByAgeGreater;
    }
}
