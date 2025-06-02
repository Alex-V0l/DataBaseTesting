package JDBC_DB.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Places {
    private int id;
    private int row;
    private int place_num;
    private String name;
}
