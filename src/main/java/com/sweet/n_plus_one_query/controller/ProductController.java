package com.sweet.n_plus_one_query.controller;

import com.sweet.n_plus_one_query.dto.ResponseDto;
import com.sweet.n_plus_one_query.dto.request.ProductRequest;
import com.sweet.n_plus_one_query.dto.request.ProductUpdateRequest;
import com.sweet.n_plus_one_query.service.IsolationService;
import com.sweet.n_plus_one_query.service.ProductService;
import com.sweet.n_plus_one_query.util.LocalizationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final IsolationService isolationService;
    private final LocalizationUtil localizationUtil;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest, BindingResult bindingResult) {
        ResponseDto responseDto = new ResponseDto();

        try{
            if(bindingResult.hasErrors()){
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(x -> localizationUtil.getLocalMessage(x.getDefaultMessage())).toList(); // get code from message to create local message
                responseDto.setError(errors);
                return  ResponseEntity.badRequest().body(responseDto);
            }

            responseDto.setMessage("Product created successfully");
            responseDto.setData(productService.createProduct(productRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest productRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Product updated successfully");
            responseDto.setData(productService.updateProduct(productId, productRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PostMapping("/readCommittedCreate")
    public ResponseEntity<?> readCommittedCreateProduct(@RequestBody ProductRequest productRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Product created successfully");
            productService.createProductWithThread(productRequest);
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    /*
         Viết query tìm các đơn hàng có sản phẩm theo tên sản phẩm.
         Nhớ trả ra tên cũ, tên hiện tại của sản phẩm.
     */


    /*
    -------- Test Isolation level
    -- Main thread cannot throw exception from sub thread:))))))
     */
    @PatchMapping("/readUncommitted/{id}")
    public ResponseEntity<?> testReadUncommitted(@PathVariable Long id) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Read-Uncommitted tested successfully");
            isolationService.testReadUncommitted(id);
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PatchMapping("/readCommitted/{id}")
    public ResponseEntity<?> testReadCommitted(@PathVariable Long id,
                                               @RequestParam Long quantity) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Read-Committed tested successfully");
            isolationService.testReadCommitted(id, quantity);
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PatchMapping("/repeatableRead/{id}")
    public ResponseEntity<?> testRepeatableRead(@PathVariable Long id,
                                               @RequestParam Long quantity) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Repeatable-Read tested successfully");
            isolationService.testRepeatableRead(id, quantity);
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PatchMapping("/serializable/{id}")
    public ResponseEntity<?> testSerializable(@PathVariable Long id,
                                                @RequestParam Long quantity) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Serializable tested successfully");
            isolationService.testSerializable(id, quantity);
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
