package com.seu.tms_mobile_banking.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BasedResponse <T>{
    private T payload;
}
