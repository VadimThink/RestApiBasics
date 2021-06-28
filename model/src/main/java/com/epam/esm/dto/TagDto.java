package com.epam.esm.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TagDto {

    private long id;
    @Size(min = 1, max = 50, message = "Name length should be >= 1, and <= 50")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagDto tagDto = (TagDto) o;

        if (id != tagDto.id) return false;
        return name != null ? name.equals(tagDto.name) : tagDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "idDto=" + id +
                ", nameDto='" + name + '\'' +
                '}';
    }
}
