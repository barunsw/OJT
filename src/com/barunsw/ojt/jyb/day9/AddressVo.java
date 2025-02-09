package day9;

import java.io.Serializable;

public class AddressVo implements Serializable {
	private int seq;
	private String name;
	private int age;
	private String phone;
	private String address;
	private Gender gender;

	public AddressVo() {

	}

	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "seq=" + seq + ", name=" + name + ", age=" + age + ", phone=" + phone + ", address=" + address;
	}

}
