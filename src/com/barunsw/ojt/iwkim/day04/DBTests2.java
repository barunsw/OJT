package com.barunsw.ojt.iwkim.day04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





public class DBTests2 {
	//입력을 하면 파일에도 저장 DB에도 저장
	private static Logger LOG = LogManager.getLogger(DBTests2.class);
	public static int NUMBER_PERSON = 1;
	private static final String url = "jdbc:mysql://localhost:3306/study01?autoReconnect=true";
	private static final String id = "root";
	private static final String pw = "barun";
	
	public static void main(String[] args) {
		
		
		Scanner sc = new Scanner(System.in);
		
		//CLI는 Command Line Interface 줄임말.  
		//사용자는 커맨드 즉 문자열을 통해 컴퓨터에게 명령을 하고 컴퓨터의 응답도 역시 문자열로 출력해주는 방식
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			LOG.info(e.getMessage(), e);
		}
		
		while (true) {
			System.out.println();
			System.out.println("'C'  입력 -> 개인정보 입력");
			System.out.println("'U'  입력 -> 개인정보 수정");
			System.out.println("'D'  입력 -> 개인정보 삭제");
			System.out.println("'RF' 입력 -> 개인정보 출력(파일)");
			System.out.println("'RD' 입력 -> 개인정보 출력(DB)");
			System.out.println("'END' 입력 -> 프로그램 종료");
			System.out.println();
			System.out.print("입력 : ");
			
			String input = sc.next();
			String name = null;
			String gender = null;
			String birth = null;
			String email = null;
			int seqNum = 0;
			
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			int queryExcResult = 0;
			ResultSet rs = null;
			File file = new File("data/day04/personInfo.cvs");
			
			
			BufferedReader reader = null; 
			BufferedWriter writer = null; 
			String currentUpdateDate = null;
			
			switch (input) {
				//입력
				case "c" :
				case "C" :
					System.out.println();
					System.out.println("개인정보를 입력해주세요.");
					System.out.print("이름 : ");
					name = sc.next();
					System.out.print("성별 : ");
					gender = sc.next();
					System.out.print("생년월일 : ");
					birth = sc.next();
					System.out.print("이메일 : ");
					email = sc.next();
				
					//파일에 개인정보 출력
				
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String regDate = format.format(new Date());
					String updateDate = format.format(new Date()); // 객체 하나만 제한
					
					try {	 
						writer = new BufferedWriter(new FileWriter(file, true));
						
						writer.write(NUMBER_PERSON + "," + name + "," + gender + "," + birth + "," + email + "," 
								+ regDate + "," + updateDate);
						NUMBER_PERSON++;
						writer.write("\n");
						
						writer.flush();
					}
					catch (FileNotFoundException fnfe) {
						LOG.info(fnfe.getMessage(), fnfe);
					    return;
					}
					catch (IOException ioe) {
						LOG.info(ioe.getMessage(), ioe);
					}
					finally {
						if (writer != null) {
							try {
								writer.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
					}
				
					//DB에 개인정보 입력
					try {
						String sql = "INSERT INTO person(name, gender, birth, email, regDate, updateDate) VALUES(?,?,?,?,?,?)";
						conn = DriverManager.getConnection(url,id,pw);
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, name);
						pstmt.setString(2, gender);
						pstmt.setString(3, birth);
						pstmt.setString(4, email);
						pstmt.setString(5, regDate);
						pstmt.setString(6, updateDate);
					
						queryExcResult = pstmt.executeUpdate();
					} 
					catch (SQLException e) {
						LOG.info(e.getMessage(), e);
					}
					finally {
						if( pstmt != null ) {
							try {
								pstmt.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
					}
					System.out.println("입력이 정상적으로 처리되었습니다.");
				break;
				
				//수정
				case "U" :
				case "u" :
					System.out.println("\n수정할 번호를 입력해주세요.");
					System.out.print("번호 : ");
					seqNum = sc.nextInt();
					
					
					String updateName = null;
					String updateGender = null;
					String updateBirth = null;
					String updateEmail = null;
					
					
					System.out.println("\n수정할 정보들을 차례대로 입력해주세요.");
					System.out.print("이름 : ");
					updateName = sc.next();
					System.out.print("성별 : ");
					updateGender = sc.next();
					System.out.print("생년월일 : ");
					updateBirth = sc.next();
					System.out.print("이메일 : ");
					updateEmail = sc.next();
					
					
					
					//DB에 개인정보 수정
					try {
						currentUpdateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						String sql = "UPDATE person SET name=?, gender=?, birth=?, email=?, updateDate=? where seq=?";
						conn = DriverManager.getConnection(url,id,pw);
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, updateName);
						pstmt.setString(2, updateGender);
						pstmt.setString(3, updateBirth);
						pstmt.setString(4, updateEmail);
						pstmt.setString(5, currentUpdateDate);
						pstmt.setInt(6, seqNum);
						
					
					
						queryExcResult = pstmt.executeUpdate();
						
					} 
					catch (SQLException e) {
						System.out.println("입력하신 정보가 없습니다. 처음부터 다시시도해주세요");
						break;
					}
					
					finally {
						if( pstmt != null ) {
							try {
								pstmt.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
					}
				
					//파일의 내용 수정
					/*
					try {
						reader = new BufferedReader(new FileReader("data/day04/personInfo.cvs")); 
						writer = new BufferedWriter(new FileWriter(new File("data/day04/personInfo.cvs")));
						String readLine = null;
						while ((readLine = reader.readLine()) != null) { // 한줄씩 읽어들여서 null이 아닐때까지 반복
							
							String[] splitData = readLine.split(",");
												
							if (Integer.parseInt(splitData[0]) == key) { // 번호가 수정할 번호인 line은 수정한 내용으로 변경
								String beforeName = splitData[1];
								String beforeGender = splitData[2];
								String beforeBirth = splitData[3];
								String beforeEmail = splitData[4];
								String beforeUpdateDate = splitData[6];
								readLine.replace(beforeName, updateName);
								readLine.replace(beforeGender, updateGender);
								readLine.replace(beforeBirth, updateBirth);
								readLine.replace(beforeEmail, updateEmail);
								readLine.replace(beforeUpdateDate, currentUpdateDate);
							}
							
							writer.write(readLine + "\n");
							writer.flush();
						
						}			
					}
					catch (FileNotFoundException fnfe) {
						LOG.info(fnfe.getMessage(), fnfe);
					}
					catch (IOException ioe) {
						LOG.info(ioe.getMessage(), ioe);
					}
					finally {
						if (reader != null ) {
							try {
								reader.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
						if (writer != null) {
							try {
								writer.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
					}
					*/
					System.out.println("수정이 완료되었습니다.");
				break;
					
				//삭제
				case "D" :
				case "d" :
					System.out.println("\n삭제할 번호를 입력해주세요.");
					System.out.print("번호 : ");
					seqNum = sc.nextInt();
					
					//DB에서 데이터 삭제
					try {
						String sql = "Delete FROM person where seq=?";
						conn = DriverManager.getConnection(url,id,pw);
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, seqNum);
						
						queryExcResult = pstmt.executeUpdate();
						
					} 
					catch (SQLException e) {
						System.out.println("번호에 해당하는 정보가 없습니다. 처음부터 다시시도해주세요");
						break;
					}
					
					finally {
						if( pstmt != null ) {
							try {
								pstmt.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
					}

					System.out.println("삭제가 완료되었습니다.");
				break;
					
					
				//파일에서 조회	
				case "RF" :
				case "rf" :
					System.out.println("\n조회할 번호를 입력해주세요.");
					System.out.print("번호 : ");
					seqNum = sc.nextInt();
					
					// 파일에서 개인정보 가져오기
					
					try {
						reader = new BufferedReader(new FileReader(file)); 
						String readLine = null;
						boolean flag = false;
						while ((readLine = reader.readLine()) != null) { // 한줄씩 읽어들여서 null이 아닐때까지 반복
							String[] splitData = readLine.split(",");
							if (Integer.parseInt(splitData[0]) == seqNum) {
								System.out.println("\n검색 결과 입니다.");
								System.out.println("이름     : " + splitData[1]);
								System.out.println("성별     : " + splitData[2]);
								System.out.println("생년월일 : " + splitData[3]);
								System.out.println("이메일   : " + splitData[4]);
								System.out.println("등록일   : " + splitData[5]);
								System.out.println("수정일   : " + splitData[6]);
								flag = true;
								break;
							}
						}	
						if (!flag) {
							System.out.println("검색 결과가 존재하지 않습니다. 처음부터 다시 시도해주세요");
							break;
						}
					}
					catch (FileNotFoundException fnfe) {
						LOG.info(fnfe.getMessage(), fnfe);
					}
					catch (IOException ioe) {
						LOG.info(ioe.getMessage(), ioe);
					}
					finally {
						if (reader != null ) {
							try {
								reader.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
					}
				break;
					
					
				//DB에서 조회
				case "RD" :
				case "rd" :
					System.out.println("\n조회할 번호를 입력해주세요.");
					System.out.print("번호 : ");
					seqNum = sc.nextInt();
					
					//DB에서 개인정보 가져오기		
					try {
						String sql = "SELECT * FROM person WHERE seq=?";
						conn = DriverManager.getConnection(url,id,pw);
						pstmt = conn.prepareStatement(sql);					
						pstmt.setInt(1, seqNum);		
						rs = pstmt.executeQuery();	
						if (rs.next()) {
							name = rs.getString("name");
							gender = rs.getString("gender");
							birth = rs.getString("birth");
							email = rs.getString("email");
							regDate = rs.getString("regDate");
							updateDate = rs.getString("updateDate");
							
							System.out.println("검색 결과 입니다.");
							System.out.println("이름     : " + name);
							System.out.println("성별     : " + gender);
							System.out.println("생년월일 : " + birth);
							System.out.println("이메일   : " + email);
							System.out.println("등록일   : " + regDate);
							System.out.println("수정일   : " + updateDate);	
						}
						else {
							System.out.println("검색 결과가 존재하지 않습니다. 처음부터 다시 시도해주세요.");
							break;
						}
					} 
					catch (SQLException e) {
						LOG.info(e.getMessage(), e);
						System.out.println("검색 결과가 존재하지 않습니다. 처음부터 다시 시도해주세요.");
						break;
					}
					finally {
						if( pstmt != null ) {
							try {
								pstmt.close();
							}
							catch (Exception e) {
								LOG.info(e.getMessage(), e);
							}
						}
					}
				break;
					
				
					
					
				case "END" :
				case "end" :
					System.out.println("프로그램을 종료합니다.");
				return;
				
				default :
					System.out.println("잘못 입력하셨습니다. 다시 입력해주세요");
				break;
			}
			
		}
		
		
		
		
		
		
	}
}
