package com.gakyvan.domain;

import lombok.Data;

@Data
public class Address {
    private String postalCode;
    private String city;
    private String state;
    private String country;
}
