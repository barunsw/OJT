package com.barunsw.ojt.jyb.day9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;

public class ObjectAddressBookImpl implements AddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectAddressBookImpl.class);
	private static final String FILE_PATH = "src/com/barunsw/ojt/jyb/day9/address_book.dat";

	private List<AddressVo> addressList = new ArrayList<>();

	public ObjectAddressBookImpl() {
		loadFromFile();
	}

	private void loadFromFile() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
			addressList = (List<AddressVo>) ois.readObject();
			LOGGER.debug("데이터 로드 완료: {} 개의 데이터", addressList.size());
		}
		catch (FileNotFoundException e) {
			LOGGER.warn("직렬화된 데이터 파일이 존재하지 않습니다.");
		}
		catch (Exception e) {
			LOGGER.error("데이터 로드 중 오류 발생: {}", e.getMessage(), e);
		}
	}

	private void saveToFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
			oos.writeObject(addressList);
			LOGGER.debug("데이터 저장 완료");
		}
		catch (IOException e) {
			LOGGER.error("데이터 저장 중 오류 발생: {}", e.getMessage(), e);
		}
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		return addressList; // 새로운 리스트 생성 없이 기존 리스트 반환
	}

	@Override
	public int insertAddress(AddressVo addressVo) {
		addressList.add(addressVo);
		saveToFile();
		LOGGER.debug("데이터 삽입: {}", addressVo);
		return 1;
	}

	@Override
	public int updateAddress(AddressVo addressVo) {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getSeq() == addressVo.getSeq()) {
				addressList.set(i, addressVo);
				saveToFile();
				LOGGER.debug("데이터 업데이트: {}", addressVo);
				return 1;
			}
		}
		LOGGER.warn("업데이트할 데이터가 없습니다 (seq: {}): {}", addressVo.getSeq(), addressVo);
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getSeq() == addressVo.getSeq()) {
				addressList.remove(i);
				saveToFile();
				LOGGER.debug("데이터 삭제: {}", addressVo);
				return 1;
			}
		}
		LOGGER.warn("삭제할 데이터가 없습니다 (seq: {}): {}", addressVo.getSeq(), addressVo);
		return 0;
	}
}
