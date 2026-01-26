package com.sweet.n_plus_one_query.controller;

import com.sweet.n_plus_one_query.dto.ResponseDto;
import com.sweet.n_plus_one_query.dto.request.OrderRequest;
import com.sweet.n_plus_one_query.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Order created successfully");
            responseDto.setData(orderService.createOrder(orderRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Order found");
            responseDto.setData(orderService.getOrderById(orderId));
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PostMapping("/testTransactional")
    public ResponseEntity<?> testTransactional(@RequestBody OrderRequest orderRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Order created successfully");
            responseDto.setData(orderService.testTransactional(orderRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PostMapping("/testSupportPropagation")
    public ResponseEntity<?> testSupportPropagation(@RequestBody OrderRequest orderRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Order created successfully");
            responseDto.setData(orderService.testSupportPropagation(orderRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    @PostMapping("/testNotSupportedPropagation")
    public ResponseEntity<?> testNotSupportedPropagation(@RequestBody OrderRequest orderRequest) {
        ResponseDto responseDto = new ResponseDto();

        try{
            responseDto.setMessage("Order created successfully");
            responseDto.setData(orderService.testNotSupportedPropagation(orderRequest));
            return ResponseEntity.ok(responseDto);
        }
        catch(Exception e){
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
