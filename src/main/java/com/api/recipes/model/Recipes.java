package com.api.recipes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Recipes {
    @Id
    @GeneratedValue
    private Integer recipes_id;
    @Size(max = 30, message = "El nombre no puede tener más de 30 caracteres")
    private String name;
    @Size(max = 100, message = "La descripción no puede tener más de 100 caracteres")
    private String description;
    private Integer preparation_time;
    @Min(value = 1, message = "La dificultad mínima es 1")
    @Max(value = 3, message = "La dificultad máxima es 3")
    private Integer difficulty;
    @ManyToOne
    @JoinColumn(name = "foodTypes_id")
    private FoodTypes foodTypes;
}
