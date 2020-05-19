package fse.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String task;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Integer priority;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PARENT_TASK_ID")
    private ParentTask parentTask;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    private String status;

    @Transient
    private Long userId;
}
