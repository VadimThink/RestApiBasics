package com.epam.esm.query;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.esm.dao.constant.ColumnNames.*;

public class SortingParametersValidator {
    private static final List<String> availableColumns = new ArrayList<>(
            Arrays.asList(ID_COLUMN_LABEL, NAME_COLUMN_LABEL, DESCRIPTION_COLUMN_LABEL,
                    PRICE_COLUMN_LABEL, DURATION_COLUMN_LABEL, CREATE_DATE_COLUMN_LABEL, LAST_UPDATE_DATE_COLUMN_LABEL));
    private final static List<String> availableOrderTypes = new ArrayList<>(Arrays.asList("ASC", "DESC"));

    public static void validateParams(SortingParameters sortingParameters){
        for (String columnName: sortingParameters.getSortColumns()) {
            if(!availableColumns.contains(columnName)){
                throw new ValidationException("Not available column name: " + columnName);
            }
        }
        for (String orderType: sortingParameters.getOrderTypes()) {
            if(!availableOrderTypes.contains(orderType)){
                throw new ValidationException("Not available order type: " + orderType);
            }
        }
    }
}
