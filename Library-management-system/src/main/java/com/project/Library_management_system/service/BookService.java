package com.project.Library_management_system.service;

import com.project.Library_management_system.dto.BookRequest;
import com.project.Library_management_system.model.*;
import com.project.Library_management_system.repository.AuthorRepository;
import com.project.Library_management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(BookRequest bookRequest) {
        Author authorFromDB = authorRepository.getAuthorByEmail(bookRequest.getAuthorEmail());

        if(authorFromDB == null) {
            authorFromDB = authorRepository.save(bookRequest.toAuthor());
        }
        Book book = bookRequest.toBook();
        book.setAuthor(authorFromDB);
        return bookRepository.save(book);
    }

    public List<Book> filter(BookFilterType filterType, Operator operator, String value) {
        switch (filterType){
            case BOOK_TITLE :
                switch (operator){
                    case EQUALS :
                        return bookRepository.findByTitle(value);
                    case LIKE:
                        return bookRepository.findByTitleContaining(value);
                    default:
                        return new ArrayList<>();
                }
            case BOOK_TYPE:
                switch (operator){
                    case EQUALS :
                        return bookRepository.findByBookType(BookType.valueOf(value));
                }
            case BOOK_NO:
                switch (operator){
                    case EQUALS :
                        return bookRepository.findByBookNo(value);
                }
        }
        return new ArrayList<>();
    }

    public void updateBookData(Book bookFromDb) {
        bookRepository.save(bookFromDb);
    }
}
