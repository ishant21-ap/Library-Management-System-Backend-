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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String title;

    @Column(length = 20, unique = true)   //See bookNo should be unique
    private String bookNo;

    @Enumerated(EnumType.STRING)
    private BookType bookType;

    private Integer securityAmount;

    //Using @JoinColumn we are making one more row where we store user id, see without using @JoinColumn
    //code will run fine but, relationship will be created but user will not store in database physically
    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Txn> txnList;

    @CreationTimestamp
    private Date createdOn;


    @UpdateTimestamp
    private Date updatedOn;
}
