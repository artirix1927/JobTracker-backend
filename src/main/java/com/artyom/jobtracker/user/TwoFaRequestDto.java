package com.artyom.jobtracker.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoFaRequestDto {
    String email;
    int code;

    public String getUser() {
        return this.email;
    }

}
