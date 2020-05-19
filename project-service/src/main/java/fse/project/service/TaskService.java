package fse.project.service;

import fse.project.model.ParentTask;
import fse.project.model.Project;
import fse.project.model.Task;
import fse.project.model.User;
import fse.project.repository.ParentTaskRepository;
import fse.project.repository.ProjectRepository;
import fse.project.repository.TaskRepository;
import fse.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private ParentTaskRepository parentTaskRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private UserRepository userRepository;

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findById(final long id) {
        final Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    @Transactional
    public void updateTask(final Task task) {
        setUser(task);
        taskRepository.save(task);
    }

    @Transactional
    public void addTask(final Task task) {
        if (task.getParentTask() != null) {
            Optional<ParentTask> optParentTask = parentTaskRepository.findById(task.getParentTask().getId());
            optParentTask.ifPresent(task::setParentTask);
        }
        if (task.getProject() != null) {
            Optional<Project> optProject = projectRepository.findById(task.getProject().getId());
            optProject.ifPresent(task::setProject);
        }

        taskRepository.save(task);
        setUser(task);
    }

    @Transactional
    public void deleteTask(final long id) {
        final Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            final Task task = taskOpt.get();
            task.setParentTask(null);
            task.setProject(null);
            taskRepository.delete(task);
        }
    }

    @Transactional
    public void endTask(final long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setEndDate(new Date());
            taskRepository.save(task);
        }
    }

    public List<Task> findTaskByProject(final long projectId) {
        final Optional<Project> project = projectRepository.findById(projectId);
        return project.map(project1 -> taskRepository.findByProject(project1)).orElse(null);
    }

    private void setUser(final Task task) {
        if (task.getUserId() != null) {
            final Optional<User> optUser = userRepository.findByEmployeeId(task.getUserId());
            if (optUser.isPresent()) {
                final User user = optUser.get();
                user.setTask(task);
                user.setProject(task.getProject());
                userRepository.save(user);
            }
        }
    }

}
