package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl user = new UserDaoJDBCImpl();
        user.dropUsersTable();
        user.createUsersTable();
        user.saveUser("testName", "testLastName", (byte) 17);
        System.out.println(user.getAllUsers().get(0));

    }
}
