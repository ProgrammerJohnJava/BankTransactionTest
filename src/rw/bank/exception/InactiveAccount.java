
package rw.bank.exception;

public class InactiveAccount extends RuntimeException{

    public InactiveAccount() {
    }

    public InactiveAccount(String string) {
        super(string);
    }
    
}
