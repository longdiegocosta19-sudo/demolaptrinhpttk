package com.shopman.dao;

import com.shopman.util.Db;
import java.sql.Connection;
import java.sql.SQLException;

/** Common JDBC access class corresponding to DAO6 in the class diagram. */
public class DAO6 {
    protected Connection getConnection() throws SQLException {
        return Db.getConnection();
    }
}
