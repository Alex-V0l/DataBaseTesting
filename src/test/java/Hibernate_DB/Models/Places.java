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
@Table(name = "places")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Places {

    @Id
    int id;
    @Column(name = "\"row\"")
    int row;
    @Column(name = "place_num")
    int place_num;
    @Column(name = "\"name\"")
    String name;
}