
package rw.bank.exception;

public class LockedAccount extends RuntimeException{

    public LockedAccount() {
    }

    public LockedAccount(String string) {
        super(string);
    }
    
}
