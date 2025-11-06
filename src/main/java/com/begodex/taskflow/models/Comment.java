package com.begodex.taskflow.models;

import jakarta.persistence.*;
import java.time.Instant;
import com.begodex.taskflow.models.user.User;
import com.begodex.taskflow.models.task.Task;
import lombok.*;

/* Comentários vinculados a Tasks, escritos por Users */
@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4000)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    private Instant createdAt;
    
}
