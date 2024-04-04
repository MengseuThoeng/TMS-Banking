package com.seu.tms_mobile_banking.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BaseError<T> {
    private String code;
    private T description;
}
