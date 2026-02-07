package com.gusbomcode.workflowlite.entities;

import com.gusbomcode.workflowlite.enums.ProjectStatus;
import com.gusbomcode.workflowlite.exceptions.domain.InvalidProjectStatusTransitionException;
import com.gusbomcode.workflowlite.exceptions.domain.ProjectCancelledException;
import com.gusbomcode.workflowlite.exceptions.domain.ProjectCompletedException;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public void activate(){
        if(this.status != ProjectStatus.DRAFT){
            throw new InvalidProjectStatusTransitionException("Project status can only change from DRAFT to ACTIVE");
        }
        this.status = ProjectStatus.ACTIVE;
    }
    public void completed(){
        if(this.status != ProjectStatus.ACTIVE){
            throw new InvalidProjectStatusTransitionException("Project status can only change from ACTIVE to COMPLETED");
        }
        this.status = ProjectStatus.COMPLETED;
    }
    public void cancelled(){
        if(this.status == ProjectStatus.DRAFT || this.status == ProjectStatus.ACTIVE){
        this.status = ProjectStatus.CANCELLED;
        }else if(this.status == ProjectStatus.COMPLETED){
            throw new ProjectCompletedException("Project is COMPLETED already");
        }else{
            throw new ProjectCancelledException("Project has already being cancelled");
        }
    }

    public void updateData(String name, String description){
        if (this.status == ProjectStatus.COMPLETED) {
            throw new ProjectCompletedException("Completed project cannot be modified");
        }
        if (this.status == ProjectStatus.CANCELLED) {
            throw new ProjectCompletedException("Project has been Cancelled");
        }
        if (!name.isBlank()) this.name = name;
        if (!description.isBlank()) this.description = description;
        this.updatedAt = Instant.now();
    }
}
