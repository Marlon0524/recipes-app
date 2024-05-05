package com.api.recipes.service;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.FoodTypes;
import com.api.recipes.repository.FoodTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodTypesService {
    private final FoodTypesRepository foodTypesRepository;
    public ResponseEntity<ApiResponse<FoodTypes>> createFoodTypes(FoodTypes foodTypes) {
        try {
            // Guardar el tipo de comida en la base de datos
            FoodTypes newFoodTypes = foodTypesRepository.save(foodTypes);
            // crear objecto apiresponse con el tipo de comida creada
            ApiResponse<FoodTypes> response = new ApiResponse<>(newFoodTypes, "success", "Tipo de comida creada correctamente");
            //devolver responseentity con el apiresponse
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            //manejar cualquier excepción que pueda ocurrir
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "error al crear el tipo de comida " + e.getMessage()));
        }
    }
}
