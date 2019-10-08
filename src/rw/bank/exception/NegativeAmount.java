
package rw.bank.exception;

public class NegativeAmount extends RuntimeException{

    public NegativeAmount() {
    }

    public NegativeAmount(String string) {
        super(string);
    }
    
}
