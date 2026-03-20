package com.gusbomcode.workflowlite.project.utils.converters;

import com.gusbomcode.workflowlite.project.enums.ProjectStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProjectStatusConverter implements Converter<String, ProjectStatus> {

    @Override
    public ProjectStatus convert(String source) {
        try {
            return ProjectStatus.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid project status: " + source);
        }
    }
}
