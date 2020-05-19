package fse.project.controllers;

import fse.project.model.User;
import fse.project.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(path = "/users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(path = "/users/{id}")
    public User findUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping(path = "/users/projects/{id}")
    public User findUserByProjectId(@PathVariable Long id) {
        return userService.findUserByProject(id);
    }

    @GetMapping(path = "/users/tasks/{id}")
    public User findUserByTaskId(@PathVariable Long id) {
        return userService.findUserByTask(id);
    }

    @PostMapping(path = "/users")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping(path = "/users")
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
