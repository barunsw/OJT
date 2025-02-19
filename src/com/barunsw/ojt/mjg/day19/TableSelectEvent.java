package com.barunsw.ojt.mjg.day19;

public class TableSelectEvent {
    private String tableName;
    
    public TableSelectEvent(String tableName) {
        this.tableName = tableName;
    }
    
    public String getTableName() {
        return tableName;
    }
}
