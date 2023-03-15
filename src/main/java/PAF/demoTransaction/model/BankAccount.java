package PAF.demoTransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    
    private Integer id;
    private String fullName;
    private Boolean isActive;
    private String acctType;
    private Float balance;





}
