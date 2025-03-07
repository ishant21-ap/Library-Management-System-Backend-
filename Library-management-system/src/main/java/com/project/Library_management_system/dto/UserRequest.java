package com.project.Library_management_system.dto;

import com.project.Library_management_system.model.User;
import com.project.Library_management_system.model.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String userName;

    @NotBlank(message = "user phone number should not be blank.")    //It is our unique identifier, thats why it cannot be blank
    private String phoneNo;

    private String email;

    private String address;

    public User toUser() {
        return User.builder().name(this.userName)
                .email(this.email)
                .phoneNo(this.phoneNo)
                .address(this.address).
                userStatus(UserStatus.ACTIVE).build();
    }
}
