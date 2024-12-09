package com.assignment.core_service.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDTO implements Serializable {

    @NotNull(message = "{message.category.name.require}")
    @Size(min = 2, max = 255, message = "{message.category.name.size}")
    private String categoryName;

    @NotNull(message = "{message.description.require}")
    @Size(min = 5, max = 4000, message = "{message.description.size}")
    private String description;

    private Boolean state;
}
