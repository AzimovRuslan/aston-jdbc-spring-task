package io.aston.serverside.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ProjectToEmployee {

    @NotEmpty(message = "Project must not be empty")
    private Project project;

    @NotEmpty
    private Employee employee;
}
