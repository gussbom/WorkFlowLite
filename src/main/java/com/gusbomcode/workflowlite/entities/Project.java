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

    public void draft(){
        if (this.status == ProjectStatus.ACTIVE
                || this.status == ProjectStatus.COMPLETED
                || this.status == ProjectStatus.CANCELLED){
            throw new InvalidProjectStatusTransitionException();
        }
    }
    public void active(){
        if(this.status != ProjectStatus.DRAFT){
            throw new InvalidProjectStatusTransitionException();
        }
        this.status = ProjectStatus.ACTIVE;
    }
    public void completed(){
        if(this.status != ProjectStatus.ACTIVE){
            throw new InvalidProjectStatusTransitionException();
        }
        this.status = ProjectStatus.COMPLETED;
    }
    public void cancelled(){
        if(this.status == ProjectStatus.DRAFT || this.status == ProjectStatus.ACTIVE){
        this.status = ProjectStatus.CANCELLED;
        }else if(this.status == ProjectStatus.COMPLETED){
            throw new ProjectCompletedException();
        }else{
            throw new ProjectCancelledException();
        }
    }

    public void updateData(String name, String description){
        if (this.status == ProjectStatus.COMPLETED) {
            throw new ProjectCompletedException();
        }
        if (this.status == ProjectStatus.CANCELLED) {
            throw new ProjectCompletedException();
        }

        if (!name.isBlank()) this.name = name;
        if (!description.isBlank()) this.description = description;
        this.updatedAt = Instant.now();
    }

//    todo: Resolve this method to update status alongside updateData, possibly merge both.
    public void updateStatus(ProjectStatus status){
        switch(status){
            case COMPLETED -> throw new ProjectCompletedException();
            case CANCELLED -> throw new ProjectCancelledException();
        }
    }
}
