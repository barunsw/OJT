package com.barunsw.ojt.mjg.day19;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBQueryManager {
	private static final Logger LOGGER = LogManager.getLogger(DBQueryManager.class);
	
    /**
     * 연결된 DB에서 "SHOW TABLES;" 쿼리를 실행하여 테이블 이름들을 Vector로 반환한다.
     * @param connection 연결된 DB Connection
     * @return 테이블 이름들을 담은 Vector
     * @throws SQLException
     */
	// DB 연결 시 루트 노드에 DB에 존재하는 테이블 반환 후, 
	// 반환된 테이블 이름들을 DB 노드의 자식으로 추가
    public static Vector<String> getTableNames(Connection connection) throws SQLException {
        Vector<String> tableNames = new Vector<>();
        String sql = "SHOW TABLES;";
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet resultSet = psmt.executeQuery();
        
        while (resultSet.next()) {
            tableNames.add(resultSet.getString(1));
        }
        
        resultSet.close();
        psmt.close();
        return tableNames;
    }

    
    // 선택한 테이블의 컬럼명들을 조회하여 Vector로 반환한다.
    // 1행만 조회 -> getMetaData() -> 컬럼 정보를 가져온다. 
    public static Vector<String> getColumnNames(Connection connection, String tableName) throws SQLException {
    	Vector<String> columnNames = new Vector<>();
//        String sql = "SHOW COLUMNS FROM " + tableName; // 컬럼명 조회
        String sql = "SHOW COLUMNS FROM `" + tableName + "`"; // 백틱(`) 추가

        LOGGER.debug("Executing query: {}", sql);
        

       
        try (PreparedStatement psmt = connection.prepareStatement(sql);
             ResultSet resultSet = psmt.executeQuery();) {
               while (resultSet.next()) {
                   columnNames.add(resultSet.getString(1)); // 첫 번째 컬럼이 필드명
               }
           }

           LOGGER.debug("컬럼 목록 가져오기 완료: {}", columnNames);
           return columnNames;
    }
   

    
    // 선택한 테이블의 모든 데이터를 조회하여 Vector<Vector<Object>>로 반환한다.
    public static Vector<Vector<Object>> getTableData(Connection connection, String tableName) throws SQLException {
        Vector<Vector<Object>> tableData = new Vector<>();
//        String sql = "SELECT * FROM " + tableName;
        String sql = "SELECT * FROM `" + tableName + "`"; // 백틱(`) 추가

        
        LOGGER.debug("Executing query: {}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql);
        	 ResultSet resultSet = psmt.executeQuery();) {
            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                tableData.add(row);
            }
        }

        LOGGER.debug("테이블 데이터 가져오기 완료: {} 개의 행", tableData.size());
        return tableData;
    }
    
}
