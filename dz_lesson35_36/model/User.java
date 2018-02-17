package dz_lesson35_36.model;

import dz_lesson35_36.dao.GeneralDAO;

public class User {

    private long id;
    private String userName;
    private String password;
    private String country;
    private UserType userType;

    public User(){
    }

    public User(String userName, String password, String country, UserType userType) {
        this.id = setId();
        this.userName = userName;
        this.password = password;
        this.country = country;
        this.userType = userType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public long setId() {
       return this.id = GeneralDAO.generateId();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return  id + ","
                 + userName + ","
                 + password + ","
                 + country + ","
                 + userType;
    }
}
