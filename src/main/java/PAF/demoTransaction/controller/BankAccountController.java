package PAF.demoTransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import PAF.demoTransaction.payload.TransferRequest;
import PAF.demoTransaction.service.BankAccountService;

@Controller
@RequestMapping("/accounts")
public class BankAccountController {
    
    @Autowired
    BankAccountService bankAccountSvc;


    @PostMapping
    public ResponseEntity<Boolean> transferMoney(@RequestBody TransferRequest transferRequest) {

        // to keep track if transferred
        Boolean bTransferred = false;

        bTransferred =  bankAccountSvc.transferMoney(transferRequest.getAccountFrom(), transferRequest.getAccountTo(), transferRequest.getAmount());

        if (bTransferred) {
            return new ResponseEntity<Boolean>(bTransferred, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Boolean>(bTransferred, HttpStatus.BAD_REQUEST);
        }
    }











}
