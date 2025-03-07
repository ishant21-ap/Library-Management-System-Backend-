package com.project.Library_management_system.service;

import com.project.Library_management_system.dto.TxnRequest;
import com.project.Library_management_system.exception.TxnException;
import com.project.Library_management_system.model.*;
import com.project.Library_management_system.repository.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Value("${book.valid.upto}")   //due days are fixed and we define it in application.properties file
    private int dueDays;

    @Value("${book.fine.amount.per.day}")
    private int fineAmountPerDay;


    public User getUserByDb(TxnRequest txnRequest) throws TxnException {
        User userFromDb = userService.getStudentByPhoneNo(txnRequest.getUserPhoneNo());

        //check if user is coming null or not
        if(userFromDb == null) {
            throw new TxnException("Student does not belong to my library");
        }
        return userFromDb;
    }


    public Book getBookByDb(TxnRequest txnRequest) throws TxnException {
        List<Book> books = bookService.filter(BookFilterType.BOOK_NO, Operator.EQUALS, txnRequest.getBookNo());
        if(books.isEmpty()) {
            throw new TxnException("Book does not belong to my library");
        }
        Book bookFromDb = books.get(0);  //I will be getting only one book
        return bookFromDb;
    }

    @Transactional(rollbackFor = TxnException.class)
    public String createTxn(User userFromDb, Book bookFromDb){
        String txnId = UUID.randomUUID().toString();
        Txn txn = Txn.builder().txnId(txnId).user(userFromDb).book(bookFromDb).txnStatus(TxnStatus.ISSUED).build();  //creating an object of txn
        txnRepository.save(txn);
        bookFromDb.setUser(userFromDb);
        bookService.updateBookData(bookFromDb);
        return txnId;
    }

    //Consider scenerio if data is saved that is txn is saved but we failed to change data in book table (line 53) so inside book table user will be null but transaction has been created
    //Transactional does either all of the statement should be executed or none of them

//    @Transactional(rollbackFor = {TxnException.class})   //we required this bcoz we are dealing with multiple queries
    public String create(TxnRequest txnRequest) throws TxnException {


        //We divide three differenct steps in three different methods becuase we only want createTxn() method data to be Transactional
        //not others

        //Firslty check student is corrct or not ?  (you can either create a filter or create a method) here we are creating a method
        User userFromDb = getUserByDb(txnRequest);

        //checkwheter book is present or not (now here we are not creating our own method, we will use filter)
        Book bookFromDb = getBookByDb(txnRequest);
        //check if this book is not assigned to some other user
        if(bookFromDb.getUser() != null) {
            throw new TxnException("Book already has been issued to someonelse.");
        }

        //create txn id
        return createTxn(userFromDb, bookFromDb);
    }

    @Transactional(rollbackFor = TxnException.class)
    public int returnBook(TxnRequest txnRequest) throws TxnException {
        User userFromDb = getUserByDb(txnRequest);
        Book bookFromDb = getBookByDb(txnRequest);
        if(bookFromDb.getUser() != userFromDb) {
            throw new TxnException("This was not user to which book was assigned");
        }
        Txn  txn = txnRepository.findByUserPhoneNoAndBookBookNoAndTxnStatus(txnRequest.getUserPhoneNo(), txnRequest.getBookNo(), TxnStatus.ISSUED);
        int fine = calculateFine(txn, bookFromDb.getSecurityAmount());
        if(fine == bookFromDb.getSecurityAmount()) {
            txn.setTxnStatus(TxnStatus.RETURNED);
        }
        else{
            txn.setTxnStatus(TxnStatus.FINED);
        }
        txn.setSettlementAmount(fine);
        bookFromDb.setUser(null);
        bookService.updateBookData(bookFromDb);
        return fine;
    }

    private int calculateFine(Txn txn, int securityAmount) {
        long issuedDate = txn.getCreatedOn().getTime();
        long returnDate = System.currentTimeMillis();

        long timeDifference = returnDate - issuedDate;
        int daysPassed = (int) TimeUnit.DAYS.convert(timeDifference, TimeUnit.MILLISECONDS);
        if(daysPassed > dueDays){
            int fineAmount = (daysPassed - dueDays) * fineAmountPerDay;
            return securityAmount - fineAmount;
        }
        return securityAmount;
    }
}
