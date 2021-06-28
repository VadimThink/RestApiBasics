package com.epam.esm.dao;

public final class SqlRequest {
    public static final String SQL_CREATE_CERTIFICATE = "INSERT INTO gift_certificate" +
            "(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_CREATE_CERTIFICATE_TAG_REFERENCE = "INSERT INTO " +
            "certificate_tags(certificate_id, tag_id) VALUES (?, ?)";
    public static final String SQL_GET_TAG_IDS_BY_CERTIFICATE_ID = "SELECT * FROM " +
            "certificate_tags WHERE certificate_id=?";
    public static final String SQL_GET_CERTIFICATE_IDS_BY_TAG_ID = "SELECT * FROM " +
            "certificate_tags WHERE tag_id=?";

    private SqlRequest(){

    }
}
