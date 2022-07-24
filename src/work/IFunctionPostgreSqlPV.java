package work;

import java.sql.Connection;
import java.util.List;

public interface IFunctionPostgreSqlPV {
    String host = "localhost";
    String port = "5432";
    String database = "farmaci";
    String user = "postgres";
    String pass = "root";

    String nameTable = "farmaci";
    String columnName = "name_of_drugs";

    void createTable(String tableName);

    String readTable(String tableName, List<String> columnList);

    List<String> readColumnDate(String tableName, String column);
    String readSpecificDate(String tableName, String column,String nameRow);

    void createTemporaryTable(String tableName, List<String> list);

    void compileRow(Connection conn, String tableName, List<String> listColumn, List<String> listRow, List<String> listQueryRow);

    void deleteTable(String tableName);
}
