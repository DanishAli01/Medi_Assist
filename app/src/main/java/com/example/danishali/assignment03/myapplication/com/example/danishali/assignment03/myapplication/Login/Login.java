package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.Login;


public class Login {
    private String id;
    private String username;
    private String token;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Login(String id, String username, String token,String email) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.email = email;
    }

    /**
     * Method to return userid
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Method to set userid
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method to retrieve username
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method to retrieve token
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     * Method to set token
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Method to check if two Login objects are equal
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == null) return false;
        if (o == null) return false;
        if (!(o instanceof Login)) return false;

        Login login = (Login) o;

        return getId().equals(login.getId()) &&
                getUsername().equals(login.getUsername()) &&
                getToken().equals(login.getToken());
    }

    /**
     * Method to hash Login object
     * @return int
     */
    @Override
    public int hashCode() {
        int hashCode;
        hashCode = (id != null ? id.hashCode() : 0);
        hashCode = 31 * hashCode + (username != null ? username.hashCode() : 0);
        hashCode = 31 * hashCode + (token != null ? token.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
