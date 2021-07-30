package com.epam.esm.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateOrderDto {

    @NotNull
    @Min(1)
    long userId;

    @NotNull
    @Min(1)
    long certificateId;

    public CreateOrderDto() {
    }

    public CreateOrderDto(long userId, long certificateId) {
        this.userId = userId;
        this.certificateId = certificateId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateOrderDto that = (CreateOrderDto) o;

        if (userId != that.userId) return false;
        return certificateId == that.certificateId;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (certificateId ^ (certificateId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CreateOrderDto{" +
                "userId=" + userId +
                ", certificateId=" + certificateId +
                '}';
    }
}
