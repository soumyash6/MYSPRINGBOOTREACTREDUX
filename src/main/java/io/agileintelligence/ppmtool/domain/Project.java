package io.agileintelligence.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name cannt be blank")
    @Length(max = 10)
    private String projectName;
    @NotBlank(message = "ProjectIdentifier name cannt be blank")
    @Size(min = 4, max = 5, message = " please use 4 to 5 character")
    @Column(unique = true, updatable = false)
    @Length(max = 10)
    private String projectIdentifier;
    @NotBlank(message = "Project description is required")
    @Length(max = 20)
    private String description;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;


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
