package day6;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        JdbcAddressBookImpl addressBook = new JdbcAddressBookImpl();

        // 새 주소 정보 설정
        AddressVO newAddress = new AddressVO();
        newAddress.setName("홍길동");
        newAddress.setAge(30);
        newAddress.setPhone("010-1234-5678");
        newAddress.setAddress("서울시 강남구");

        try {
            // 주소 추가
            addressBook.insertAddress(newAddress);
            LOGGER.info("주소가 성공적으로 추가되었습니다. 생성된 seq: {}", newAddress.getSeq());
        } catch (Exception e) {
            LOGGER.error("주소 추가 중 오류 발생", e);
        }

        // 주소 목록 조회
        try {
            List<AddressVO> addressList = addressBook.selectAddressList(new AddressVO());
            LOGGER.info("주소 목록 조회 성공: {}", addressList);
        } catch (Exception e) {
            LOGGER.error("주소 목록 조회 중 오류 발생", e);
        }
    }
}
