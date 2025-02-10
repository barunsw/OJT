package com.barunsw.ojt.mjg.day10;

import java.io.IOException;
import java.io.Reader;
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

import com.barunsw.ojt.mjg.constants.Gender;
import com.barunsw.ojt.mjg.day10.AddressBookInterface;
import com.barunsw.ojt.mjg.day10.AddressVo;

public class JdbcAddressBookImpl implements AddressBookInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcAddressBookImpl.class);
    private static final Properties jdbcProperties = new Properties();
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public JdbcAddressBookImpl() {
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

    @Override
    public List<AddressVo> selectAddressList(AddressVo addressVo) {
        List<AddressVo> addressList = new ArrayList<>();
        String SQL = "SELECT SEQ, NAME, AGE, GENDER, ADDRESS, PHONE FROM TB_ADDRESSBOOK";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement psmt = conn.prepareStatement(SQL);
             ResultSet resultSet = psmt.executeQuery()) {

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
            LOGGER.debug("데이터 조회 성공: {} 개의 데이터", addressList.size());
        } catch (Exception e) {
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
            psmt.setString(3, addressVo.getGender().getEng());
            psmt.setString(4, addressVo.getAddress());
            psmt.setString(5, addressVo.getPhone());            
            int result = psmt.executeUpdate();
            LOGGER.debug("데이터 삽입 성공: {}", addressVo);
            return result;

        } catch (Exception e) {
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
            psmt.setString(3, addressVo.getGender().getEng());
            psmt.setString(4, addressVo.getAddress());
            psmt.setString(5, addressVo.getPhone());
            psmt.setInt(6, addressVo.getSeq());  // seq 기준으로 업데이트
            int result = psmt.executeUpdate();
            LOGGER.debug("데이터 업데이트 성공: {}", addressVo);
            return result;

        } catch (Exception e) {
            LOGGER.error("Error during update operation: {}", e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public int deleteAddress(AddressVo addressVo) {
        String SQL = "DELETE FROM TB_ADDRESSBOOK WHERE SEQ = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement psmt = conn.prepareStatement(SQL)) {

            psmt.setInt(1, addressVo.getSeq());  // seq 기준으로 삭제
            int result = psmt.executeUpdate();
            LOGGER.debug("데이터 삭제 성공: {}", addressVo);
            return result;

        } catch (Exception e) {
            LOGGER.error("Error during delete operation: {}", e.getMessage(), e);
            return 0;
        }
    }
}

