package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_PRODUCTS")
public class ProductModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID idProduct;
    private String name;
    private BigDecimal value;

    public String getName(){
        return this.name;
    }
    public BigDecimal getValue(){
        return this.value;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setValue(BigDecimal value){
        this.value = value;
    }
}
