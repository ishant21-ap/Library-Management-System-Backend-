package com.project.Library_management_system.repository;

import com.project.Library_management_system.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    //native Query
    @Query(value = "select * from author where email = :email", nativeQuery = true)  //make sure to add native query = true
    Author getAuthorByEmail(String email);
}
