package com.api.recipes.repository;

import com.api.recipes.model.FoodTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTypesRepository extends JpaRepository<FoodTypes, Integer> {
}
