package com.github.realericvega.minetubers.manager.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.function.Function;

public class DatabaseManager {

    private static DatabaseManager instance;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String database;

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private int port;

    @Getter
    @Setter
    private Connection connection;


    public static DatabaseManager init() {
        if (instance == null)
            instance = new DatabaseManager();

        return instance;
    }

    public void connect() {
        setHost("localhost");
        setDatabase("users");
        setUsername("root");
        setPassword("");
        setPort(3306);

        try {
            synchronized (this) {
                if (this.connection != null && !this.getConnection().isClosed())
                    return;

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://"
                        + this.host + ":" + this.port + "/" + this.database, this.username, this.password));

                System.out.println("Successfully connected to Database!");
            }
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    public PreparedStatement createPreparedStatement(String query) throws SQLException {
        PreparedStatement ps = null;

        if (this.connection != null && !this.connection.isClosed())
            ps = this.connection.prepareStatement(query);

        return ps;
    }

    public void createTable(String table, Function<String, String> function) {
        function.apply(table);
//        try {
//            if (tableExists(this.connection, table))
//                return;
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
//
//        Statement statement = null;
//
//        try {
//            statement = this.connection.createStatement();
//            statement.executeUpdate("CREATE TABLE " + table);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private boolean tableExists(Connection conn, String table) throws SQLException {
        boolean tExists = false;
        ResultSet rs = conn.getMetaData().getTables(null, null, table, null);
        Throwable throwable = null;

        try {
            while (rs.next()) {
                String tName = rs.getString(table);
                if (tName != null && tName.equals(table)) {
                    tExists = true;
                    break;
                }
            }
        } catch (Throwable throwable1) {
            throwable = throwable1;
            throw throwable1;
        } finally {
            if (rs != null) {
                if (throwable != null) {
                    try {
                        rs.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    rs.close();
                }
            }
        }

        return tExists;
    }

    public void stop() {
        try {
            this.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
