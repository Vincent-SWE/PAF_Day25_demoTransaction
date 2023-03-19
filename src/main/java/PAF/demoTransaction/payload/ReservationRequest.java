package PAF.demoTransaction.payload;

import java.util.List;

import PAF.demoTransaction.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    
    // This is used for me to pump in when I do the API call
    private List<Book> books;
    private String fullname;


}
