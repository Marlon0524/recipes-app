package com.api.recipes.service;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.FoodTypes;
import com.api.recipes.model.Recipes;
import com.api.recipes.repository.FoodTypesRepository;
import com.api.recipes.repository.RecipesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipesServiceTest {
    @Mock
    private RecipesRepository recipesRepository;
    @Mock
    private FoodTypesRepository foodTypesRepository;
    @InjectMocks
    private RecipesService recipesService;
    @Test
    void createRecipes() {
        // Arrange
        Recipes inputRecipes = new Recipes();
        FoodTypes foodTypes = new FoodTypes();
        foodTypes.setFoodTypes_id(1);
        inputRecipes.setFoodTypes(foodTypes);

        when(foodTypesRepository.findById(1)).thenReturn(Optional.of(foodTypes));
        when(recipesRepository.save(inputRecipes)).thenReturn(inputRecipes);

        // Act
        ResponseEntity<ApiResponse<Recipes>> responseEntity = recipesService.createRecipes(inputRecipes);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("receta creada con éxito", responseEntity.getBody().getMessage());
        assertEquals(inputRecipes, responseEntity.getBody().getData());

    }
    @Test
    void getRecipesId(){
        // Arrange
        Recipes expectedRecipes = new Recipes();
        when(recipesRepository.findById(1)).thenReturn(Optional.of(expectedRecipes));

        // Act
        ResponseEntity<ApiResponse<Recipes>> responseEntity = recipesService.getRecipesId(1);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Receta encontrada", responseEntity.getBody().getMessage());
        assertEquals(expectedRecipes, responseEntity.getBody().getData());
    }

    @Test
    void getAllRecipes(){
        // Arrange
        Pageable pageable = Pageable.unpaged();
        List<Recipes> recipesList = new ArrayList<>();
        Page<Recipes> expectedPage = new PageImpl<>(recipesList);
        when(recipesRepository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Recipes> resultPage = recipesService.getAllRecipes(pageable);

        // Assert
        assertEquals(expectedPage, resultPage);
    }
    @Test
    void filterRecipes(){
        // Arrange
        List<Recipes> expectedRecipesList = new ArrayList<>();
        when(recipesRepository.findRecipesByFilters(anyInt(), anyInt(), anyInt())).thenReturn(expectedRecipesList);

        // Act
        List<Recipes> resultRecipesList = recipesService.filterRecipes(1, 2, 120);

        // Assert
        assertEquals(expectedRecipesList, resultRecipesList);
    }
}