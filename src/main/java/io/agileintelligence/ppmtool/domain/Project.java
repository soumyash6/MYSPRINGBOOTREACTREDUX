package io.agileintelligence.ppmtool.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String projectName;
  private String projectIdentifier;
  private String description;
  private Date startDate;
  private Date endDate;

  private Date createdAt;
  private Date updatedAt;


  @PrePersist
  protected void onCreate()
  {
    this.createdAt=new Date();


  }
  @PreUpdate
  protected void onUpdate()
  {
    this.updatedAt=new Date();
  }


}
