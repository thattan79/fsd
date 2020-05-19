package fse.project.service;

import fse.project.model.ParentTask;
import fse.project.model.Project;
import fse.project.model.Task;
import fse.project.model.User;
import fse.project.repository.ProjectRepository;
import fse.project.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Resource
    private TaskService taskService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Test
    public void findAllTasks() {
        final Task task1 = getTask();
        final Task task2 = getTask();
        taskService.addTask(task1);
        taskService.addTask(task2);
        assertTrue(taskService.findAllTasks().size() > 0);
    }

    @Test
    public void findById() {
        final Task task = getTask();
        taskService.addTask(task);
        assertNotNull(taskService.findById(task.getId()));
    }

    @Test
    public void updateTask() {
        final Task task = getTask();
        taskService.addTask(task);
        task.setPriority(10);
        taskService.updateTask(task);
        assertEquals(10, taskService.findById(task.getId()).getPriority(), 0);
    }

    @Test
    public void addTask() {
        final User user = getUser();
        final Task task = getTask();
        task.setParentTask(getParentTask(1L));
        task.setProject(getProject());
        user.setTask(getTask());
        userRepository.save(user);
        task.setUserId(1L);
        taskService.addTask(task);
        assertTrue(task.getId() != 0);
    }

    @Test
    public void deleteTask() {
        final Task task = getTask();
        taskService.addTask(task);
        taskService.deleteTask(task.getId());
        assertNull(taskService.findById(task.getId()));
    }

    @Test
    public void endTask() {
        final Task task = getTask();
        taskService.addTask(task);
        taskService.endTask(task.getId());
        assertNotNull(taskService.findById(task.getId()).getEndDate());
    }

    @Test
    public void findTaskByProject() {
        final Task task = getTask();
        task.setParentTask(getParentTask(10L));
        task.setProject(getProject());
        taskService.addTask(task);
        assertTrue(taskService.findTaskByProject(task.getProject().getId()).size() > 0);
    }

    private Task getTask() {
        final Task task = new Task();
        task.setPriority(5);
        task.setTask("Test");
        task.setStartDate(new Date());
        return task;
    }

    private User getUser() {
        final User user = new User();
        user.setEmployeeId(1L);
        user.setFirstName("Test");
        user.setLastName("Unit");
        return user;
    }

    private Project getProject() {
        final Project project = new Project();
        project.setPriority(5);
        project.setProject("Test");
        projectRepository.save(project);
        return project;
    }

    private ParentTask getParentTask(long id) {
        final ParentTask parentTask = new ParentTask();
        parentTask.setTask("Test");
        parentTask.setId(id);
        return parentTask;
    }
}
