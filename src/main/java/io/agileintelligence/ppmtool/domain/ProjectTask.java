/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.domain;

/**
 * @author SOUMYA SAHOO
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, unique = true)
    private String projectSequence;
    @NotBlank(message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    //ManyToOne with Backlog
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    @Column(updatable = false)
    private String projectIdentifier;
    @Temporal(TemporalType.DATE)
    private Date create_At;
    @Temporal(TemporalType.DATE)
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
