package com.ReactiveMongoCrud.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;

    private String name;
    private String category;
    private int qty;
    private double price;

}
