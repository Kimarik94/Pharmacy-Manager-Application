package ru.imp.platov.appUsers;

public class User {
        private Integer user_id;
        private String user_name;
        private String user_surname;
        private String user_login;
        private String user_password;
        private String user_role;

        public User(Integer user_id, String userName, String userSurname, String userLogin, String userPassword, String userRole) {
            this.user_id = user_id;
            this.user_name = userName;
            this.user_surname = userSurname;
            this.user_login = userLogin;
            this.user_password = userPassword;
            this.user_role = userRole;
        }

    public Integer getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
