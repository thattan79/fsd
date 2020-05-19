package fse.project.service;

import fse.project.ProjectTasksException;
import fse.project.model.Project;
import fse.project.model.Task;
import fse.project.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void findAllUser() {
        final User user1 = getUser(11);
        final User user2 = getUser(12);
        userService.addUser(user1);
        userService.addUser(user2);
        assertTrue(userService.findAllUsers().size() > 0);
    }

    @Test
    public void findById() {
        final User user = getUser(30);
        userService.addUser(user);
        assertNotNull(userService.findById(user.getEmployeeId()));
    }

    @Test
    public void findUserByProject() {
        final User user = getUser(14);
        userService.addUser(user);
        assertNotNull(userService.findUserByProject(user.getProject().getId()));
        assertNull(userService.findUserByProject(0));
    }

    @Test
    public void findUserByTask() {
        final User user = getUser(15);
        userService.addUser(user);
        assertNotNull(userService.findUserByTask(user.getTask().getId()));
        assertNull(userService.findUserByTask(0));
    }

    @Test
    public void updateUser() {
        final User user = getUser(6);
        userService.addUser(user);
        user.setFirstName("Name");
        userService.updateUser(user);
        assertThat(userService.findById(user.getEmployeeId()).getFirstName(), is("Name"));
    }

    @Test
    public void addUser() {
        final User user = getUser(7);
        userService.addUser(user);
        assertTrue(user.getId() != 0);
    }

    @Test
    public void deleteUser() {
        final User user = getUser(25);
        userService.addUser(user);
        userService.deleteUser(user.getEmployeeId());
        assertNull(userService.findById(user.getEmployeeId()));
        userService.deleteUser(0);
    }

    @Test(expected = ProjectTasksException.class)
    public void projectTasksException() {
        final User user1 = getUser(20);
        userService.addUser(user1);
        final User user2 = getUser(20);
        userService.addUser(user2);
    }

    private User getUser(long employeeId) {
        final User user = new User();
        user.setEmployeeId(employeeId);
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setTask(getTask());
        user.setProject(getProject());
        return user;
    }

    private Task getTask() {
        final Task task = new Task();
        task.setPriority(5);
        task.setTask("Test");
        return task;
    }

    private Project getProject() {
        final Project project = new Project();
        project.setPriority(5);
        project.setProject("Test");
        return project;
    }

}
