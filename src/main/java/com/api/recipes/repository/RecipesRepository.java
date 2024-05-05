package com.api.recipes.repository;

import com.api.recipes.model.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
    @Query(value = "SELECT * FROM recipesdb.recipes r\n" +
            "WHERE \n" +
            "    (r.food_types_id = ?1 OR 1 IS NULL) AND \n" +
            "    (r.difficulty = ?2 OR ?2 IS NULL) AND \n" +
            "    (r.preparation_time = ?3 OR ?3 IS NULL)\n" +
            "LIMIT 0, 1000;", nativeQuery = true)
    List<Recipes> findRecipesByFilters(
            Integer food_types_id, Integer difficulty, Integer preparation_time);

}
