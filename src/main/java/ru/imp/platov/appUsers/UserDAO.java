package ru.imp.platov.appUsers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.imp.platov.database.DatabaseManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final DataSource dataSource;

    public UserDAO() {
        this.dataSource = DatabaseManager.getDataSource();
    }


    public void addUser(User user) throws SQLException {
        String addUser = "INSERT INTO users (user_name, user_surname, user_login, user_password, user_role) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(addUser)) {
            statement.setString(1, user.getUser_name());
            statement.setString(2, user.getUser_surname());
            statement.setString(3, user.getUser_login());
            statement.setString(4, user.getUser_password());
            statement.setString(5, user.getUser_role());
            statement.executeUpdate();
        }
    }

    public User getUserByLogin(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE user_login=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String surname = resultSet.getString("user_surname");
                String userLogin = resultSet.getString("user_login");
                String password = resultSet.getString("user_password");
                Role role = Role.valueOf(resultSet.getString("user_role"));
                return new User(userId, name, surname, userLogin, password, role.toString());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(Integer id) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String surname = resultSet.getString("user_surname");
                String userLogin = resultSet.getString("user_login");
                String password = resultSet.getString("user_password");
                Role role = Role.valueOf(resultSet.getString("user_role"));
                return new User(userId, name, surname, userLogin, password, role.toString());
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "SELECT user_id, user_name, user_surname, user_login, user_password, user_role FROM users";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userSurname = resultSet.getString("user_surname");
                String userLogin = resultSet.getString("user_login");
                String userPassword = resultSet.getString("user_password");
                Role userRole = Role.valueOf(resultSet.getString("user_role"));
                userList.add(new User(userId, userName, userSurname, userLogin, userPassword, userRole.toString()));
            }
        }
        return userList;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET user_name = ?, user_surname = ?, " +
                "user_login = ?, user_password = ?, user_role = ? WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUser_name());
            statement.setString(2, user.getUser_surname());
            statement.setString(3, user.getUser_login());
            statement.setString(4, user.getUser_password());
            statement.setString(5, user.getUser_role());
            statement.setInt(6, user.getUser_id());
            statement.executeUpdate();
        }
    }

    public void deleteUser(Integer id) throws SQLException {
        String sqlDelete = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlDelete)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public String checkPassword(String userName, String userPassword) throws SQLException {
        String role = null;
        String query = "SELECT * FROM users WHERE user_login=? AND user_password=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            statement.setString(2, userPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString("user_role");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return role;
    }
}
