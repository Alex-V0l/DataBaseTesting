package Hibernate_DB.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "animal")
@Entity
public class Animal {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "\"name\"")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "type")
    private int type;
    @Column(name = "sex")
    private int sex;
    @Column(name = "place")
    private int place;
}

