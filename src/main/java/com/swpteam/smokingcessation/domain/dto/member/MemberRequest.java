package com.swpteam.smokingcessation.domain.dto.member;

import com.swpteam.smokingcessation.domain.enums.MemberGender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberRequest {

    String fullName;
    
    @Past(message = "DOB_INVALID")
    LocalDate dob;

    String address;

    @NotNull(message = "GENDER_REQUIRED")
    MemberGender gender;
}
