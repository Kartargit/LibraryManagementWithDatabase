package com.example.Library_Management_System.Services;

import com.example.Library_Management_System.Enums.Genre;
import com.example.Library_Management_System.Models.Author;
import com.example.Library_Management_System.Models.Book;
import com.example.Library_Management_System.Repositories.AuthorRepository;
import com.example.Library_Management_System.Repositories.BookRepository;
import com.example.Library_Management_System.RequestDtos.AddBookRequestDto;
import com.example.Library_Management_System.ResponseDtos.BookResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
//       entities only will go into database and will come out
        Author author = optionalAuthor.get();
        book.setAuthor(author);//set in child
//        bookRepository.save(book);
        List<Book> list = author.getBookList();
        list.add(book);
        author.setBookList(list);//set in parent
        authorRepository.save(author);
        return "Book added Successfully and update";
    }
    public List<BookResponseDto> getBookListByGenre(Genre genre){
        List<Book> bookList = bookRepository.findBooksByGenre(genre);
        List<BookResponseDto> responseDtoList = new ArrayList<>();
        for (Book book: bookList){
            BookResponseDto bookResponseDto = new BookResponseDto(book.getTitle(),book.getPrice(),book.getIsAvailable(),
                                                  book.getGenre(),book.getPublicationDate(),book.getAuthor().getName());
            responseDtoList.add(bookResponseDto);
        }
        return responseDtoList;
    }
}
