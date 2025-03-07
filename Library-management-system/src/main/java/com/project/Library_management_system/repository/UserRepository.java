package com.project.Library_management_system.repository;


import com.project.Library_management_system.model.User;
import com.project.Library_management_system.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where :q", nativeQuery = true)
    List<User> findUsersByNativeQuery(String q);

    User findByPhoneNoAndUserType(String phoneNo, UserType type);
}
