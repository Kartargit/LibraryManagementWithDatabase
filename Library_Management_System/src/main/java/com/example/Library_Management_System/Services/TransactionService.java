package com.example.Library_Management_System.Services;

import com.example.Library_Management_System.CustomException.BookNotAvailableException;
import com.example.Library_Management_System.CustomException.BookNotFoundException;
import com.example.Library_Management_System.Enums.CardStatus;
import com.example.Library_Management_System.Enums.TransactionStatus;
import com.example.Library_Management_System.Enums.TransactionType;
import com.example.Library_Management_System.Models.LibraryCard;
import com.example.Library_Management_System.Models.Transactions;
import com.example.Library_Management_System.Repositories.BookRepository;
import com.example.Library_Management_System.Repositories.LibraryCardRepository;
import com.example.Library_Management_System.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.Library_Management_System.Models.Book;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Value("${book.maxLimit}")
    private Integer maxBookLimit;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryCardRepository libraryCardRepository;
    public String issueBook(Integer bookId,Integer cardId) throws Exception{

        Transactions transaction = new Transactions(TransactionStatus.PENDING, TransactionType.ISSUED,0);


        Optional<Book> optionalBook = bookRepository.findById(bookId);
//        book level exception handling
        if(!optionalBook.isPresent()){
            throw new BookNotFoundException("BookId is incorrect");
        }
        Book book = optionalBook.get();
        if(book.getIsAvailable()==Boolean.FALSE){
            throw new BookNotAvailableException("Book is not available");
        }
//        card level exception handling
        Optional<LibraryCard> cardOptional = libraryCardRepository.findById(cardId);
        if(!cardOptional.isPresent()){
            throw new Exception("Card id is invalid");
        }

        LibraryCard card = cardOptional.get();
        if(!card.getCardStatus().equals(CardStatus.ACTIVE)){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Card is not in right status");
        }
        if(card.getNoOfBooksIssued()>=maxBookLimit){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Already maximum book issued to the card");
        }
//        validations over now can successfully can issue the book
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

//        card and book need to be updated also
        book.setIsAvailable(Boolean.FALSE);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()+1);

//        unidirectional mapping
        transaction.setBook(book);
        transaction.setLibraryCard(card);

//here cascading will work child to parent as cascading is type all so both ways works
        Transactions newTransactionWithId = transactionRepository.save(transaction);

//        bidirectional mapping
        book.getTransactionsList().add(newTransactionWithId);
        card.getTransactionsList().add(newTransactionWithId);

//        final save in parent is optional if child is saved


        return "Transaction is successful and Book has been issued";
    }
    public String returnBook(Integer bookId,Integer cardId) throws Exception{
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(!optionalBook.isPresent()){
            throw new Exception("BookId is incorrect");
        }
        Optional<LibraryCard> optionalLibraryCard = libraryCardRepository.findById(cardId);
        if(!optionalLibraryCard.isPresent()){
            throw new Exception("Card Id is not correct");
        }
        Book book = optionalBook.get();
        LibraryCard card = optionalLibraryCard.get();
        List<Transactions> transactionsList =transactionRepository.findTransactionsByBookAndLibraryCardTransactionStatusAndTransactionType
                                             (book,card,TransactionStatus.SUCCESS,TransactionType.ISSUED);
        Transactions latestTransaction = transactionsList.get(transactionsList.size()-1);
        Date issuedDate = latestTransaction.getCreatedAt();
//        calculate issued days to milliseconds
        long milliSecondTime = Math.abs(System.currentTimeMillis()-issuedDate.getTime());
        long noOfDaysFrom_issuedDate = TimeUnit.DAYS.convert(milliSecondTime,TimeUnit.MILLISECONDS);
        int fineAmount = 0;
        if(noOfDaysFrom_issuedDate>15){
            fineAmount = Math.toIntExact((noOfDaysFrom_issuedDate - 15) * 5);
        }
        Transactions transaction = new Transactions(TransactionStatus.SUCCESS,TransactionType.RETURNED,fineAmount);
        transaction.setBook(book);
        transaction.setLibraryCard(card);
        book.setIsAvailable(Boolean.TRUE);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()-1);
//        saving the child first to avoid duplicate transaction by creating id for the transaction
        Transactions newTransaction = transactionRepository.save(transaction);
        book.getTransactionsList().add(newTransaction);
        card.getTransactionsList().add(newTransaction);
//        optional but safety or formality we save the parents too
        bookRepository.save(book);
        libraryCardRepository.save(card);

        return "Book has been returned Successfully";

    }
}
