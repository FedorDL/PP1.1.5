package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Users (id TINYINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name_id VARCHAR(45), lastname_id VARCHAR(90), age_id BIGINT)");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.getMessage();
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
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?")) {
            preparedStatement.setLong(1,id);
            System.out.println("User с id = " + id + " удален");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name_id"));
                user.setLastName(resultSet.getString("lastname_id"));
                user.setAge(resultSet.getByte("age_id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
