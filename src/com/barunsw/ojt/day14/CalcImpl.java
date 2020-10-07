package com.barunsw.ojt.day14;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalcImpl extends UnicastRemoteObject implements CalcInterface {
	public CalcImpl() throws RemoteException {
	}
	
	@Override
	public int add(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return a + b;
	}

	@Override
	public int substract(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return a - b;
	}

	@Override
	public int multiply(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return a * b;
	}

	@Override
	public int devide(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return a / b;
	}
}
