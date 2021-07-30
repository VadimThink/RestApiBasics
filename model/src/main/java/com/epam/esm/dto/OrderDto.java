package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class OrderDto {

    private long id;

    private User user;

    private GiftCertificate giftCertificate;

    private ZonedDateTime orderDate;

    private BigDecimal cost;

    public OrderDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        if (id != orderDto.id) return false;
        if (user != null ? !user.equals(orderDto.user) : orderDto.user != null) return false;
        if (giftCertificate != null ? !giftCertificate.equals(orderDto.giftCertificate) : orderDto.giftCertificate != null)
            return false;
        if (orderDate != null ? !orderDate.equals(orderDto.orderDate) : orderDto.orderDate != null) return false;
        return cost != null ? cost.equals(orderDto.cost) : orderDto.cost == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", user=" + user +
                ", giftCertificate=" + giftCertificate +
                ", orderDate=" + orderDate +
                ", cost=" + cost +
                '}';
    }
}
