package com.barunsw.ojt.jyb.day10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;

public class FileAddressBookImpl implements RmiAddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
	private static final String CSV_FILE_PATH = "src/com/barunsw/ojt/jyb/day10/address_book.csv";

	private List<AddressVo> addressList = new ArrayList<>();
	private int currentSeq = 1;

	public FileAddressBookImpl() {
		loadFromCsv();
	}

	/**
	 * CSV 파일에서 주소 데이터를 읽어옵니다.
	 */
	private void loadFromCsv() {
		try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue;

				String[] fields = line.split(",");
				AddressVo address = new AddressVo();
				address.setSeq(Integer.parseInt(fields[0].trim()));
				address.setName(fields[1].trim());
				address.setAge(Integer.parseInt(fields[2].trim()));
				address.setGender(Gender.toGender(fields[3].trim()));
				address.setAddress(fields[4].trim());
				address.setPhone(fields[5].trim());

				addressList.add(address);

				// 최대 SEQ 값 업데이트
				if (address.getSeq() >= currentSeq) {
					currentSeq = address.getSeq() + 1;
				}
			}
			LOGGER.debug("CSV 데이터 로드 완료: {} 개의 데이터", addressList.size());
		}
		catch (FileNotFoundException e) {
			LOGGER.warn("지정된 경로에 CSV 파일을 찾을 수 없습니다. 새로운 파일을 생성합니다. 경로: {}", CSV_FILE_PATH);
		}
		catch (IOException e) {
			LOGGER.error("CSV 파일을 읽는 중에 IO 오류가 발생했습니다. 파일 경로: {}. 오류 메시지: {}", CSV_FILE_PATH, e.getMessage(), e);
		}
		catch (Exception e) {
			LOGGER.error("CSV 로드 중 예상치 못한 오류가 발생했습니다. 오류 메시지: {}", e.getMessage(), e);
		}
	}

	/**
	 * 주소 데이터를 CSV 파일에 저장합니다.
	 */
	private void saveToCsv() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
			for (AddressVo address : addressList) {
				writer.write(String.format("%d,%s,%d,%s,%s,%s%n", address.getSeq(), address.getName(), address.getAge(),
						address.getGender() != null ? address.getGender().toString() : "", address.getAddress(),
						address.getPhone()));
			}
			LOGGER.debug("CSV 데이터 저장 완료");
		}
		catch (IOException e) {
			LOGGER.error("CSV 파일에 데이터를 저장하는 중 오류가 발생했습니다. 파일 경로: {}. 오류 메시지: {}", CSV_FILE_PATH, e.getMessage(), e);
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
		LOGGER.debug("주소 데이터 삽입 성공: {}", addressVo);
		return 1;
	}

	@Override
	public int updateAddress(AddressVo addressVo) {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getSeq() == addressVo.getSeq()) {
				addressList.set(i, addressVo);
				saveToCsv();
				LOGGER.debug("주소 데이터 업데이트 성공: {}", addressVo);
				return 1;
			}
		}
		LOGGER.warn("업데이트 실패: 주소 데이터가 존재하지 않습니다. SEQ 값: {}", addressVo.getSeq());
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getSeq() == addressVo.getSeq()) {
				addressList.remove(i);
				saveToCsv();
				LOGGER.debug("주소 데이터 삭제 성공: {}", addressVo);
				return 1;
			}
		}
		LOGGER.warn("삭제 실패: 주소 데이터가 존재하지 않습니다. SEQ 값: {}", addressVo.getSeq());
		return 0;
	}
}
