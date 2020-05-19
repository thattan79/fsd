package fse.project.service;

import fse.project.ProjectTasksException;
import fse.project.model.Project;
import fse.project.model.Task;
import fse.project.model.User;
import fse.project.repository.ProjectRepository;
import fse.project.repository.TaskRepository;
import fse.project.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private TaskRepository taskRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findById(final long employeeId) {
        final Optional<User> user = userRepository.findByEmployeeId(employeeId);
        return user.orElse(null);
    }

    public User findUserByProject(final long projectId) {
        final Optional<Project> project = projectRepository.findById(projectId);
        final List<User> users = project.map(project1 -> userRepository.findByProject(project1)).orElse(null);
        if (users != null) {
            return users.get(0);
        }
        return null;
    }

    public User findUserByTask(final long taskId) {
        final Optional<Task> task = taskRepository.findById(taskId);
        final List<User> users = task.map(task1 -> userRepository.findByTask(task1)).orElse(null);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public void addUser(final User user) {
        if (user != null) {
            Optional<User> optUser = userRepository.findByEmployeeId(user.getEmployeeId());
            if (optUser.isPresent()) {
                throw new ProjectTasksException("Employee Id already exists");
            }
            userRepository.save(user);
        }
    }

    public void updateUser(final User user) {
        userRepository.save(user);
    }

    public void deleteUser(final long employeeId) {
        final Optional<User> optUser = userRepository.findByEmployeeId(employeeId);
        optUser.ifPresent(user -> userRepository.delete(user));
    }
}
