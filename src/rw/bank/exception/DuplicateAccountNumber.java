
package rw.bank.exception;

public class DuplicateAccountNumber extends RuntimeException{

    public DuplicateAccountNumber() {
    }

    public DuplicateAccountNumber(String string) {
        super(string);
    }
    
}
