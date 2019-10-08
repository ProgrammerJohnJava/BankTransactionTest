
package rw.bank.data;

import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;

public class BankAccountData {
    public static Operation insert = Operations.insertInto("bankaccount")
                                     .columns("accountNumber","ownerNames","accountStatus","accountBalance")
                                     .values("acc001","Manzi","ACTIVE",500.0)
                                     .values("acc002","Mandera","INACTIVE",500.0)
                                     .values("acc003","Maliza","LOCKED",500.0)
                                     .values("acc005","Liza","LOCKED",500.0)
                                     .build();
    
    public static Operation delete = Operations.deleteAllFrom("bankaccount");
}
