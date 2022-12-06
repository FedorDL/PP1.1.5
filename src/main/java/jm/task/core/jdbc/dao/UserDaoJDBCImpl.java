package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            String queryCreateTable = "CREATE TABLE IF NOT EXISTS Users (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name_id VARCHAR(45), lastname_id VARCHAR(90), age_id INT)";
            statement.execute(queryCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            String queryCreateTable = "DROP TABLE IF EXISTS Users";
            statement.execute(queryCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (name_id, lastname_id, age_id) VALUES (?, ?, ?)")) {
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.printf("User с именем – %s добавлен в базу данных", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            String queryRemoveUserById = String.format("delete from users where id = %d", id);
            statement.execute(queryRemoveUserById);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> user2 = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name_id"));
                user.setLastName(resultSet.getString("lastname_id"));
                user.setAge(resultSet.getByte("age_id"));
                user2.add(user);
                users = user2;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            String queryCreateTable = "TRUNCATE TABLE Users";
            statement.execute(queryCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
