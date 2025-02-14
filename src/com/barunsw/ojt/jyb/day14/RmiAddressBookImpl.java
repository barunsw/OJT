package com.barunsw.ojt.jyb.day14;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.jyb.day10.FileAddressBookImpl;
import com.barunsw.ojt.jyb.day10.JdbcAddressBookImpl;
import com.barunsw.ojt.jyb.day10.ObjectAddressBookImpl;
import com.barunsw.ojt.vo.AddressVo;

public class RmiAddressBookImpl extends UnicastRemoteObject implements RmiAddressBookInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(RmiAddressBookImpl.class);
	private final JdbcAddressBookImpl jdbcAddressBookImpl;
//	private final MybatisAddressBookImpl mybatisAddressBookImpl;
//	private final ObjectAddressBookImpl objectAddressBookImpl;
//	private final FileAddressBookImpl fileAddressBookImpl;

	protected RmiAddressBookImpl() throws RemoteException {
		super();
		this.jdbcAddressBookImpl = new JdbcAddressBookImpl();
//		this.mybatisAddressBookImpl = new MybatisAddressBookImpl();
//		this.objectAddressBookImpl = new ObjectAddressBookImpl();
//		this.fileAddressBookImpl = new FileAddressBookImpl();
	}

	@Override
	public List<AddressVo> selectAddressList(AddressVo addressVo) {
		return jdbcAddressBookImpl.selectAddressList(addressVo);
	}

	@Override
	public int insertAddress(AddressVo addressVo) {
		return jdbcAddressBookImpl.insertAddress(addressVo);
	}

	@Override
	public int updateAddress(AddressVo addressVo) {
		return jdbcAddressBookImpl.updateAddress(addressVo);
	}

	@Override
	public int deleteAddress(AddressVo addressVo) {
		return jdbcAddressBookImpl.deleteAddress(addressVo);
	}

}
