
package rw.bank.exception;

public class InsufficientBalance extends RuntimeException{

    public InsufficientBalance() {
    }

    public InsufficientBalance(String string) {
        super(string);
    }
    
}
