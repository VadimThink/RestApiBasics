package com.epam.esm.dto;

public class AuthenticationResponseDto {

    private String login;

    private String token;

    public AuthenticationResponseDto() {
    }

    public AuthenticationResponseDto(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationResponseDto that = (AuthenticationResponseDto) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        return token != null ? token.equals(that.token) : that.token == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthenticationResponseDto{" +
                "login='" + login + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
