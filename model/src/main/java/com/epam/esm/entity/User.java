package com.epam.esm.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "spent_money")
    private BigDecimal spentMoney;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    @PrePersist
    protected void onCreate() {
        spentMoney = BigDecimal.ZERO;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (spentMoney != null ? !spentMoney.equals(user.spentMoney) : user.spentMoney != null) return false;
        return orders != null ? orders.equals(user.orders) : user.orders == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (spentMoney != null ? spentMoney.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", spentMoney=" + spentMoney +
                ", orders=" + orders +
                ", id=" + getId() +
                '}';
    }
}
