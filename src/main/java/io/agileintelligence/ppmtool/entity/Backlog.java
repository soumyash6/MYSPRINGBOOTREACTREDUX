package io.agileintelligence.ppmtool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Schema(description = "Backlog entity")
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the backlog", example = "1")
    private Long id;

    @Schema(description = "Sequence number for project tasks", example = "0")
    private Integer PTSequence = 0;

    @Schema(description = "Identifier for the project", example = "PROJ123")
    private String projectIdentifier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    @Schema(description = "Associated project for the backlog")
    private Project project;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
    @Schema(description = "List of project tasks associated with the backlog")
    private List<ProjectTask> projectTasks = new ArrayList<>();
}