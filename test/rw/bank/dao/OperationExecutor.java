
package rw.bank.dao;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OperationExecutor {
    
    public static void executeOperation(Operation op) throws SQLException{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        DriverManagerDestination dest = new DriverManagerDestination("jdbc:mysql://localhost:3306/test_db", "root", "root");
        DbSetup db = new DbSetup(dest, op);
        db.launch();
    }
}
