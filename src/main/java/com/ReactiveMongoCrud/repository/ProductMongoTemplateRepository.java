package com.ReactiveMongoCrud.repository;

import com.ReactiveMongoCrud.dto.ProductDto;
import com.ReactiveMongoCrud.entity.Product;
import com.ReactiveMongoCrud.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@RequiredArgsConstructor
public class ProductMongoTemplateRepository {

    private final ReactiveMongoTemplate template;


    public Flux<ProductDto> findAllByName(String name) {

        Flux<Product> productFlux = template
                .find(query(where("name").is(name)), Product.class);
        return
                productFlux.map(AppUtils::entityToDto);

    }
    public Flux<ProductDto> findAllByCategory(String category) {
        var reg = ".*" + category + ".*";
        Flux<Product> productFlux = template
                .find(query(where("category").regex(reg)), Product.class);
        return
                productFlux.map(AppUtils::entityToDto);

    }

    public Flux<ProductDto> saveAll(Flux<ProductDto> data) {

        Flux<ProductDto> productDtoFlux =  data.map(AppUtils::dtoToEntity).flatMap(template::save).map(AppUtils::entityToDto);

        return productDtoFlux;
    }

}
