package PAF.demoTransaction.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import PAF.demoTransaction.model.BankAccount;

@Repository
public class BankAccountRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    // Our SQL statments
    private final String CHECK_BALANCE_SQL = " SELECT balance FROM bankaccount WHERE id = ? ";
    private final String GET_ACCOUNT_SQL = " SELECT * FROM bankaccount WHERE id = ? ";
    private final String WITHDRAW_SQL = "UPDATE bankaccount SET balance = balance - ? WHERE id = ? ";
    private final String DEPOSIT_SQL = "UPDATE bankaccount SET balance = balance + ? WHERE id = ? ";

    // We are not using it but we write the below SQL statment for learning purposes
    private final String CREATE_ACCOUNT_SQL = " INSERT INTO bankaccount (full_name, is_active, acct_type, balance) VALUES (?, ?, ?, ?) ";



    // We are calling this function from the service lay thus we cannot make it "private"
    // Checking the balance of the account
    public Boolean checkBalance(Integer accountId, Float withdrawnAmount) {
        Boolean bWithdrawBalanceAvailable = false;

        Float returnedBalance = jdbcTemplate.queryForObject(CHECK_BALANCE_SQL, Float.class, accountId);

        if (withdrawnAmount <= returnedBalance) {
            bWithdrawBalanceAvailable = true;
        }

        return bWithdrawBalanceAvailable;

    }

    // Getting the account
    // Find out why BeanPropertyRowMapper.newInstance(BankAccount.class) works but just "BankAccount.class" does not work when POSTING with POSTMAN
    public BankAccount retrieveAccountDetails(Integer accountId) {
        BankAccount bankAccount = null;

        bankAccount = jdbcTemplate.queryForObject(GET_ACCOUNT_SQL, BeanPropertyRowMapper.newInstance(BankAccount.class), accountId);

        return bankAccount;
    }


    public Boolean withdrawAmount(Integer accountId, Float withdrawnAmount) {
        Boolean bWithdrawn = false;

        int iUpdated = jdbcTemplate.update(WITHDRAW_SQL, withdrawnAmount, accountId);

        if (iUpdated > 0) {
            bWithdrawn = true;
        }
        return bWithdrawn;
    }

    // Check against Darryl's code to see if I did mine correctly
    public Boolean depositAmount(Integer accountId, Float depositAmount) {
        Boolean bDeposited= false;

        int iDeposited = 0;
        iDeposited = jdbcTemplate.update(DEPOSIT_SQL, depositAmount, accountId);

        if (iDeposited > 0) {
            return bDeposited =true;
        }

        return bDeposited;

    }



    public Boolean createAccount(BankAccount bankAccount) {

        Boolean bCreated = false;
        jdbcTemplate.execute(CREATE_ACCOUNT_SQL, new PreparedStatementCallback<Boolean>(){
        
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, bankAccount.getFullName());
                ps.setBoolean(2, bankAccount.getIsActive());
                ps.setString(3, bankAccount.getAcctType());
                ps.setFloat(4, bankAccount.getBalance());
                Boolean result = ps.execute();

                return result;
            }
        });

        return bCreated;
    }







}
