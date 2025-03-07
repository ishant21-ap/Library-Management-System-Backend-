package com.project.Library_management_system.controller;

import com.project.Library_management_system.dto.BookRequest;
import com.project.Library_management_system.model.Book;
import com.project.Library_management_system.model.BookFilterType;
import com.project.Library_management_system.model.Operator;
import com.project.Library_management_system.model.UserFilterType;
import com.project.Library_management_system.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public Book addBook(@RequestBody @Valid BookRequest bookRequest) {
        return bookService.addBook(bookRequest);
    }


    @GetMapping("/filter")
    public List<Book> filter(@RequestParam("filterBy") BookFilterType filterType,
                             @RequestParam("operator") Operator operator,
                             @RequestParam("value") String value) {

        return bookService.filter(filterType, operator, value);
    }
}
