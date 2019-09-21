/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.domain;

/**
 *
 * @author SOUMYA SAHOO
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dueDate;
    //ManyToOne with Backlog
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

    @Column(updatable = false)
    private String projectIdentifier;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date create_At;
    @Temporal(javax.persistence.TemporalType.DATE)
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
