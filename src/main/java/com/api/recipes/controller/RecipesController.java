package com.api.recipes.controller;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.Recipes;
import com.api.recipes.repository.RecipesRepository;
import com.api.recipes.service.RecipesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecipesController {
    private final RecipesService recipesService;
    private final RecipesRepository recipesRepository;
    private static final Logger logger = Logger.getLogger(RecipesController.class.getName());

    @PostMapping
    public ResponseEntity<ApiResponse<Recipes>> createRecipes(@RequestBody Recipes recipes) {
        return recipesService.createRecipes(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Recipes>> getRecipesById(@PathVariable Integer id) {
        return recipesService.getRecipesId(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Recipes>>> getAllRecipes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        logger.info("Llamada al endpoint GET /api recibida.");
        Page<Recipes> recipesPage = recipesService.getAllRecipes(PageRequest.of(page, size));

        if (recipesPage != null && recipesPage.hasContent()) {
            ApiResponse<Page<Recipes>> response = new ApiResponse<>(recipesPage, "success", "Recetas obtenidas satisfactoriamente");
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Page<Recipes>> response = new ApiResponse<>(null, "error", "No se encontraron recetas");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<List<Recipes>>> getProductsByFilters(
            @RequestParam(required = false) Integer food_types_id,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) Integer preparation_time) {

        List<Recipes> recipes = recipesRepository.findRecipesByFilters(food_types_id, difficulty, preparation_time);

        if (recipes.isEmpty()) {
            // Si la lista de recetas está vacía, devuelve un ResponseEntity con un mensaje apropiado
            ApiResponse<List<Recipes>> response = new ApiResponse<>(null, "error", "No se encontraron recetas con los filtros proporcionados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            // Si se encontraron recetas, devuelve un ResponseEntity con las recetas y un mensaje de éxito
            ApiResponse<List<Recipes>> response = new ApiResponse<>(recipes, "success", "Recetas obtenidos satisfactoriamente");
            return ResponseEntity.ok().body(response);
        }
    }



}
