package fse.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String firstName;

    private String lastName;

    private long employeeId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TASK_ID")
    private Task task;
}
