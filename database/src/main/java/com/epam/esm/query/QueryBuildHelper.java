package com.epam.esm.query;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class QueryBuildHelper {

    public String buildUpdateColumnsQuery(Set<String> columns) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean isFirstElement = true;
        for (String column : columns) {
            if (!isFirstElement) {
                queryBuilder.append(", ");
            } else {
                isFirstElement = false;
            }
            queryBuilder.append(column);
            queryBuilder.append("=?");
        }
        return queryBuilder.toString();
    }

}
