package com.jgonzal.retail.adapters.input.rest.data.request;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String name;
    private String email;
}
