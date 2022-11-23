package com.ReactiveMongoCrud.service;


import com.ReactiveMongoCrud.dto.ProductDto;
import com.ReactiveMongoCrud.entity.Product;
import com.ReactiveMongoCrud.repository.ProductMongoTemplateRepository;
import com.ReactiveMongoCrud.repository.ProductRepository;
import com.ReactiveMongoCrud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMongoTemplateRepository templateRepository;

    // GET ALL PRODUCTS
    public Flux<ProductDto> getProducts(){

        return repository.findAll().map(AppUtils::entityToDto);

    }

    // GET PRODUCT BY ID
    public Mono<ProductDto> getProduct(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    // GET PRODUCT BY NAME
    public Flux<ProductDto> getProductByName(String name)
    {
        return templateRepository.findAllByName(name);
    }


    // GET PRODUCT BY CATEGORY
    public Flux<ProductDto> getProductByCategory(String category)
    {
        return templateRepository.findAllByCategory(category);
    }







    // GET PRODUCTS IN PRICE RANGE
    public Flux<ProductDto> getProductsInPriceRange(double min, double max){

        return repository.findByPriceBetween(Range.closed(min,max));

    }

    // GET PRODUCTS IN QTY RANGE
    public Flux<ProductDto> getProductsInQtyRange(int min, int max){

        return repository.findByQtyBetween(Range.closed(min,max));

    }

    // SAVE PRODUCT
    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){


        // Direct save without flatmap (accessing element from Mono)
//        ProductDto productDto = productDtoMono.block();
//        return repository.save(AppUtils.dtoToEntity(productDto)).map(AppUtils::entityToDto);


        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto);

    }

    // SAVE ALL PRODUCTS
    public Flux<ProductDto> saveAll(Flux<ProductDto> data) {

        // direct by accessing elements from Flux
//        List<Product> productList = null;
//        data.toStream().forEach(productDto -> productList.add(AppUtils.dtoToEntity(productDto)));
//
//        return repository.saveAll(productList).map(AppUtils::entityToDto);

        return templateRepository.saveAll(data);
    }


    // UPDATE PRODUCT
    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id){

        // direct by accessing elements from Flux
//        if(repository.existsById(id).block()){
//
//            ProductDto productDto = productDtoMono.block();
//            productDto.setId(id);
//
//            return repository.save(AppUtils.dtoToEntity(productDto)).map(AppUtils::entityToDto);
//        }
//        else{
//            return null;
//        }


        return repository.findById(id)
                .flatMap(product -> productDtoMono.map(AppUtils::dtoToEntity)
                                                    .doOnNext(product1 -> product1.setId(id))
                        )
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
    }

    // DELETE Product
    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }




}
