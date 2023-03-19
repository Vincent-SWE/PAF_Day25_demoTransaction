package PAF.demoTransaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import PAF.demoTransaction.model.BankAccount;
import PAF.demoTransaction.repository.BankAccountRepo;


@Repository
public class BankAccountService {
    
    @Autowired
    BankAccountRepo bankAccountRepo;

    // Learning about "Isolation"
    // We are learning and using on topic "Dirty Reads"
    // Boolean because we want to know if the transfer is successful or not

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Boolean transferMoney(Integer accountFrom, Integer accountTo, Float amount) {
        Boolean bTransferred = false;
        Boolean bWithdrawn = false;
        Boolean bDeposited = false;
        // Boolean bCanWithdraw = false;

        BankAccount depositAccount = null;
        BankAccount withdrawalAccount = null;
        Boolean bDepositAccountValid = false;
        Boolean bWithdrawalAccountValid = false;
        Boolean bProceed = false;


        // ===============================================================================
            // This is the entire logical flow of the service layer
            // Check if accounts (withdrawer and depositor) are valid
            // Check withdrawn account has more money than withdrawal amount
            // perform the withdrawal (requires transaction)
            // perform the deposit (requires transaction)
            // check if transaction successful
        // ===============================================================================



        // Check if accounts (withdrawer and depositor) are valid
        withdrawalAccount = bankAccountRepo.retrieveAccountDetails(accountFrom);
        depositAccount = bankAccountRepo.retrieveAccountDetails(accountTo);
        bWithdrawalAccountValid = withdrawalAccount.getIsActive();
        bDepositAccountValid = depositAccount.getIsActive();
        if (bWithdrawalAccountValid && bDepositAccountValid)
        bProceed = true;


        // Check withdrawn account has more money than withdrawal amount
        if (bProceed) {
            if (withdrawalAccount.getBalance() < amount) {
            //     bCanWithdraw = true;
            // }
            // else {
            bProceed = false;
            }
        }



        if (bProceed) {

            // perform the withdrawal (requires transaction)
            bWithdrawn = bankAccountRepo.withdrawAmount(accountFrom, amount);

            // perform the deposit (requires transaction)
            bDeposited = bankAccountRepo.depositAmount(accountTo, amount);
        }


            // return transactions successful
        if (bWithdrawn && bDeposited)
            bTransferred = true;

            // The below is to check the values boolean values at the end as I was having issues
            // System.out.println("bTransferred: " + bTransferred);
            // System.out.println("bDeposited: " + bDeposited);
            return bTransferred;
     }









}