package com.api.recipes.service;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.FoodTypes;
import com.api.recipes.repository.FoodTypesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodTypesServiceTest {
    @Mock
    private FoodTypesRepository foodTypesRepository;
    @InjectMocks
    private FoodTypesService foodTypesService;
    @Test
    void createFoodTypes() {
        // Arrange
        FoodTypesRepository foodTypesRepository = mock(FoodTypesRepository.class);
        FoodTypesService foodTypesService = new FoodTypesService(foodTypesRepository);
        FoodTypes mockFoodTypes = new FoodTypes(); // Mocking the foodtypes object
        FoodTypes savedFoodTypes = new FoodTypes(); // Mocking the saved foddtypes object

        // Mocking the repository method to return the saved foodtype
        when(foodTypesRepository.save(any())).thenReturn(savedFoodTypes);

        // Act
        ResponseEntity<ApiResponse<FoodTypes>> responseEntity = foodTypesService.createFoodTypes(mockFoodTypes);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Tipo de comida creada correctamente", responseEntity.getBody().getMessage());
        assertEquals(savedFoodTypes, responseEntity.getBody().getData());

    }
}