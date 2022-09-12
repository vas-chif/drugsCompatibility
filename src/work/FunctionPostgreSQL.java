package work;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionPostgreSQL implements IFunctionPostgreSqlPV {

    private Connection connection;
    private final String host;
    private final String port;
    private final String username;
    private final String password;
    private final String database;

    public FunctionPostgreSQL(String host, String port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;

    }// end constructor ConnectionPostegreSQL(String host, String port, String username, String password, String database, Connection connection)


    private Connection connectToDB() throws SQLException {
        this.connection = null;

        if (host.isEmpty() || port.isEmpty() || database.isEmpty() || username.isEmpty() | password.isEmpty()) {
            throw new SQLException("Database credentials missing");
        }

        try {
            Class.forName("org.postgresql.Driver");

            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + this.database, this.username, this.password);

            if (this.connection != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection filed");
            }// end else of if (conn != null)

        } catch (Exception e) {
            System.out.println(e);
        }// end catch (Exception e) of try

        return connection;
    }// end method connectToDB()

    private void disconnection() throws Exception {
        if (this.connection != null) {
            this.connection.close();
            this.connection = null;
        }// end if (this.connection != null)

    }// end method disconnection()

    @Override
    public void createTable(String tableName) {
        Statement statement;

        try {
            this.connectToDB();

            String query = "create table " + tableName + " (id integer primary key, name_of_drugs varchar(200) not null);";
            statement = this.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");

        } catch (Exception e) {
            System.out.println(e);
        }// end  catch (Exception e) of try
    }// end method createTable( String tableName)

    @Override
    public String readTable(String tableName, List<String> columnList) {
        Statement statement;
        ResultSet rs = null;
        String output = "\t";

        for (int count = 2; count<= columnList.size(); count++) {
            output += (columnList.get(count -1)) + "\t";
           
        }

        try {
            this.connectToDB();

            String query = String.format("select * from %s", tableName);
            statement = this.connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                int count = 0;
                String outContainTable = "";


                for (String elment : columnList) {
                    outContainTable += rs.getString(elment) + "\t";
                    count++;
                }
                output += "\n" + outContainTable;

            }// end loop while (rs.next())

        } catch (Exception e) {
            System.out.println(e);
        }// end  catch (Exception e) of try


        return output;
    }// end method readTable(Connection conn, String table_name)

    @Override
    public List<String> readColumnDate(String tableName, String column) {
        Statement statement;
        ResultSet rs = null;
        List<String> extractListOfTable = new ArrayList<>();

        try {
            this.connectToDB();

            String query = String.format("select * from %s", tableName);
            statement = this.connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                String elementOfTable = "";
                elementOfTable = rs.getString(column);
                extractListOfTable.add(elementOfTable);
                // System.out.println(output);

            }// end loop while (rs.next())


        } catch (Exception e) {
            System.out.println(e);
        }// end  catch (Exception e) of try
        return extractListOfTable;
    }// end method readColumnDate(Connection conn, String table_name)

    @Override
    public String readSpecificDate(String tableName, String column, String nameRow) {
        Statement statement;
        ResultSet rs = null;
        String searchedRow = "";
        String searchedItem = "";

        try {
            this.connectToDB();

            String query = String.format("select * from %s", tableName);
            statement = this.connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                searchedRow = rs.getString(columnName);// non modifiable column date
                if (searchedRow.equalsIgnoreCase(nameRow)){
                    //System.out.println(searchedRow);
                    searchedItem = rs.getString(column);
                    //System.out.println(searchedItem);
                }

                // System.out.println(output);

            }// end loop while (rs.next())


        } catch (Exception e) {
            System.out.println(e);
        }// end  catch (Exception e) of try
        return searchedItem;
    }

    @Override
    public void createTemporaryTable(String tableName, List<String> list) {
        Statement statement;

        try {
            this.connectToDB();

            String query = String.format("create table %s (\n\"name_of_drugs\" varchar(50) not null,\n", tableName);

            for (String element : list) {

                if (element == list.get(list.size() - 1)) {
                    query += String.format("\"%s\" varchar(50) not null\n", element);
                    break;
                }

                query += String.format("\"%s\" varchar(50) not null,\n", element);

            }

            query += ");";

            System.out.println(query);

            statement = this.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");

        } catch (Exception e) {
            System.out.println(e);
        }// end  catch (Exception e) of try
    }// end method createTemporaryTable(Connection conn, String tableName)

    @Override
    public void compileRow(Connection conn, String tableName, List<String> listColumn, List<String> listRow, List<String> listQueryRow) {
        Statement statement;

        try {
            String setQueryRow = "";
            int count = 0;

            for (String setRow : listRow) {
                setQueryRow += String.format("UPDATE public.%s\nSET ", tableName);

                for (int i = 0; i < listColumn.size(); i++) {
                    if (i == listColumn.size() - 1) {
                        setQueryRow = setQueryRow.substring(0, setQueryRow.length() - 1);
                        break;
                    }// end if (elenemt == listRow.get(listRow.size() - 1))

                    setQueryRow += String.format("%s = '%s',", listColumn.get(i + 1), listQueryRow.get(count));

                    // nameListQueryRow = listColumn.get(count);

                }// end loop for (String setNameRrow : listRow)

                setQueryRow += String.format("\nwhere %s = '%s'; \n", listColumn.get(0), setRow);

                count++;
            }// end loop for (String element : listColumn)

            System.out.println(setQueryRow);

            statement = conn.createStatement();
            statement.executeUpdate(setQueryRow);
            System.out.println("Row is compile");
        } catch (Exception e) {
            System.out.println(e);
        }// end catch (Exception e) of try
    }//end compileRow(Connection conn, String tableName, List<String> listColumn, List<String> listRow, List<String> listQueryRow)

    @Override
    public void deleteTable(String tableName) {
        Statement statement;

        try {

            this.connectToDB();
            String query = String.format("drop table %s", tableName);
            statement = this.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table is dropped");

        } catch (Exception e) {
            System.out.println(e);
        }// end  catch (Exception e) of try

    }


}// end class ConnectionPostegreSQL
