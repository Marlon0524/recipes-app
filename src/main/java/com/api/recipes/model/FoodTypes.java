package com.api.recipes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class FoodTypes {
    @Id
    @GeneratedValue
    private Integer foodTypes_id;
    @Size(max = 20, message = "El nombre no puede contener más de 20 caracteres")
    private String name;
}
