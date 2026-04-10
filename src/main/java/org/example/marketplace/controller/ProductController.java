package org.example.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping()
    public ProductDto create(@RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @GetMapping("/{id}")
    public ProductDto get(@PathVariable Long id) {
        return productService.get(id);
    }
}
