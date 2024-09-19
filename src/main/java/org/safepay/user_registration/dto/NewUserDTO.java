package org.safepay.user_registration.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewUserDTO {

    @NotNull(message = "Username cannot be null!")
    @Size(min = 1, max = 60, message = "Username must be between 1 and 60 characters.")
    String username;

    @NotNull(message = "Surname cannot be null!")
    @Size(min = 1, max = 60, message = "Surname must be between 1 and 60 characters.")
    String surname;

    @NotNull(message = "Mobile Number cannot be null!")
    @Size(min = 10, max = 12, message = "Mobile Number must be between 10 and 12 characters.")
    String mobNo;

    @NotNull(message = "Email Id cannot be null!")
    @Size(min = 5, max = 60, message = "Email Id must be between 5 and 60 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format.")
    String emailId;

    @NotNull(message = "Password cannot be null!")
    @Size(min = 10, max = 60, message = "Password must be between 10 and 60 characters.")
    String userPassword;

}
