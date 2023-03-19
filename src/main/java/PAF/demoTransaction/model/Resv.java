package PAF.demoTransaction.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resv {

    private Integer id;
    private Date resvDate;
    private String fullName;


}
