package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduto(@RequestBody @Valid ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        var products = productRepository.findAll();
        if(products.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductModel> getOne(@PathVariable(value = "id") UUID id){
        var product = productRepository.findById(id);
        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity deleteOne(@PathVariable(value = "id") UUID id){
        var product = productRepository.findById(id);
        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductModel> updateOne(@PathVariable (value = "id") UUID id,
                                                  @Valid @RequestBody ProductRecordDto productDto){
        var product = productRepository.findById(id);
        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        var productModel = product.get();
        BeanUtils.copyProperties(productDto, productModel);
        productRepository.save(productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productModel);
    }
}
