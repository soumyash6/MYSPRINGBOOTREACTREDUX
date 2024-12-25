package io.agileintelligence.ppmtool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Schema(description = "Project Task entity")
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the project task", example = "1")
    private Long id;

    @Column(updatable = false, unique = true)
    @Schema(description = "Sequence number for the project task", example = "PROJTASK1")
    private String projectSequence;

    @NotBlank(message = "Please include a project summary")
    @Schema(description = "Summary of the project task", example = "Implement login functionality")
    private String summary;

    @Schema(description = "Acceptance criteria for the project task", example = "User can log in with valid credentials")
    private String acceptanceCriteria;

    @Schema(description = "Status of the project task", example = "IN_PROGRESS")
    private String status;

    @Schema(description = "Priority of the project task", example = "1")
    private Integer priority;

    @Temporal(TemporalType.DATE)
    @Schema(description = "Due date of the project task", example = "2023-12-31")
    private Date dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    @Schema(description = "Backlog associated with the project task")
    private Backlog backlog;

    @Column(updatable = false)
    @Schema(description = "Identifier for the project", example = "PROJ1")
    private String projectIdentifier;

    @Temporal(TemporalType.DATE)
    @Schema(description = "Creation date of the project task", example = "2023-01-01")
    private Date create_At;

    @Temporal(TemporalType.DATE)
    @Schema(description = "Last update date of the project task", example = "2023-06-01")
    private Date update_At;

    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }

    @Override
    public String toString() {
        return "ProjectTask{"
                + "id=" + id
                + ", projectSequence='" + projectSequence + '\''
                + ", summary='" + summary + '\''
                + ", acceptanceCriteria='" + acceptanceCriteria + '\''
                + ", status='" + status + '\''
                + ", priority=" + priority
                + ", dueDate=" + dueDate
                + ", projectIdentifier='" + projectIdentifier + '\''
                + ", create_At=" + create_At
                + ", update_At=" + update_At
                + '}';
    }
}