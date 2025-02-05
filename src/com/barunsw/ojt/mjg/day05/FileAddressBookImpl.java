package com.barunsw.ojt.mjg.day05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.mjg.day05.AddressBookInterface;
import com.barunsw.ojt.mjg.day05.AddressVo;
import com.barunsw.ojt.constants.Gender;

public class FileAddressBookImpl implements AddressBookInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
    private static final String CSV_FILE_PATH = "src/com/barunsw/ojt/mjg/day05/address_book.csv";

    private List<AddressVo> addressList = new ArrayList<>();
    private int currentSeq = 1;  // SEQ 초기값

    public FileAddressBookImpl() {
        loadFromCsv();
    }

    private void loadFromCsv() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                AddressVo address = new AddressVo(line, currentSeq, null, line);
                address.setSeq(Integer.parseInt(fields[0].trim()));
                address.setName(fields[1].trim());
                address.setAge(Integer.parseInt(fields[2].trim()));
                address.setGender(Gender.toGender(fields[3].trim()));
                address.setAddress(fields[4].trim());
                addressList.add(address);

                // 최대 SEQ 값 갱신
                if (address.getSeq() >= currentSeq) {
                    currentSeq = address.getSeq() + 1;
                }
            }
            LOGGER.debug("CSV 데이터 로드 완료: {} 개의 데이터", addressList.size());
        } catch (FileNotFoundException e) {
            LOGGER.warn("CSV 파일이 존재하지 않습니다. 새로 생성합니다.");
        } catch (Exception e) {
            LOGGER.error("CSV 로드 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    private void saveToCsv() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (AddressVo address : addressList) {
                writer.write(String.format("%d,%s,%d,%s,%s%n",
                        address.getSeq(),
                        address.getName(),
                        address.getAge(),
                        address.getGender() != null ? address.getGender().toString() : "",
                        address.getAddress()));
            }
            LOGGER.debug("CSV 데이터 저장 완료");
        } catch (IOException e) {
            LOGGER.error("CSV 저장 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    @Override
    public List<AddressVo> selectAddressList(AddressVo addressVo) {
        return addressList;
    }

    @Override
    public int insertAddress(AddressVo addressVo) {
        // 자동으로 SEQ 값 설정
        addressVo.setSeq(currentSeq++);
        addressList.add(addressVo);
        saveToCsv();
        LOGGER.debug("데이터 삽입: {}", addressVo);
        return 1;
    }

    @Override
    public int updateAddress(AddressVo addressVo) {
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).getSeq() == addressVo.getSeq()) {
                addressList.set(i, addressVo);
                saveToCsv();
                LOGGER.debug("데이터 업데이트: {}", addressVo);
                return 1;
            }
        }
        LOGGER.warn("업데이트할 데이터를 찾을 수 없습니다: {}", addressVo);
        return 0;
    }

    @Override
    public int deleteAddress(AddressVo addressVo) {
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).getSeq() == addressVo.getSeq()) {
                addressList.remove(i);
                saveToCsv();
                LOGGER.debug("데이터 삭제: {}", addressVo);
                return 1;
            }
        }
        LOGGER.warn("삭제할 데이터를 찾을 수 없습니다: {}", addressVo);
        return 0;
    }
}

