package com.project.Library_management_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class Txn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn
    private User user;

    private String txnId;

    @ManyToOne
    @JoinColumn
    private Book book;

    @Enumerated
    private TxnStatus txnStatus;

    private int settlementAmount;

    @CreationTimestamp
    private Date createdOn;


    @UpdateTimestamp
    private Date updatedOn;
}
