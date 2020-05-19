package fse.project.controllers;

import fse.project.model.Task;
import fse.project.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
public class TaskController {

    @Resource
    private TaskService taskService;

    @GetMapping("/alltasks")
    public List<Task> findAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/task/{id}")
    public Task findTask(@PathVariable(value = "id") Long id) {
        return taskService.findById(id);
    }

    @PutMapping("/task/{id}")
    public void updateTask(@PathVariable(value = "id") Long id, @RequestBody Task task) {
        task.setId(id);
        taskService.updateTask(task);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable(value = "id") Long id) {
        taskService.deleteTask(id);
    }

    @PostMapping("/task")
    public void addTask(@RequestBody Task task) {
        taskService.addTask(task);
    }

    @GetMapping(path = "/task/project/{id}")
    public List<Task> findTaskByProject(@PathVariable Long id) {
        return taskService.findTaskByProject(id);
    }

    @PutMapping(path = "/endtask/{id}")
    public void endTask(@PathVariable Long id) {
        taskService.endTask(id);
    }
}
