package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {

    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("testName1", "testLastName1", (byte) 17);
        user.saveUser("testName2", "testLastName2", (byte) 19);
        user.saveUser("testName3", "testLastName3", (byte) 55);
        user.saveUser("testName4", "testLastName4", (byte) 46);
        user.getAllUsers();
        user.removeUserById(1);
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
