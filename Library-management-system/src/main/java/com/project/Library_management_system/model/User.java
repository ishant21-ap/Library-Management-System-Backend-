package com.project.Library_management_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    @Column(nullable = false, unique = true, length = 15)   //Making phoneNo unique or we can make email
    private String phoneNo;

    @Column(unique = true, nullable = false , length = 50)
    private String email;

    private String address;


    //Now using below we can get data when this tables rows are created, this annotation is came from
    //hibernate, so we can use it only when we are working with hibernate
    @CreationTimestamp
    private Date createdOn;

    //Now there is one question, like if the application  server is running in india, and database server
    //is running on USA or other country, now question is what type of time will you store in database ?
    //So by default the time will be store of india means application server, if we want to store the
    //time of the database server like usa then we can specify it like @UpdateTimestamp(source = SourceType.DB)
    //VM stands for virtual machine and DB stands for DAtabase

    //Same as above but for updated time
    @UpdateTimestamp
    private Date updatedOn;


    //Now wehenever we are working with enums we use this below annoation, now enum type can of two types
    //one is ordinal and one is String, see when you only type @Enumerated only then this is default ordinal
    //type now ordinal means index based, like currently for user type 0-index stands for ADMIN and 1 stands
    //for STUDENT, so in database it will store in the form of 0 and 1, if user specify EnumType as string
    //then in database it will store in the form of String like ADMIM, STUDENT.
    @Enumerated(EnumType.STRING)
    private UserType userType;


    //Ordinal example
    @Enumerated
    private UserStatus userStatus;


    //Now we dont want to store book list physically in our table but we want relationship should be created
    //thats why we have used mappedBy
    @OneToMany(mappedBy = "user")  //One user can have many books
    private List<Book> bookList;


    @OneToMany(mappedBy = "user")
    private List<Txn> txnList;

    

}
