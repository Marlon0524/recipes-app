package com.api.recipes.controller;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.FoodTypes;
import com.api.recipes.repository.FoodTypesRepository;
import com.api.recipes.service.FoodTypesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FoodTypesControllerTest {
    @Mock
    private FoodTypesService foodTypesService;
    @Mock
    private FoodTypesRepository foodTypesRepository;
    @InjectMocks
    private FoodTypesController foodTypesController;
    @Test
    void createFoodTypes() {

        FoodTypesService foodTypesService = mock(FoodTypesService.class);
        FoodTypesController foodTypesController = new FoodTypesController(foodTypesService);
        FoodTypes mockFoodTypes = new FoodTypes();

        when(foodTypesService.createFoodTypes(any())).thenReturn(ResponseEntity.ok(new ApiResponse<>(mockFoodTypes,"success","Tipo de comida creada satisfactoriamente")));

        ResponseEntity<ApiResponse<FoodTypes>> responseEntity = foodTypesController.createFoodTypes(mockFoodTypes);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Tipo de comida creada satisfactoriamente", responseEntity.getBody().getMessage());
        assertEquals(mockFoodTypes, responseEntity.getBody().getData());
        // Verifica que el método de servicio fue llamado una vez con el argumento correcto
        verify(foodTypesService, times(1)).createFoodTypes(mockFoodTypes);
    }
}