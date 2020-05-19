package fse.project.repository;

import fse.project.model.Project;
import fse.project.model.Task;
import fse.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByProject(final Project project);

    List<User> findByTask(final Task task);

    Optional<User> findByEmployeeId(final long employeeId);
}
