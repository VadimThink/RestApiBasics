package com.epam.esm.dto;

import java.math.BigDecimal;

public class UserDto {

    private String name;

    private BigDecimal spentMoney;

    public UserDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (name != null ? !name.equals(userDto.name) : userDto.name != null) return false;
        return spentMoney != null ? spentMoney.equals(userDto.spentMoney) : userDto.spentMoney == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (spentMoney != null ? spentMoney.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", spentMoney=" + spentMoney +
                '}';
    }
}
