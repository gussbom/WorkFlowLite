package com.gusbomcode.workflowlite.project.entities;

import com.gusbomcode.workflowlite.project.enums.ProjectStatus;
import com.gusbomcode.workflowlite.project.exceptions.InvalidProjectStatusTransitionExceptionProject;
import com.gusbomcode.workflowlite.project.exceptions.ProjectCancelledExceptionProject;
import com.gusbomcode.workflowlite.project.exceptions.ProjectCompletedExceptionProject;
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

    public void draft(){
        if (this.status == ProjectStatus.ACTIVE
                || this.status == ProjectStatus.COMPLETED
                || this.status == ProjectStatus.CANCELLED){
            throw new InvalidProjectStatusTransitionExceptionProject();
        }
    }
    public void active(){
        if(this.status != ProjectStatus.DRAFT){
            throw new InvalidProjectStatusTransitionExceptionProject();
        }
        this.status = ProjectStatus.ACTIVE;
    }
    public void completed(){
        if(this.status != ProjectStatus.ACTIVE){
            throw new InvalidProjectStatusTransitionExceptionProject();
        }
        this.status = ProjectStatus.COMPLETED;
    }
    public void cancelled(){
        if(this.status == ProjectStatus.DRAFT || this.status == ProjectStatus.ACTIVE){
        this.status = ProjectStatus.CANCELLED;
        }else if(this.status == ProjectStatus.COMPLETED){
            throw new ProjectCompletedExceptionProject();
        }else{
            throw new ProjectCancelledExceptionProject();
        }
    }

    public void updateData(String name, String description){
        if (this.status == ProjectStatus.COMPLETED) {
            throw new ProjectCompletedExceptionProject();
        }
        if (this.status == ProjectStatus.CANCELLED) {
            throw new ProjectCompletedExceptionProject();
        }

        if (!name.isBlank()) this.name = name;
        if (!description.isBlank()) this.description = description;
        this.updatedAt = Instant.now();
    }

    public void updateStatus(ProjectStatus status){
        switch(status){
            case DRAFT -> this.draft();
            case ACTIVE -> this.active();
            case COMPLETED -> this.completed();
            case CANCELLED -> this.cancelled();
        }
    }
}
