package com.project.Library_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TxnRequest {

    @NotBlank(message = "user phone number should not be blank.")
    private String userPhoneNo;  //Unique identifier for user

    @NotBlank(message = "book number should not be blank.")
    private String bookNo;    //Unique identifier for book

}
