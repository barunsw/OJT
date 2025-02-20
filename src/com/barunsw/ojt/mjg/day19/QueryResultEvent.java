package com.barunsw.ojt.mjg.day19;

import java.util.Vector;

public class QueryResultEvent {
    private Vector<String> columnNames;
    private Vector<Vector<Object>> tableData;

    public QueryResultEvent(Vector<String> columnNames, Vector<Vector<Object>> tableData) {
        this.columnNames = columnNames;
        this.tableData = tableData;
    }

    public Vector<String> getColumnNames() {
        return columnNames;
    }

    public Vector<Vector<Object>> getTableData() {
        return tableData;
    }
}
