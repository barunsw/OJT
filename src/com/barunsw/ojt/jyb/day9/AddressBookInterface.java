package day9;

import java.util.List;

public interface AddressBookInterface {
	public List<AddressVo> selectAddressList(AddressVo addressVO);

	public int insertAddress(AddressVo addressVO) throws Exception;

	public int updateAddress(AddressVo addressVO) throws Exception;

	public int deleteAddress(AddressVo addressVO) throws Exception;
}
