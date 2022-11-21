package com.ReactiveMongoCrud.utils;

import com.ReactiveMongoCrud.dto.ProductDto;
import com.ReactiveMongoCrud.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto entityToDto(Product product){
        ProductDto productDto = new ProductDto();

        // source, destination (if both classes have same attributes)
        BeanUtils.copyProperties(product, productDto);

        return productDto;
    }


    public static Product dtoToEntity(ProductDto productDto){
        Product product = new Product();

        BeanUtils.copyProperties(productDto, product);

        return product;
    }


}
