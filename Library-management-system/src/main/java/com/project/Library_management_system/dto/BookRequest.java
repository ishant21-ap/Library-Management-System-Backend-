package com.project.Library_management_system.dto;

import com.project.Library_management_system.model.Author;
import com.project.Library_management_system.model.Book;
import com.project.Library_management_system.model.BookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookRequest {

    //What should we get from frontend is defined here, we are not exposing our model class directly to frontend

    @NotBlank(message = "book Title should not be blank.")
    private String bookTitle;

    @NotBlank(message = "bookNo should not be blank.")
    private String bookNo;

    @NotBlank(message = "author name should not be blank.")
    private String authorName;

    @NotBlank(message = "author email should not be blank.")
    private String authorEmail;

    @NotNull(message = "book Type should not be blank.")
    private BookType bookType;

    @Positive(message = "security amount should not be positive.")
    private int securityAmount;

    public Author toAuthor(){
        return Author.builder().email(this.authorEmail).name(this.authorName).build();
    }

    public Book toBook() {
        return Book.builder().bookNo(this.bookNo).title(this.bookTitle)
                .securityAmount(this.securityAmount).bookType(this.bookType).build();
    }
}
