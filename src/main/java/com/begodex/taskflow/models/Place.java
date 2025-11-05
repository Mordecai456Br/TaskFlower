package com.begodex.taskflow.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity(name = "places")
@Table(name = "places")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Place implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 45)

    private String description;


}