package ca.gbc.productservice.controller;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
        private final ProductService productService;

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public void createProduct(@RequestBody ProductRequest productRequest){
            productService.createProduct(productRequest);
        }
        @ResponseStatus(HttpStatus.OK)
        @GetMapping
        public List<ProductResponse> getAllProducts(){
            return productService.getAllProducts();
        }


        @PutMapping
        //@ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<?> updateProduct(@PathVariable("productId") String productId,
                                               @RequestBody ProductRequest productRequest){
                String updateProductId = productService.updateProduct(productId,productRequest);
                // set the header attribute
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location","/api/product/" + updateProductId);
                return new ResponseEntity<>(headers,HttpStatus.NO_CONTENT);
        }

        public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productId){
                productService.deleteProduct(productId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
