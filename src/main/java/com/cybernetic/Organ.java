package com.cybernetic;

import lombok.Data;

@Data
public class Organ {
    private String id;  
    private String name;
    private String bloodType;
    private int weight; // Weight in grams
    private String hlaType;

    public Organ(String id, String name, String bloodType, int weight, String hlaType) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
        this.weight = weight;
        this.hlaType = hlaType;
    }
}
