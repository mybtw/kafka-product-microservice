package ru.astaf.productmicroservice.service;

import ru.astaf.productmicroservice.service.dto.CreateProductDto;

public interface ProductService {
    String createProduct(CreateProductDto createProductDto);
}
