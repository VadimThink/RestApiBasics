package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;
    @Size(min = 1, max = 50, message = "Name length should be >= 1, and <= 50")
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @Size(min = 1, max = 100, message = "Description length should be >= 1, and <= 100")
    private String description;
    @DecimalMin(value = "1", message = "Price should be >= 1")
    private BigDecimal price;
    @Min(value = 1, message = "Duration should be >= 1")
    private int duration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime lastUpdateDate;
    private List<TagDto> tagList;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(long id, @Size(min = 1, max = 50,
            message = "Name length should be >= 1, and <= 50") @NotEmpty(message = "Name should not be empty") String name,
                              @Size(min = 1, max = 100, message = "Description length should be >= 1, and <= 100")
                                      String description, @Min(value = 1, message = "Price should be >= 1")
                                      BigDecimal price, @Min(value = 1, message = "Duration should be >= 1")
                                      int duration, ZonedDateTime createDate, ZonedDateTime lastUpdateDate, List<TagDto> tagList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagList = tagList;
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

    public void setName(@Size(min = 1, max = 50) String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 1, max = 100) String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@Min(1) BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(@Min(1) int duration) {
        this.duration = duration;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<TagDto> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagDto> tagList) {
        this.tagList = tagList;
    }

    public void addTag(TagDto tagDto) {
        if (tagList == null) {
            tagList = new ArrayList<>();
        }
        tagList.add(tagDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificateDto that = (GiftCertificateDto) o;

        if (id != that.id) return false;
        if (duration != that.duration) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        return tagList != null ? tagList.equals(that.tagList) : that.tagList == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", duration=" + duration +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", tagList=" + tagList +
                '}';
    }
}
