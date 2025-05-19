package com.onlineShoppingBe.beForOnlineShoppingSystem.dtos;

import lombok.*;

@Data
public class QuantityDTO {
    private Long id;
    private String productCode;
    private int quantity  ;
    private String operation;
    }

