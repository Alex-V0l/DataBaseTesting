package JDBC_DB.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worker {
    private int id;
    private String name;
    private int age;
    private int position;
}
