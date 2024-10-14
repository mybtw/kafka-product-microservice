package ru.astaf.productmicroservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.astaf.productmicroservice.event.ProductCreatedEvent;
import ru.astaf.productmicroservice.service.ProductService;
import ru.astaf.productmicroservice.service.dto.CreateProductDto;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto createProductDto) {
        //TODO save to DB
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, createProductDto.getTitle(),
                createProductDto.getPrice(), createProductDto.getQuantity());

        kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);
        LOGGER.info("Return {}", productId);
        return productId;
    }
}
