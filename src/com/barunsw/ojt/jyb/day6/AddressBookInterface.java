package day6;

import java.util.List;

public interface AddressBookInterface {

	public List<AddressVO> selectAddressList(AddressVO addressVo) throws Exception;

	public void insertAddress(AddressVO addressVo) throws Exception;

	public void updateAddress(AddressVO addressVo) throws Exception;

	public void deleteAddress(AddressVO addressVo) throws Exception;

}
