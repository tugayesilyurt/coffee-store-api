package com.bestseller.coffee.controller;

import com.bestseller.coffee.dto.request.drink.CreateDrinkDto;
import com.bestseller.coffee.dto.request.drink.UpdateDrinkDto;
import com.bestseller.coffee.dto.response.drink.CreatedDrinkDto;
import com.bestseller.coffee.dto.response.drink.DeletedDrinkDto;
import com.bestseller.coffee.dto.response.drink.UpdatedDrinkDto;
import com.bestseller.coffee.service.DrinkService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/drinks")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @PostMapping()
    public ResponseEntity<?> createDrink(@RequestBody @Valid CreateDrinkDto createDrinkDto){
        CreatedDrinkDto response = drinkService.createDrink(createDrinkDto);
        return new ResponseEntity<CreatedDrinkDto>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDrink(@PathVariable("id") @NotNull Long id,
                                         @RequestBody @Valid UpdateDrinkDto updateDrinkDto){
        UpdatedDrinkDto response = drinkService.updateDrink(id,updateDrinkDto);
        return new ResponseEntity<UpdatedDrinkDto>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDrink(@PathVariable("id") @NotNull Long id){
        DeletedDrinkDto response = drinkService.deleteDrink(id);
        return new ResponseEntity<DeletedDrinkDto>(response,HttpStatus.OK);
    }

}
