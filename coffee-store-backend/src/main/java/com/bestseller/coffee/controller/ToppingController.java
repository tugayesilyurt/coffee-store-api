package com.bestseller.coffee.controller;


import com.bestseller.coffee.dto.request.topping.CreateToppingDto;
import com.bestseller.coffee.dto.request.topping.UpdateToppingDto;
import com.bestseller.coffee.dto.response.topping.CreatedToppingDto;
import com.bestseller.coffee.dto.response.topping.DeletedToppingDto;
import com.bestseller.coffee.dto.response.topping.MostUsedToppingsDto;
import com.bestseller.coffee.dto.response.topping.UpdatedToppingDto;
import com.bestseller.coffee.service.ToppingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/toppings")
@RequiredArgsConstructor
public class ToppingController {

    private final ToppingService toppingService;

    @PostMapping()
    public ResponseEntity<?> createTopping(@RequestBody @Valid CreateToppingDto createToppingDto){
        CreatedToppingDto response = toppingService.createTopping(createToppingDto);
        return new ResponseEntity<CreatedToppingDto>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopping(@PathVariable("id") @NotNull Long id,
                                           @RequestBody @Valid UpdateToppingDto updateToppingDto){
        UpdatedToppingDto response = toppingService.updateTopping(id,updateToppingDto);
        return new ResponseEntity<UpdatedToppingDto>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopping(@PathVariable("id") @NotNull Long id){
        DeletedToppingDto response = toppingService.deleteTopping(id);
        return new ResponseEntity<DeletedToppingDto>(response,HttpStatus.OK);
    }

    @GetMapping("/most-used")
    public ResponseEntity<?> getMostUsedToppings(){
        MostUsedToppingsDto response = toppingService.findMostUsedToppings();
        return new ResponseEntity<MostUsedToppingsDto>(response,HttpStatus.OK);
    }
}
