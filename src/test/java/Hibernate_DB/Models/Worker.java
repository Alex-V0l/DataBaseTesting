package Hibernate_DB.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="worker")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worker {
    @Id
    private int id;
    @Column(name="\"name\"", nullable = false)
    private String name;
    @Column(name="age")
    private int age;
    @Column(name="\"position\"")
    private int position;
}
