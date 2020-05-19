package fse.project.controllers;

import fse.project.model.Project;
import fse.project.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static fse.project.util.TestUtil.APPLICATION_JSON_UTF8;
import static fse.project.util.TestUtil.convertObjectToJsonBytes;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService service;

    @Test
    public void findAllProjects() throws Exception {
        final Project project = new Project();

        when(service.findAllProjects()).thenReturn(asList(project));

        this.mockMvc.perform(get("/projects")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(service, times(1)).findAllProjects();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findAllProjectsWithTask() throws Exception {
        final Project project = new Project();

        when(service.findAllProjectsWithTasksCount()).thenReturn(asList(project));

        this.mockMvc.perform(get("/projects/tasks")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(service, times(1)).findAllProjectsWithTasksCount();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findProject() throws Exception {
        final Project project = new Project();

        when(service.findById(1)).thenReturn(project);

        this.mockMvc.perform(get("/projects/1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

        verify(service, times(1)).findById(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void addProject() throws Exception {
        final Project project = new Project();

        doNothing().when(service).addProject(project);

        this.mockMvc.perform(post("/projects")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(project))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        ArgumentCaptor<Project> projCapture = ArgumentCaptor.forClass(Project.class);
        verify(service, times(1)).addProject(projCapture.capture());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateProject() throws Exception {
        final Project project = new Project();
        doNothing().when(service).updateProject(project);

        this.mockMvc.perform(put("/projects")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(project))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        ArgumentCaptor<Project> projCapture = ArgumentCaptor.forClass(Project.class);
        verify(service, times(1)).updateProject(projCapture.capture());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void endProject() throws Exception {
        doNothing().when(service).endProject(1);

        this.mockMvc.perform(delete("/projects/1")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        verify(service, times(1)).endProject(1);
        verifyNoMoreInteractions(service);
    }
}
