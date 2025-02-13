package com.barunsw.ojt.mjg.common;

import java.io.IOException;
import java.io.Reader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.constants.SearchType;
import com.barunsw.ojt.vo.AddressVo;

public class JdbcAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcAddressBookImpl.class);
    private static final Properties jdbcProperties = new Properties();
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public JdbcAddressBookImpl() throws RemoteException {
        try (Reader reader = Resources.getResourceAsReader("Jdbc.properties")) {
            jdbcProperties.load(reader);
            dbUrl = jdbcProperties.getProperty("url");
            dbUser = jdbcProperties.getProperty("username");
            dbPassword = jdbcProperties.getProperty("password");
        }
        catch (IOException ioe) {
            throw new RuntimeException("JDBC 설정 로드 실패", ioe);
        }
        LOGGER.debug("JDBC 설정 로드 완료");
    }


    // 초성(ㄱ, ㄴ, ㄷ, ...)에 대응하는 정규식을 반환하는 헬퍼 메서드
    private String getRegexForInitial(String initial) {
        switch (initial) {
            case "ㄱ": return "^[가-깋]";
            case "ㄴ": return "^[나-닣]";
            case "ㄷ": return "^[다-딯]";
            case "ㄹ": return "^[라-맇]";
            case "ㅁ": return "^[마-밓]";
            case "ㅂ": return "^[바-빟]";
            case "ㅅ": return "^[사-싷]";
            case "ㅇ": return "^[아-잏]";
            case "ㅈ": return "^[자-짛]";
            case "ㅊ": return "^[차-칳]";
            case "ㅋ": return "^[카-킿]";
            case "ㅌ": return "^[타-팋]";
            case "ㅍ": return "^[파-핗]";
            case "ㅎ": return "^[하-힣]";
            default:
                return null;
        }
    }

    @Override
    public List<AddressVo> selectAddressList(AddressVo addressVo) {
        List<AddressVo> addressList = new ArrayList<>();

        // 기본 SELECT 문 (전체 조회)
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SEQ, NAME, AGE, GENDER, ADDRESS, PHONE");
        sb.append(" FROM TB_ADDRESSBOOK");
        sb.append(" WHERE 1=1");

        // 초성 검색을 위한 변수
        String regex = null;

        // UI에서 초성 필터가 설정된 경우(searchType=LETTER, searchWord=ㄱ 등)
        if (addressVo != null 
            && addressVo.getSearchType() == SearchType.LETTER 
            && addressVo.getSearchWord() != null) {
            
            // 초성에 맞는 정규식을 구함
            regex = getRegexForInitial(addressVo.getSearchWord());
            if (regex != null) {
                // WHERE 조건에 "AND NAME REGEXP ?" 추가
                sb.append(" AND NAME REGEXP ?");
            }
        }

        LOGGER.debug("SQL={}", sb);

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement psmt = conn.prepareStatement(sb.toString())) 
        {
            int paramIndex = 1;

            // 초성 정규식이 있으면 바인딩
            if (regex != null) {
                psmt.setString(paramIndex++, regex);
            }

            // 실행
            try (ResultSet resultSet = psmt.executeQuery()) {
                while (resultSet.next()) {
                    AddressVo address = new AddressVo();
                    address.setSeq(resultSet.getInt("SEQ"));
                    address.setName(resultSet.getString("NAME"));
                    address.setAge(resultSet.getInt("AGE"));
                    address.setGender(Gender.toGender(resultSet.getString("GENDER")));
                    address.setAddress(resultSet.getString("ADDRESS"));
                    address.setPhone(resultSet.getString("PHONE"));
                    addressList.add(address);
                }
            }
            LOGGER.debug("데이터 조회 성공: {} 개의 데이터", addressList.size());
        } 
        catch (Exception e) {
            LOGGER.error("Error during select operation: {}", e.getMessage(), e);
        }
        return addressList;
    }

    @Override
    public int insertAddress(AddressVo addressVo) {
        String SQL = "INSERT INTO TB_ADDRESSBOOK (NAME, AGE, GENDER, ADDRESS, PHONE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement psmt = conn.prepareStatement(SQL)) {

            psmt.setString(1, addressVo.getName());
            psmt.setInt(2, addressVo.getAge());
            psmt.setString(3, addressVo.getGender().name());
            psmt.setString(4, addressVo.getAddress());
            psmt.setString(5, addressVo.getPhone());
            int result = psmt.executeUpdate();
            LOGGER.debug("데이터 삽입 성공: {}", addressVo);
            return result;

        } 
        catch (Exception e) {
            LOGGER.error("Error during insert operation: {}", e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public int updateAddress(AddressVo addressVo) {
        String SQL = "UPDATE TB_ADDRESSBOOK SET NAME = ?, AGE = ?, GENDER = ?, ADDRESS = ?, PHONE = ? WHERE SEQ = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement psmt = conn.prepareStatement(SQL)) {

            psmt.setString(1, addressVo.getName());
            psmt.setInt(2, addressVo.getAge());
            psmt.setString(3, addressVo.getGender().name());
            psmt.setString(4, addressVo.getAddress());
            psmt.setString(5, addressVo.getPhone());
            psmt.setInt(6, addressVo.getSeq());
            int result = psmt.executeUpdate();
            LOGGER.debug("데이터 업데이트 성공: {}", addressVo);
            return result;

        } 
        catch (Exception e) {
            LOGGER.error("Error during update operation: {}", e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public int deleteAddress(AddressVo addressVo) {
        String SQL = "DELETE FROM TB_ADDRESSBOOK WHERE SEQ = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement psmt = conn.prepareStatement(SQL)) {

            psmt.setInt(1, addressVo.getSeq());
            int result = psmt.executeUpdate();
            LOGGER.debug("데이터 삭제 성공: {}", addressVo);
            return result;

        } 
        catch (Exception e) {
            LOGGER.error("Error during delete operation: {}", e.getMessage(), e);
            return 0;
        }
    }
}

