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
@Table(name="types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Types {
    @Id
    private int id;
    @Column(name="\"name\"")
    private String name;
}
