package com.bestseller.coffee.controller;

import com.bestseller.coffee.dto.request.order.CreateOrderDto;
import com.bestseller.coffee.dto.response.order.CreatedOrderDto;
import com.bestseller.coffee.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderDto createOrderDto){
        log.info("create order request handle!");
        CreatedOrderDto response = orderService.createOrder(createOrderDto);
        return new ResponseEntity<CreatedOrderDto>(response, HttpStatus.CREATED);
    }
}