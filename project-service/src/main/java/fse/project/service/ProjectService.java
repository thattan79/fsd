package fse.project.service;

import fse.project.model.Project;
import fse.project.model.Task;
import fse.project.model.User;
import fse.project.repository.ProjectRepository;
import fse.project.repository.TaskRepository;
import fse.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ProjectService {

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private TaskRepository taskRepository;

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> findAllProjectsWithTasksCount() {
        final List<Project> projects = new ArrayList<>();
        final Predicate<Task> isCompleted = ct -> ct.getEndDate() != null && ct.getEndDate().before(new Date());
        projectRepository.findAll().forEach(p -> {
            final Project project = new Project();
            project.setId(p.getId());
            project.setProject(p.getProject());
            project.setStartDate(p.getStartDate());
            project.setEndDate(p.getEndDate());
            project.setPriority(p.getPriority());
            List<Task> noOfTasks = retrieveTasksByProject(p);
            project.setCountOfTasks(noOfTasks.size());
            project.setCountOfCompletedTasks(noOfTasks.stream().filter(isCompleted).count());
            projects.add(project);
        });

        return projects;
    }

    public Project findById(final long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.orElse(null);
    }

    public void addProject(final Project project) {
        if (project != null) {
            projectRepository.save(project);
            if (project.getManagerId() != null) {
                Optional<User> optUser = userRepository.findById(project.getManagerId());
                if (optUser.isPresent()) {
                    User user = optUser.get();
                    user.setProject(project);
                    userRepository.save(user);
                }
            }
        }
    }

    public void updateProject(final Project project) {
        addProject(project);
    }

    public void endProject(final long id) {
        final Optional<Project> optProject = projectRepository.findById(id);
        if (optProject.isPresent()) {
            Project project = optProject.get();
            project.setEndDate(new Date());
            projectRepository.save(project);
        }
    }

    private List<Task> retrieveTasksByProject(final Project project) {
        return taskRepository.findByProject(project);
    }
}
