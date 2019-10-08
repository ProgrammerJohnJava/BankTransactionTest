/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.bank.dao;

import java.util.List;
import javax.naming.InsufficientResourcesException;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rw.bank.data.BankAccountData;
import rw.bank.domain.BankAccount;
import rw.bank.exception.DuplicateAccountNumber;
import rw.bank.exception.InactiveAccount;
import rw.bank.exception.InsufficientBalance;
import rw.bank.exception.LockedAccount;
import rw.bank.exception.NegativeAmount;

/**
 *
 * @author student
 */
public class BankAccountServiceNGTest extends OperationExecutor{
    
    public BankAccountServiceNGTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        OperationExecutor.executeOperation(BankAccountData.insert);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        OperationExecutor.executeOperation(BankAccountData.delete);
    }

    @Test
    public void testOpenAccount() {
        BankAccount ac = new BankAccount();
        ac.setAccountNumber("acc004");
        ac.setOwnerNames("Fab");
        ac.setAccountBalance(300.0);
        ac.setAccountStatus("ACTIVE");
        
        String msg = BankAccountService.openAccount(ac);
        assertEquals(msg, "Success");
    }
    
    @Test(expectedExceptions = {DuplicateAccountNumber.class})
    public void testOpenAccountNegative() {
        BankAccount ac = new BankAccount();
        ac.setAccountNumber("acc001");
        ac.setOwnerNames("Fab");
        ac.setAccountBalance(300.0);
        ac.setAccountStatus("ACTIVE");
        
        BankAccountService.openAccount(ac);
    }

    @Test
    public void testFindByAccountNo() {
        BankAccount ac = BankAccountService.findByAccountNo("acc001");
        assertEquals(ac.getAccountNumber(), "acc001");
    }

    @Test
    public void testCheckBalance() {       
        BankAccount ac = BankAccountService.findByAccountNo("acc001");
        Double amt = BankAccountService.checkBalance(ac);
        assertEquals(amt, 500.0);
    }

    @Test(expectedExceptions = {InactiveAccount.class})
    public void testCheckBalanceNegative() {       
        BankAccount ac = BankAccountService.findByAccountNo("acc002");
        BankAccountService.checkBalance(ac);
    }

    @Test(expectedExceptions = {LockedAccount.class})
    public void testFindByStatusNegative() {
        List<BankAccount> lockedAccounts = BankAccountService.findByStatus("LOCKED");
    }

    @Test
    public void testWithdrawCash() {
        BankAccount ac = BankAccountService.findByAccountNo("acc001");
        String msg = BankAccountService.withdrawCash(ac, 200.0);
        assertEquals(msg, "Success");
    }
    
    @Test(expectedExceptions = {NegativeAmount.class})
    public void testWithdrawCashNegative1() {
        BankAccount ac = BankAccountService.findByAccountNo("acc001");
        BankAccountService.withdrawCash(ac, -200.0);
    }
    
    @Test(expectedExceptions = {InsufficientBalance.class})
    public void testWithdrawCashNegative2() {
        BankAccount ac = BankAccountService.findByAccountNo("acc001");
        BankAccountService.withdrawCash(ac, 2000.0);
    }

    @Test
    public void testTransferCash() {
        BankAccount sender = BankAccountService.findByAccountNo("acc001");
        BankAccount rec = BankAccountService.findByAccountNo("acc002");
        String msg = BankAccountService.transferCash(sender, rec, 200.0);
        assertEquals(msg, "Success");
    }
    
    @Test(expectedExceptions = {LockedAccount.class})
    public void testTransferCashNegative() {
        BankAccount sender = BankAccountService.findByAccountNo("acc003");
        BankAccount rec = BankAccountService.findByAccountNo("acc005");
        BankAccountService.transferCash(sender, rec, 200.0);
    }
    
}
