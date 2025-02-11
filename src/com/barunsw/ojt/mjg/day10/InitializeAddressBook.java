//package com.barunsw.ojt.mjg.day10;
//
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.barunsw.ojt.mjg.constants.Gender;
//import com.barunsw.ojt.vo.AddressVo;
//
//public class InitializeAddressBook {
//    public static void main(String[] args) {
//        List<AddressVo> addressList = new ArrayList<>();
//        
//        // 기존 AddressVo에 맞는 데이터 추가
//        addressList.add(new AddressVo(1, "문종근", 26, Gender.MAN, "서울시 송파구", "010-1234-5678"));
////        addressList.add(new AddressVo(2, "신사임당", 28, Gender.WOMAN, "서울시 송파구", "010-1111-1111"));
////        addressList.add(new AddressVo(3, "장보고", 50, Gender.MAN, "완도", "010-1111-2222"));
//
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/com/barunsw/ojt/mjg/day10/address_book.dat"))) {
//            oos.writeObject(addressList);
//            System.out.println("데이터가 성공적으로 저장되었습니다.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
