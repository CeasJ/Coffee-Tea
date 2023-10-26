package com.backend.dream.rest;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public ProductDTO getOne(@PathVariable("id") Long id){
        return productService.findById(id);
    }
    @GetMapping()
    public List<ProductDTO> getAll() throws Exception {
        return productService.findAll();
    }
    @PostMapping()
    public Product create(@RequestBody ProductDTO productDTO) {
        return productService.create(productDTO);
    }
    @PutMapping("{id}")
    public ProductDTO update(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id){
        return productService.update(productDTO);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }
}
