package com.barunsw.ojt.mjg.day19;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBQueryManager {
	private static final Logger LOGGER = LogManager.getLogger(DBQueryManager.class);
	
	/*
	 * ExplorerPanel 
	 * 1. 연결된 DB의 테이블들 DB 노드에 추가 -> "SHOW TABLES;"
	 * 2. 트리에서 선택한 테이블 컬럼명 조회   -> "SHOW COLUMNS FROM " + tableName;
	 * 3. 트리에서 선택한 테이블 데이터 조회   -> "SHOW COLUMNS FROM " + tableName;
	 */

	/**
	 * 연결된 DB에서 "SHOW TABLES;" 쿼리를 실행하여 테이블 이름들을 Vector로 반환한다.
	 * 
	 * @param connection 연결된 DB Connection
	 * @return 테이블 이름들을 담은 Vector
	 * @throws SQLException
	 */
	// DB 연결 시 루트 노드에 DB에 존재하는 테이블 반환 후,
	// 반환된 테이블 이름들을 DB 노드의 자식으로 추가
	public static Vector<String> getTableNames(Connection connection) throws SQLException {
		Vector<String> tableNames = new Vector<>();
		String sql = "SHOW TABLES;";
		
		try(PreparedStatement psmt = connection.prepareStatement(sql);
			ResultSet resultSet = psmt.executeQuery();) {
			while (resultSet.next()) {
				tableNames.add(resultSet.getString(1));
			}
		}
		catch (SQLException sqle) {
			LOGGER.error(sqle.getMessage());
		}

		return tableNames;
	}

	// 선택한 테이블의 컬럼명들을 조회하여 Vector로 반환한다.
	// 1행만 조회 -> getMetaData() -> 컬럼 정보를 가져온다.
	public static Vector<String> getColumnNames(Connection connection, String tableName) throws SQLException {
		Vector<String> columnNames = new Vector<>();
		String sql = "SHOW COLUMNS FROM " + tableName; // 컬럼명 조회

		LOGGER.debug("Executing query: {}", sql);

		try (PreparedStatement psmt = connection.prepareStatement(sql); ResultSet resultSet = psmt.executeQuery();) {
			while (resultSet.next()) {
				columnNames.add(resultSet.getString(1)); // 첫 번째 컬럼이 필드명
			}
		}
		catch (SQLException sqle) {
			LOGGER.error(sqle.getMessage());
		}

		LOGGER.debug("컬럼 목록 가져오기 완료: {}", columnNames);
		return columnNames;
	}

	// 선택한 테이블의 모든 데이터를 조회하여 Vector<Vector<Object>>로 반환한다.
	public static Vector<Vector<Object>> getTableData(Connection connection, String tableName) throws SQLException {
		Vector<Vector<Object>> tableData = new Vector<>();
		String sql = "SELECT * FROM " + tableName;

		LOGGER.debug("Executing query: {}", sql);

		try (PreparedStatement psmt = connection.prepareStatement(sql); ResultSet resultSet = psmt.executeQuery();) {
			int columnCount = resultSet.getMetaData().getColumnCount();

			while (resultSet.next()) {
				Vector<Object> row = new Vector<>();
				for (int i = 1; i <= columnCount; i++) {
					row.add(resultSet.getObject(i));
				}
				tableData.add(row);
			}
		}
		catch (SQLException sqle) {
			LOGGER.error(sqle.getMessage());
		}

		LOGGER.debug("테이블 데이터 가져오기 완료: {} 개의 행", tableData.size());
		return tableData;
	}

    /**
     * InputPanel
     * 1. 사용자 쿼리를 실행하여 결과를 QueryResultEvent로 반환합니다.
     * 2. SELECT: 컬럼 및 데이터 벡터를 구성하여 반환
     * 3. 명령별 메시지 반환
     *    - SELECT: N개의 row를 출력했습니다.
     *    - INSERT: N개의 row를 추가했습니다.
     *    - UPDATE: N개의 row를 수정했습니다.
     *    - DELETE: N개의 row를 삭제했습니다.
     *    - CREATE: 테이블이 생성되었습니다.
     */
    public static QueryResultEvent executeQuery(Connection connection, String query) throws SQLException {
        query = query.trim();
        String queryUpper = query.toUpperCase();
        
        if (queryUpper.startsWith("SELECT")) {
            // SELECT 쿼리 처리
            Vector<String> columnNames = new Vector<>();
            Vector<Vector<Object>> tableData = new Vector<>();
            
            LOGGER.debug("Executing query: {}", query);
            try (PreparedStatement psmt = connection.prepareStatement(query);
                 ResultSet resultSet = psmt.executeQuery()) {
                 
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(metaData.getColumnLabel(i));
                }
                
                while (resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getObject(i));
                    }
                    tableData.add(row);
                }
            }
            LOGGER.debug("쿼리 실행 완료 - 컬럼: {}, 행 수: {} (SELECT: {}개의 row를 출력했습니다.)", 
                         columnNames, tableData.size(), tableData.size());
            return new QueryResultEvent(columnNames, tableData);
        }
        else {
            // SELECT가 아닌 쿼리 처리 (INSERT, UPDATE, DELETE, CREATE 등)
            LOGGER.debug("Executing update query: {}", query);
            int updateCount = 0;
            try (PreparedStatement psmt = connection.prepareStatement(query)) {
                updateCount = psmt.executeUpdate();
            }
            LOGGER.debug("Update query executed, affected rows: {}", updateCount);
            
            String message = "";
            if (queryUpper.startsWith("INSERT")) {
                message = updateCount + "개의 row를 추가했습니다.";
            }
            else if (queryUpper.startsWith("UPDATE")) {
                message = updateCount + "개의 row를 수정했습니다.";
            }
            else if (queryUpper.startsWith("DELETE")) {
                message = updateCount + "개의 row를 삭제했습니다.";
            }
            else if (queryUpper.startsWith("CREATE")) {
                message = "테이블이 생성되었습니다.";
            }
            else {
                message = "영향받은 행 수: " + updateCount;
            }
            
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Message");
            Vector<Vector<Object>> tableData = new Vector<>();
            Vector<Object> row = new Vector<>();
            row.add(message);
            tableData.add(row);
            return new QueryResultEvent(columnNames, tableData);
        }
    }
}
