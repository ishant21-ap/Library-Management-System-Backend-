package com.project.Library_management_system.service;

import com.project.Library_management_system.model.Author;
import com.project.Library_management_system.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorData(String email) {
       return  authorRepository.getAuthorByEmail(email);
    }
}
