package com.api.recipes.service;

import com.api.recipes.model.ApiResponse;
import com.api.recipes.model.FoodTypes;
import com.api.recipes.model.Recipes;
import com.api.recipes.repository.FoodTypesRepository;
import com.api.recipes.repository.RecipesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipesService {
    private final RecipesRepository recipesRepository;
    private final FoodTypesRepository foodTypesRepository;

    public ResponseEntity<ApiResponse<Recipes>> createRecipes(Recipes recipes) {

            try {
                //Obtener el Id de la foodtype desde la comida
                Integer foodTypeId = recipes.getFoodTypes().getFoodTypes_id();

                //verificar si la foodtype existe en la base de datos
                Optional<FoodTypes> existingFoodTypesOptional = foodTypesRepository.findById(foodTypeId);
                if (existingFoodTypesOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse<>(null, "error", "tipo de comida no encontrada con el ID:" + foodTypeId));
                }
                //obtener la instancia existente de foodtype en la bd
                FoodTypes existingFoodTypes = existingFoodTypesOptional.get();
                //Asignar la foodtype existente a la comida
                recipes.setFoodTypes(existingFoodTypes);
                //guardar la receta en la bd
                Recipes newRecipes = recipesRepository.save(recipes);
                //construir el objeto apiresponse con la receta creada
                ApiResponse<Recipes> response = new ApiResponse<>(newRecipes, "success", "receta creada con éxito");
                //devolver el responseentity con el apiresponse
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (Exception e) {
                //manejar cualquier excepción que pueda ocurrir
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(null, "error", "error al crear la receta " + e.getMessage()));
            }

    }

    public ResponseEntity<ApiResponse<Recipes>> getRecipesId(@PathVariable Integer id) {
        try {
            Optional<Recipes> optionalRecipes = recipesRepository.findById(id);
            if (optionalRecipes.isPresent()) {
                Recipes recipes = optionalRecipes.get();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(recipes, "success", "Receta encontrada"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(null, "error", "receta no encontrada con el id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "error al obtener la receta" + e.getMessage()));
        }
    }

    public Page<Recipes> getAllRecipes(Pageable pageable) {
        return recipesRepository.findAll(pageable);
    }

    public List<Recipes> filterRecipes(Integer food_types_id, Integer difficulty, Integer preparation_time) {
        return recipesRepository.findRecipesByFilters(food_types_id, difficulty, preparation_time);
    }
}
