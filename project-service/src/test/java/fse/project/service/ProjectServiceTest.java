package fse.project.service;

import fse.project.model.ParentTask;
import fse.project.model.Project;
import fse.project.model.Task;
import fse.project.model.User;
import fse.project.repository.TaskRepository;
import fse.project.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProjectServiceTest {

    @Resource
    private ProjectService projectService;

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private UserRepository userRepository;

    @Test
    public void findAllProjects() {
        final Project project1 = getProject();
        final Project project2 = getProject();
        projectService.addProject(project1);
        projectService.addProject(project2);
        assertTrue(projectService.findAllProjects().size() > 0);
    }

    @Test
    public void findById() {
        final Project project = getProject();
        projectService.addProject(project);
        assertNotNull(projectService.findById(project.getId()));
    }

    @Test
    public void findAllProjectsHavingTask() {
        final Project project = getProject();
        projectService.addProject(project);
        final Task task = getTask();
        task.setProject(project);
        taskRepository.save(task);
        List<Project> projects = projectService.findAllProjectsWithTasksCount();
        assertNotNull(projects);
        assertThat(projects, hasSize(1));
        assertThat(projects.iterator().next().getCountOfCompletedTasks(), is(1L));
    }

    @Test
    public void addProject() {
        final ParentTask parentTask = new ParentTask();

        userRepository.save(getUser());
        final Project project = getProject();
        projectService.addProject(project);
        assertTrue(project.getId() != 0);
    }

    @Test
    public void updateProject() {
        final Project project = getProject();
        projectService.addProject(project);
        project.setPriority(10);
        projectService.updateProject(project);
        assertEquals(10, projectService.findById(project.getId()).getPriority(), 0);
    }

    @Test
    public void endProject() {
        final Project project = getProject();
        projectService.addProject(project);
        projectService.endProject(project.getId());
        assertNotNull(projectService.findById(project.getId()).getEndDate());
    }

    private Project getProject() {
        final Project project = new Project();
        project.setPriority(5);
        project.setProject("Test");
        project.setStartDate(new Date());
        project.setManagerId(1L);
        return project;
    }

    private Task getTask() {
        final Task task = new Task();
        task.setPriority(5);
        task.setTask("Test");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        return task;
    }

    private User getUser() {
        final User user = new User();
        user.setEmployeeId(1L);
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setTask(getTask());
        user.setProject(getProject());
        return user;
    }

}
