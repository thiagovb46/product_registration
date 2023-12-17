package com.example.springboot.services;

import com.example.springboot.controllers.ProductController;
import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ProductModel createOne(ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        productRepository.save(productModel);
        return productModel;
    }
    public boolean deleteOne(UUID id){
        var product = productRepository.findById(id);
        if(product.isEmpty())
            return false;
        productRepository.deleteById(id);
        return true;
    }
    public ProductModel getOne(UUID id){
        var product = productRepository.findById(id);
        if(product.isEmpty())
            return null;
        product.get().add(linkTo(methodOn(ProductController.class).getAll()).withRel("Products List"));
        return product.get();
    }
    public List<ProductModel> getAll(){
        var products = productRepository.findAll();
        if(products.isEmpty())
            return null;
        for(ProductModel product: products){
            UUID id = product.getIdProduct();
            product.add(linkTo(methodOn(ProductController.class).getOne(id)).withSelfRel());
        }
        return products;
    }
    public ProductModel updateOne(UUID id, ProductRecordDto productDto){
        var product = productRepository.findById(id);
        if(product.isEmpty())
            return null;
        var productModel = product.get();
        BeanUtils.copyProperties(productDto, productModel);
        productRepository.save(productModel);
        return productModel;
    }
}
