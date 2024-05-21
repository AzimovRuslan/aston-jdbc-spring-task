package io.aston.serverside.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class Project {
    private int id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    public Project(String name) {
        this.name = name;
    }
}
