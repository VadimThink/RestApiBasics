package com.epam.esm.query;

import java.util.List;

public class SortingParameters {
    //todo validation??
    private final List<String> sortColumns;
    private final List<String> orderTypes;

    public SortingParameters(List<String> sortColumns, List<String> orderTypes) {
        this.sortColumns = sortColumns;
        this.orderTypes = orderTypes;
    }

    public List<String> getSortColumns() {
        return sortColumns;
    }

    public List<String> getOrderTypes() {
        return orderTypes;
    }
}
