package com.jgonzal.retail.adapters.input.rest.data.response;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
} 