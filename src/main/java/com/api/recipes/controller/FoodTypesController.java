package com.api.recipes.controller;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.FoodTypes;
import com.api.recipes.service.FoodTypesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food-types")
@RequiredArgsConstructor
public class FoodTypesController {
    private final FoodTypesService foodTypesService;

    @PostMapping
    public ResponseEntity<ApiResponse<FoodTypes>> createFoodTypes(@RequestBody FoodTypes foodTypes) {
        return foodTypesService.createFoodTypes(foodTypes);
    }

}
