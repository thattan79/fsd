package fse.project.controllers;

import fse.project.model.Project;
import fse.project.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @GetMapping(path = "/projects")
    public List<Project> findAllProjects() {
        return projectService.findAllProjects();
    }

    @GetMapping(path = "/projects/tasks")
    public List<Project> findAllProjectsWithTasksCount() {
        return projectService.findAllProjectsWithTasksCount();
    }

    @GetMapping(path = "/projects/{id}")
    public Project findProject(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @PostMapping(path = "/projects")
    public void addProject(@RequestBody Project project) {
        projectService.addProject(project);
    }

    @PutMapping(path = "/projects")
    public void updateProject(@RequestBody Project project) {
        projectService.updateProject(project);
    }

    @DeleteMapping(path = "/projects/{id}")
    public void endProject(@PathVariable Long id) {
        projectService.endProject(id);
    }
}
