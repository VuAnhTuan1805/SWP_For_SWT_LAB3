package com.swpteam.smokingcessation.domain.dto.account;

import com.swpteam.smokingcessation.domain.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "INVALID_EMAIL_FORMAT")
    String email;

    @NotBlank(message = "MESSAGE_REQUIRED")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    @Pattern(regexp = "\\d{10}", message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    @NotNull(message = "ROLE_REQUIRED")
    Role role;
}