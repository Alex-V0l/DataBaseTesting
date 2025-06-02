package JDBC_DB.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {
    private int id;
    private String name;
    private int age;
    private int type;
    private int sex;
    private int place;
}
