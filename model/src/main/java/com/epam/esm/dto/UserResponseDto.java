package com.epam.esm.dto;

import com.epam.esm.constant.Status;
import com.epam.esm.entity.Role;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    private long id;

    @NotEmpty
    @Size(min = 1, max = 50, message = "Name length should be >= 1, and <= 50")
    private String name;

    private BigDecimal spentMoney;

    @NotEmpty
    @Size(min = 1, max = 20)
    private String login;

    @NotEmpty
    @Email
    private String email;

    private Status status;

    private Set<Role> roles = new HashSet<>();

    public UserResponseDto() {
    }

    public UserResponseDto(long id, String name, BigDecimal spentMoney) {
        this.id = id;
        this.name = name;
        this.spentMoney = spentMoney;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        this.spentMoney = spentMoney;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserResponseDto that = (UserResponseDto) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (spentMoney != null ? !spentMoney.equals(that.spentMoney) : that.spentMoney != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (status != that.status) return false;
        return roles != null ? roles.equals(that.roles) : that.roles == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (spentMoney != null ? spentMoney.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spentMoney=" + spentMoney +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", roles=" + roles +
                '}';
    }
}
