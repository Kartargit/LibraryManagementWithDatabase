package com.example.Library_Management_System.Services;

import com.example.Library_Management_System.Models.Author;
import com.example.Library_Management_System.Models.Book;
import com.example.Library_Management_System.Repositories.AuthorRepository;
import com.example.Library_Management_System.Repositories.BookRepository;
import com.example.Library_Management_System.RequestDtos.AddBookRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    public String addBook(AddBookRequestDto requestDto) throws Exception{
//        validation of authorId
        Optional<Author> optionalAuthor = authorRepository.findById(requestDto.getAuthorId());
        if(!optionalAuthor.isPresent()){
            throw new Exception("AuthorId is inCorrect");
        }
        Book book = new Book(requestDto.getTitle(),requestDto.getPrice(),requestDto.isAvailable(),requestDto.getGenre(),requestDto.getPublicationDate());
        Author author = optionalAuthor.get();
        book.setAuthor(author);
        bookRepository.save(book);
        List<Book> list = author.getBookList();
        list.add(book);
        author.setBookList(list);
        authorRepository.save(author);
        return "Book added Successfully";
    }
}
