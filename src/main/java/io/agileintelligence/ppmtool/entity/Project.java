package io.agileintelligence.ppmtool.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "project")
@Schema(description = "Project entity")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the project", example = "1")
    private Long id;

    @NotBlank(message = "Project name cannot be blank")
    @Length(max = 10, message = "Project name must not exceed 10 characters")
    @Schema(description = "Name of the project", example = "Project A")
    private String projectName;

    @NotBlank(message = "Project Identifier cannot be blank")
    @Size(min = 4, max = 5, message = "Please use 4 to 5 characters")
    @Column(unique = true, updatable = false)
    @Schema(description = "Unique project identifier", example = "PROJ1")
    private String projectIdentifier;

    @NotBlank(message = "Project description is required")
    @Length(max = 20, message = "Description must not exceed 20 characters")
    @Schema(description = "Description of the project", example = "This is a sample project")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Start date of the project", example = "2023-01-01")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "End date of the project", example = "2023-12-31")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    @Schema(description = "Creation date of the project", example = "2023-01-01")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Last update date of the project", example = "2023-06-01")
    private Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    @Schema(description = "Backlog associated with the project")
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Schema(description = "User associated with the project")
    private User user;

    @Schema(description = "Leader of the project", example = "John Doe")
    private String projectLeader;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}