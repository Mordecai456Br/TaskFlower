package com.begodex.taskflower.DTO;

import com.begodex.taskflower.models.project.Category;
import com.begodex.taskflower.models.project.ProjectStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class ProjectRequestDTO {

    @NotBlank
    private String title;
    private String description;
    private Category category;        // usado para projetos pessoais
    private String corporateCategory; // usado se corporate project
    private ProjectStatus status;
    @Min(0) @Max(100)
    private Integer prdScopePercent;


}
