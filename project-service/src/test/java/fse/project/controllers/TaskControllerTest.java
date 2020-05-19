package fse.project.controllers;

import fse.project.model.Task;
import fse.project.service.TaskService;
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
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Test
    public void findAllTasks() throws Exception {
        final Task task = new Task();

        when(service.findAllTasks()).thenReturn(asList(task));

        this.mockMvc.perform(get("/alltasks")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(service, times(1)).findAllTasks();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findTaskById() throws Exception {
        final Task task = new Task();

        when(service.findById(1)).thenReturn(task);

        this.mockMvc.perform(get("/task/1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

        verify(service, times(1)).findById(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findTaskByProject() throws Exception {
        final Task task = new Task();
        when(service.findTaskByProject(1)).thenReturn(asList(task));
        this.mockMvc.perform(get("/task/project/1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

        verify(service, times(1)).findTaskByProject(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void addTask() throws Exception {
        final Task task = new Task();
        doNothing().when(service).addTask(task);
        this.mockMvc.perform(post("/task")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(task))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        ArgumentCaptor<Task> taskCapture = ArgumentCaptor.forClass(Task.class);
        verify(service, times(1)).addTask(taskCapture.capture());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateTask() throws Exception {
        final Task task = new Task();
        doNothing().when(service).updateTask(task);
        this.mockMvc.perform(put("/task/1")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(task))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        ArgumentCaptor<Task> taskCapture = ArgumentCaptor.forClass(Task.class);
        verify(service, times(1)).updateTask(taskCapture.capture());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteTask() throws Exception {
        doNothing().when(service).deleteTask(1);
        this.mockMvc.perform(delete("/task/1")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteTask(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void endTask() throws Exception {
        doNothing().when(service).endTask(1);
        this.mockMvc.perform(put("/endtask/1")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        verify(service, times(1)).endTask(1);
        verifyNoMoreInteractions(service);
    }

}
