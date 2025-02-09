package day9;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAddressBookImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
    private static String FILE_PATH;
    private DbAddressImpl dbAddressImpl;
    private static Properties addressBookProperties = new Properties();

    public FileAddressBookImpl() {
        dbAddressImpl = new DbAddressImpl();
        loadProperties();
    }

    private void loadProperties() {
        try (Reader reader = Resources.getResourceAsReader("AddressBookApp.properties")) {
            LOGGER.info("프로퍼티 파일 로딩 시작");
            addressBookProperties.load(reader);
            FILE_PATH = addressBookProperties.getProperty("addressFilePath", "addressBook.csv"); // 기본값 설정
            LOGGER.info("주소록 파일 경로: " + FILE_PATH);
        } catch (IOException e) {
            LOGGER.error("설정 파일 로드 중 오류 발생", e);
            FILE_PATH = "addressBook.csv"; // 오류 발생 시 기본 경로 사용
        }
    }

    public void saveAddressListToFile() {
        List<AddressVo> addressList = dbAddressImpl.selectAddressList(new AddressVo()); // 주소록 데이터 가져오기

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (AddressVo address : addressList) {
                String line = String.format("%d,%s,%d,%s,%s", 
                        address.getSeq(), address.getName(), address.getAge(), address.getPhone(), address.getAddress());
                writer.write(line);
                writer.newLine();
            }
            LOGGER.info("주소록이 파일에 성공적으로 저장되었습니다: " + FILE_PATH);
        } catch (IOException e) {
            LOGGER.error("파일 저장 중 오류 발생", e);
        }
    }
}
