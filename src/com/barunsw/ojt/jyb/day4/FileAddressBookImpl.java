package day4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileAddressBookImpl implements AddressBookInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileAddressBookImpl.class);
	public static Properties addressBook_Properties = new Properties();
	private List<AddressVO> addressList = new ArrayList<>();
	private String addressFilePath;

	public FileAddressBookImpl() throws Exception {
		super();
		Reader reader = Resources.getResourceAsReader("AddressBookApp.properties");
		addressBook_Properties.load(reader);

		Iterator<Object> keySet = addressBook_Properties.keySet().iterator();
		while (keySet.hasNext()) {
			Object key = keySet.next();
			Object value = addressBook_Properties.get(key);
			LOGGER.debug(String.format("%s = %s", key, value));
		}
		LOGGER.debug("==============================");
		String pathName = FileAddressBookImpl.addressBook_Properties.getProperty("addressFilePath");
		addressFilePath = pathName;
		loadFile();
	}

	private void loadFile() throws Exception {
		try (BufferedReader reader = new BufferedReader(new FileReader(addressFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				if (fields.length == 5) {
					AddressVO address = new AddressVO();
					address.setSeq(Integer.parseInt(fields[0]));
					address.setName(fields[1]);
					address.setAge(Integer.parseInt(fields[2]));
					address.setPhone(fields[3]);
					address.setAddress(fields[4]);

					addressList.add(address);
					LOGGER.debug(address.toString());
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void saveFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(addressFilePath))) {
			for (AddressVO address : addressList) {
				String line = String.format("%s,%s,%s,%s,%s", address.getSeq(), address.getName(), address.getAge(),
						address.getPhone(), address.getAddress());
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public List<AddressVO> selectAddressList(AddressVO addressVO) {
		return addressList;
	}

	@Override
	public void insertAddress(AddressVO addressVO) throws Exception {
		int newSeq = addressList.isEmpty() ? 1 : addressList.get(addressList.size() - 1).getSeq() + 1;
		addressVO.setSeq(newSeq);
		addressList.add(addressVO);
		saveFile();
	}

	@Override
	public void updateAddress(AddressVO addressVO) throws Exception {
		for (int i = 0; i < addressList.size(); i++) {
			if (addressList.get(i).getSeq() == addressVO.getSeq()) {
				addressList.set(i, addressVO);
				break;
			}
		}
		saveFile();
	}

	@Override
	public void deleteAddress(AddressVO addressVO) throws Exception {
		addressList.removeIf(address -> address.getSeq() == addressVO.getSeq());
		saveFile();
	}
}
