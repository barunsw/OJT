package day4;

import java.util.List;
import java.util.Scanner;

public class AddressBookApp {
	public static void main(String[] args) {
		try {
			ObjectAddressBookImpl addressBook = new ObjectAddressBookImpl();
			Scanner scanner = new Scanner(System.in);
			int choice;

			do {
				System.out.println();
				System.out.println("주소록");
				System.out.println();
				System.out.println("1. 추가");
				System.out.println("2. 조회");
				System.out.println("3. 수정");
				System.out.println("4. 삭제");
				System.out.println("0. 종료");
				System.out.print("선택: ");
				choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {
				case 1:
					AddressVO newAddress = new AddressVO();
					System.out.print("이름: ");
					newAddress.setName(scanner.nextLine());
					System.out.print("나이: ");
					newAddress.setAge(scanner.nextInt());
					scanner.nextLine();
					System.out.print("전화번호: ");
					newAddress.setPhone(scanner.nextLine());
					System.out.print("주소: ");
					newAddress.setAddress(scanner.nextLine());
					addressBook.insertAddress(newAddress);
					System.out.println("주소가 추가되었습니다.");
					break;

				case 2:
					List<AddressVO> addressList = addressBook.selectAddressList(null);
					System.out.println("주소록 목록:");
					for (AddressVO address : addressList) {
						System.out.println(address);
					}
					break;

				case 3:
					System.out.print("수정할 주소의 SEQ 입력: ");
					int seqToUpdate = scanner.nextInt();
					scanner.nextLine();
					AddressVO updateAddress = new AddressVO();
					updateAddress.setSeq(seqToUpdate);
					System.out.print("새 이름: ");
					updateAddress.setName(scanner.nextLine());
					System.out.print("새 나이: ");
					updateAddress.setAge(scanner.nextInt());
					scanner.nextLine();
					System.out.print("새 전화번호: ");
					updateAddress.setPhone(scanner.nextLine());
					System.out.print("새 주소: ");
					updateAddress.setAddress(scanner.nextLine());
					addressBook.updateAddress(updateAddress);
					System.out.println("주소가 수정되었습니다.");
					break;

				case 4:
					System.out.print("삭제할 주소의 SEQ 입력: ");
					int seqToDelete = scanner.nextInt();
					AddressVO deleteAddress = new AddressVO();
					deleteAddress.setSeq(seqToDelete);
					addressBook.deleteAddress(deleteAddress);
					System.out.println("주소가 삭제되었습니다.");
					break;

				case 0:
					System.out.println("프로그램을 종료합니다.");
					break;

				default:
					System.out.println("잘못된 선택입니다. 다시 시도하세요.");
				}
			} while (choice != 0);

			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
