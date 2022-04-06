package com.barunsw.ojt.cjs.day07;

import java.util.ArrayList;
import java.util.List;

public class MemAddressBookImpl implements AddressBookInterface {
	private List<AddressVo> addressBookList = new ArrayList<>();

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		// TODO Auto-generated method stub
		return addressBookList;
	}

	@Override
	public int insertAddress(AddressVo addressVo) throws Exception {
		// TODO Auto-generated method stub
		addressBookList.add(addressVo);
		
		return 0;
	}

	@Override
	public int updateAddress(AddressVo addressVo) throws Exception {
		// TODO Auto-generated method stub
		int addressBookCount = addressBookList.size();
		for (int i = 0; i < addressBookCount; i++) {
			AddressVo oneAddress = addressBookList.get(i);
			if (oneAddress != null && oneAddress.getName().equals(addressVo.getName())) {
				addressBookList.set(i, addressVo);
			}
		}
		
		return 0;
	}

	@Override
	public int deleteAddress(AddressVo addressVo) throws Exception {
		// TODO Auto-generated method stub
		int addressBookCount = addressBookList.size();
		int deleteIndex = -1;
		for (int i = 0; i < addressBookCount; i++) {
			AddressVo oneAddress = addressBookList.get(i);
			if (oneAddress != null && oneAddress.getName().equals(addressVo.getName())) {
				deleteIndex = i;
				break;
			}
		}
		
		if (deleteIndex >= 0) {
			addressBookList.remove(deleteIndex);
		}
		
		return 0;
	}

}
