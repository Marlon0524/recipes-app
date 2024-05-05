package com.api.recipes.controller;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.Recipes;
import com.api.recipes.repository.RecipesRepository;
import com.api.recipes.service.RecipesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipesControllerTest {
    @Mock
    private RecipesService recipesService;
    @Mock
    private RecipesRepository recipesRepository;
    @InjectMocks
    private RecipesController recipesController;
    @Test
    void createRecipes() {
        Recipes recipes = new Recipes();
        ApiResponse<Recipes> expectedResponse = new ApiResponse<>(recipes, "success", "Receta creada satisfactoriamente");
        when(recipesService.createRecipes(recipes)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ApiResponse<Recipes>> actualResponse = recipesController.createRecipes(recipes);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void getRecipesById(){
        Integer id = 1;
        Recipes recipes = new Recipes();
        ApiResponse<Recipes> expectedResponse = new ApiResponse<>(recipes, "success", "Receta obtenida satisfactoriamente");
        when(recipesService.getRecipesId(id)).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ResponseEntity<ApiResponse<Recipes>> actualResponse = recipesController.getRecipesById(id);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
    }

    @Test
    void  getAllRecipes(){
        int page = 0;
        int size = 10;
        List<Recipes> recipesList = Arrays.asList(new Recipes(), new Recipes());
        Page<Recipes> recipesPage = new PageImpl<>(recipesList);
        ApiResponse<Page<Recipes>> expectedResponse = new ApiResponse<>(recipesPage, "success", "Recetas obtenidas satisfactoriamente");
        when(recipesService.getAllRecipes(PageRequest.of(page, size))).thenReturn(recipesPage);

        ResponseEntity<ApiResponse<Page<Recipes>>> actualResponse = recipesController.getAllRecipes(page, size);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());

    }
}