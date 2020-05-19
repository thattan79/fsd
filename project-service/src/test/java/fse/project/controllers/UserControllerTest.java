package fse.project.controllers;

import fse.project.model.User;
import fse.project.service.UserService;
import fse.project.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void findAllUsers() throws Exception {
        final User user = new User();

        when(service.findAllUsers()).thenReturn(asList(user));

        this.mockMvc.perform(get("/users")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(service, times(1)).findAllUsers();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findById() throws Exception {
        final User user = new User();

        when(service.findById(1)).thenReturn(user);

        this.mockMvc.perform(get("/users/1")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

        verify(service, times(1)).findById(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findUserByProjectId() throws Exception {
        final User user = new User();

        when(service.findUserByProject(1)).thenReturn(user);

        this.mockMvc.perform(get("/users/projects/1")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

        verify(service, times(1)).findUserByProject(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void findUserByTaskId() throws Exception {
        final User user = new User();

        when(service.findUserByTask(1)).thenReturn(user);
        this.mockMvc.perform(get("/users/tasks/1")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

        verify(service, times(1)).findUserByTask(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void addUser() throws Exception {
        final User user = new User();

        doNothing().when(service).addUser(user);
        this.mockMvc.perform(post("/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
        verify(service, times(1)).addUser(userCapture.capture());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateUser() throws Exception {
        final User user = new User();

        doNothing().when(service).updateUser(user);
        this.mockMvc.perform(put("/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user))
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
        verify(service, times(1)).updateUser(userCapture.capture());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteUser() throws Exception {
        doNothing().when(service).deleteUser(1);
        this.mockMvc.perform(delete("/users/1")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteUser(1);
        verifyNoMoreInteractions(service);
    }
}
