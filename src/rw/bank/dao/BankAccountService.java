package rw.bank.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import rw.bank.domain.BankAccount;
import rw.bank.exception.DuplicateAccountNumber;
import rw.bank.exception.InactiveAccount;
import rw.bank.exception.InsufficientBalance;
import rw.bank.exception.LockedAccount;
import rw.bank.exception.NegativeAmount;

public class BankAccountService {

    public static String openAccount(BankAccount acc) {
        if (findByAccountNo(acc.getAccountNumber()) != null) {
            throw new DuplicateAccountNumber();
        } else {
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            s.save(acc);
            s.getTransaction().commit();
            s.close();
            return "Success";
        }

    }

    public static BankAccount findByAccountNo(String accNumber) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        BankAccount acc = (BankAccount) s.get(BankAccount.class, accNumber);
        s.close();
        return acc;
    }

    public static Double checkBalance(BankAccount acc) {
        if (acc.getAccountStatus().equals("INACTIVE")) {
            throw new InactiveAccount();
        } else {
            return acc.getAccountBalance();
        }
    }
    
    public static List<BankAccount> findByStatus(String status){
        if(status.equals("LOCKED")){
            throw new LockedAccount();
        }else{
            Session s = HibernateUtil.getSessionFactory().openSession();
            Query q = s.createQuery("SELECT a FROM BankAccount a WHERE a.accountStatus = :x");
            q.setParameter("x", status);
            List<BankAccount> list = q.list();
            s.close();
            return list;
        }
    }
    
    public static String withdrawCash(BankAccount acc, Double amount){
        if(amount <= 0){
            throw new NegativeAmount();
        }else if(acc.getAccountBalance() < amount){
            throw  new InsufficientBalance();
        }else{
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            acc.setAccountBalance(acc.getAccountBalance() - amount);
            s.getTransaction().commit();
            s.close();
            return "Success";
        }
    }
    
    public static String transferCash(BankAccount sender, BankAccount rec, Double amount){
        if(sender.getAccountStatus().equals("LOCKED")){
            throw new LockedAccount();
        }else if(rec.getAccountStatus().equals("LOCKED")){
            throw  new LockedAccount();
        }else{
            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();
            sender.setAccountBalance(sender.getAccountBalance() - amount);
            rec.setAccountBalance(rec.getAccountBalance() + amount);
            s.getTransaction().commit();
            s.close();
            return "Success";
        }
    }
}

