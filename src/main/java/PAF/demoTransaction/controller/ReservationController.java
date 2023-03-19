package PAF.demoTransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import PAF.demoTransaction.payload.ReservationRequest;
import PAF.demoTransaction.service.ReservationService;

@Controller
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService resvSvc;


    @PostMapping
    public ResponseEntity<Boolean> reserveBooks(@RequestBody ReservationRequest rq) {

        Boolean bReserveSuccessful = resvSvc.reserveBook(rq.getBooks(), rq.getFullname());

        if (bReserveSuccessful) 
            return new ResponseEntity<Boolean>(bReserveSuccessful, HttpStatus.OK);
        else
            return new ResponseEntity<Boolean>(bReserveSuccessful, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    
}
