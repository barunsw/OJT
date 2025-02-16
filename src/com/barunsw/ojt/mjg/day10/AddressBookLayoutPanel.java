package com.barunsw.ojt.mjg.day10;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.common.RmiAddressBookInterface;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.vo.AddressVo;

public class AddressBookLayoutPanel extends JPanel {
	
//	private AddressBookInterface addressBookInterface = new MybatisAddressBookImpl();
//	private AddressBookInterface addressBookInterface = new JdbcAddressBookImpl();
//	private AddressBookInterface addressBookInterface = new FileAddressBookImpl();
	//private AddressBookInterface addressBookInterface = new ObjectAddressBookImpl();
	private AddressBookInterface addressBookInterface;
	
	private static final Logger LOGGER = LogManager.getLogger(RmiAddressBookInterface.class);
	
	private int selectedRow = 0;
	private int lastSelectedRow = -1;
	
	private final int TABLE_CELL_ID_NAME 		= 0;
	private final int TABLE_CELL_ID_AGE			= 1;
	private final int TABLE_CELL_ID_GENDER		= 2;
	private final int TABLE_CELL_ID_PHONE		= 3;
	private final int TABLE_CELL_ID_ADDRESS	 	= 4;
	private final int TABLE_CELL_ID_PERSON		= 5;	
	
	private List<AddressVo> personList = new ArrayList<>();
	
	private JScrollPane jScrollPane = new JScrollPane();        // 주소록 스크롤
    private JTable jTable = new JTable();                       // 주소록 테이블
    private JSpinner jSpinner = new JSpinner();                 // 나이 Spinner
    
    // 입력 패널, 입력 필드
    private JPanel jPanel_Form = new JPanel();                          // 입력 패널 따로
    private JTextField jTextField_Name 		= new JTextField(10);       // 이름 입력 필드
    private JTextField jTextField_Phone 	= new JTextField(10);       // 전화번호 입력 필드
    private JTextField jTextField_Address 	= new JTextField(15);       // 주소 입력 필드

    private JPanel jPanel_Gender = new JPanel();                             // 라디오 버튼을 묶을 패널
    private JRadioButton jRadioButton_Male 		= new JRadioButton("남자");   // 남자 라디오 버튼
    private JRadioButton jRadioButton_Female 	= new JRadioButton("여자");   // 여자 라디오 버튼
    private ButtonGroup buttonGroup_Gender 		= new ButtonGroup();         // 성별 그룹
    
    // 버튼 패널, 버튼
    private JPanel jPanel_Command = new JPanel();               // 버튼 패널 따로
    private JButton jButton_Add 	= new JButton("추가");           // 추가 버튼
    private JButton jButton_Update 	= new JButton("변경");        // 변경 버튼
    private JButton jButton_Delete 	= new JButton("삭제");        // 삭제 버튼
    
    // 라벨
    private JLabel jLabel_Name 		= new JLabel("이름");             // 이름 라벨
    private JLabel jLabel_Age 		= new JLabel("나이");             // 나이 라벨
    private JLabel jLabel_Gender 	= new JLabel("성별");             // 성별 라벨
    private JLabel jLabel_Phone 	= new JLabel("전화번호");          // 전화번호 라벨
    private JLabel jLabel_Address 	= new JLabel("주소");             // 주소 라벨 
    
    private JPanel jPanel_Result = new JPanel();                // 결과 패널(테이블)
    
    private JScrollPane jScrollPane_Table = new JScrollPane(jTable);   // 테이블 및 스크롤 페인
    
    private Dimension BUTTON_SIZE = new Dimension(60, 22);

    private GridBagLayout gridBagLayout = new GridBagLayout();  // GridBagLayout 사용
    
    // 테이블 모델 가져오기
    private CommonTableModel tableModel = new CommonTableModel();
    
    // 셀 렌더러
    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    
    // 헤더 렌더러
    private JTableHeader tableHeader = jTable.getTableHeader();
    
    // 기본 헤더 렌더러 가져오기 - 기존 룩앤필 유지
    private DefaultTableCellRenderer defaultHeaderRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();

    public AddressBookLayoutPanel() {
        try {
        	initAddressBookIf();
            initComponent();
            initTable();
            initData();
        }
        catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void initAddressBookIf() {
    	Properties properties = new Properties();
    	try (Reader reader = Resources.getResourceAsReader("config.properties")) {
            properties.load(reader);
            
            String addressIfClass = properties.getProperty("address_if_class");
            
            
            Object o = Class.forName(addressIfClass).newInstance();
            
            addressBookInterface = (AddressBookInterface)o;
        }
        catch (IOException ioe) {
            throw new RuntimeException("JDBC 설정 로드 실패", ioe);
        } 
    	catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void initComponent() {
        this.setLayout(gridBagLayout);
        jPanel_Form.setLayout(gridBagLayout);
        jPanel_Command.setLayout(gridBagLayout);
        jPanel_Result.setLayout(gridBagLayout);
        
		jButton_Add.setPreferredSize(BUTTON_SIZE);
		jButton_Add.setMaximumSize(BUTTON_SIZE);
		jButton_Add.setMinimumSize(BUTTON_SIZE);
		
		jButton_Update.setPreferredSize(BUTTON_SIZE);
		jButton_Update.setMaximumSize(BUTTON_SIZE);
		jButton_Update.setMinimumSize(BUTTON_SIZE);
		
		jButton_Delete.setPreferredSize(BUTTON_SIZE);
		jButton_Delete.setMaximumSize(BUTTON_SIZE);
		jButton_Delete.setMinimumSize(BUTTON_SIZE);
        
        // 입력 패널 따로 설정
		this.add(jPanel_Form, new GridBagConstraints(
			0, 0, 1, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0
		));
		
        // 이름 라벨 및 텍스트 필드
		jPanel_Form.add(jLabel_Name, new GridBagConstraints(
            0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(5, 5, 5, 5), 0, 0
        ));
        
		jPanel_Form.add(jTextField_Name, new GridBagConstraints(
            1, 0, 1, 1, 1.0, 0.0,
            GridBagConstraints	.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 5, 5), 0, 0
        ));

        // 나이 라벨 및 스피너
		jPanel_Form.add(jLabel_Age, new GridBagConstraints(
            2, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(5, 0, 5, 5), 0, 0
        ));

		jPanel_Form.add(jSpinner, new GridBagConstraints(
            3, 0, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 5, 5), 0, 0
        ));

        // 성별 라벨 및 라디오 버튼
		jPanel_Form.add(jLabel_Gender, new GridBagConstraints(
            4, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(5, 0, 5, 5), 0, 0
        ));

        buttonGroup_Gender.add(jRadioButton_Male);
        buttonGroup_Gender.add(jRadioButton_Female);
        jPanel_Gender.add(jRadioButton_Male);
        jPanel_Gender.add(jRadioButton_Female);
        
        jPanel_Form.add(jPanel_Gender, new GridBagConstraints(
            5, 0, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(5, 0, 5, 5), 0, 0
        ));

        // 전화번호 라벨 및 텍스트 필드
        jPanel_Form.add(jLabel_Phone, new GridBagConstraints(
            0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(0, 5, 5, 5), 0, 0
        ));

        jPanel_Form.add(jTextField_Phone, new GridBagConstraints(
            1, 3, 5, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 5, 5), 0, 0
        ));

        // 주소 라벨 및 텍스트 필드
        jPanel_Form.add(jLabel_Address, new GridBagConstraints(
            0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(0, 5, 5, 5), 0, 0
        ));

        jPanel_Form.add(jTextField_Address, new GridBagConstraints(
            1, 4, 5, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 5, 5), 0, 0
        ));
        
        // 버튼 패널 따로 설정
		this.add(jPanel_Command, new GridBagConstraints(
			0, 5, 7, 1, 1.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 0, 0),	0, 0
		));
		
        // 버튼들
        jPanel_Command.add(jButton_Add, new GridBagConstraints(
            0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
            new Insets(0, 5, 5, 5), 0, 0
        ));

        jPanel_Command.add(jButton_Update, new GridBagConstraints(
            1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 5, 5), 0, 0
        ));

        jPanel_Command.add(jButton_Delete, new GridBagConstraints(
            2, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 5, 5), 0, 0
        ));
        
        // 테이블 스크롤
		this.add(jScrollPane_Table, new GridBagConstraints(
            0, 6, 7, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 5, 5), 0, 0
        ));
		
		// 추가 버튼 눌렀을 때
		jButton_Add.addActionListener(new AddressBookLayoutPanel_jButton_Add_ActionListener(this));
		
		// 변경 버튼 눌렀을 때
		jButton_Update.addActionListener(new AddressBookLayoutPanel_jButton_Update_ActionListener(this));
		
		// 삭제 버튼 눌렀을 때
		jButton_Delete.addActionListener(new AddressBookLayoutPanel_jButton_Delete_ActionListener(this));
		
		// 테이블 필드 클릭했을 때 텍스트 필드에 값 넣기
		jTable.addMouseListener(new AddressBookLayoutPanel_jTable_MouseListener(this));

    }
    
    // 테이블 생성
    private void initTable() {
    	
    	Vector<String> columnData = new Vector<>();
    	
        // 테이블 컬럼명 정의
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("성별");
		columnData.add("전화번호");
		columnData.add("주소");
        
		// 데이터 모델에 속성값 지정
		tableModel.setColumn(columnData);
		
        // JTable에 테이블 모델 설정하여 테이블 구성
        jTable.setModel(tableModel);
        
        // 가운데 정렬
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        // 각 컬럼에 대해 width, 렌더러 설정
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            switch (i) {
                case TABLE_CELL_ID_NAME:
                    jTable.getColumnModel().getColumn(i).setPreferredWidth(75);
                    jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    break;
                case TABLE_CELL_ID_AGE:
                    jTable.getColumnModel().getColumn(i).setPreferredWidth(50);
                    jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    break;
                case TABLE_CELL_ID_GENDER:
                    jTable.getColumnModel().getColumn(i).setPreferredWidth(50);
                    jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    break;
                case TABLE_CELL_ID_PHONE:
                    jTable.getColumnModel().getColumn(i).setPreferredWidth(150);
                    jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    break;
                case TABLE_CELL_ID_ADDRESS:
                    jTable.getColumnModel().getColumn(i).setPreferredWidth(150);
                    jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    break;
                default:
                    // 추가 컬럼이 있다면 기본값
                    jTable.getColumnModel().getColumn(i).setPreferredWidth(100);
                    jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                    break;
            }   
        }
	
	    // 정렬만 가운데 정렬로 변경
	    defaultHeaderRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	
	    // 렌더러를 다시 테이블 헤더에 설정
	    tableHeader.setDefaultRenderer(defaultHeaderRenderer);
        
    }
    
    // DB or 파일에서 조회한 데이터 테이블에 반영
    private void initData() {
        // DB or 파일에서 전체 목록 조회
    	try {
			personList = addressBookInterface.selectAddressList(new AddressVo());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
        
    	Vector rowData = new Vector<>();
    	
    	for (AddressVo onePerson : personList) {
    		Vector data = new Vector();
    		data.add(onePerson.getName());
    		data.add(onePerson.getAge());
    		data.add(onePerson.getGender());
    		data.add(onePerson.getPhone());
    		data.add(onePerson.getAddress());
    		data.add(onePerson);
    		
    		// 전체 객체(AddressVo) 자체를 Vector에 추가
            // 실제 테이블에 표시되지는 않음
            // UPDATE, DELETE 등 작업 시에 ID(또는 seq)나 기타 필드를 참조하기 위해 사용
            // 객체에서 onePerson.getSeq() 꺼내서 사용 가능 
    		
    		// LOGGER.info( "Phone : " + onePerson.getPhone() );
    		// LOGGER.info( "person : " + onePerson );
    		
    		rowData.add(data);
    	}
    	
    	// 테이블 모델에 완성된 rowData를 반영
    	tableModel.setData(rowData);
    	
    	// 테이블 내용이 바뀐걸 모델에 알리는 작업
    	tableModel.fireTableDataChanged();   
    }
    
	// 데이터 추가
	private void insertData() {
		try {
			// 화면 필드값 읽기
			String name    = jTextField_Name.getText().trim();
			String phone   = jTextField_Phone.getText().trim();
			String address = jTextField_Address.getText().trim();
			int age        = (Integer) jSpinner.getValue();

			Gender gender = null;
			if (jRadioButton_Male.isSelected()) {
				gender = Gender.toGender(jRadioButton_Male.getText());
			}
			else if (jRadioButton_Female.isSelected()) {
				gender = Gender.toGender(jRadioButton_Female.getText());
			}

			// 모든 값 들어있는지 체크
			if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || gender == null) {
				JOptionPane.showMessageDialog(
					this,
					"모든 값을 입력하세요.",
					"경고",
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}

			// VO 세팅
			AddressVo onePerson = new AddressVo();
			onePerson.setName(name);
			onePerson.setAge(age);
			onePerson.setGender(gender);
			onePerson.setPhone(phone);
			onePerson.setAddress(address);

			// DB or 파일에 Insert
			addressBookInterface.insertAddress(onePerson);

			// insert 후 선택된 Row 업데이트
			selectedRow = jTable.getSelectedRow();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		} 
	}
	
	// 데이터 변경
	private void updateData() {

		try {
			AddressVo onePerson = new AddressVo();
			int selectedRow = jTable.getSelectedRow();
			
			int age = (Integer)jSpinner.getValue();
			
			onePerson = personList.get(selectedRow);
			
			onePerson.setName(jTextField_Name.getText());
			
			onePerson.setAge(age);
			
			if (jRadioButton_Male.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Male.getText()));
			}
			else if (jRadioButton_Female.isSelected()) {
				onePerson.setGender(Gender.toGender(jRadioButton_Female.getText()));
			}
			else {
				JOptionPane.showMessageDialog(AddressBookLayoutPanel.this, "값을 선택하세요.", "경고", JOptionPane.ERROR_MESSAGE);
			}
			
			onePerson.setPhone(jTextField_Phone.getText());
			
			onePerson.setAddress(jTextField_Address.getText());
			
			Object value = tableModel.getValueAt(selectedRow, TABLE_CELL_ID_PERSON);
			if (value instanceof AddressVo) {
				try {
					AddressVo addressVo = (AddressVo)value;
					addressBookInterface.updateAddress(addressVo);
				}
				catch (Exception ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
			}
			
			addressBookInterface.updateAddress(onePerson);

			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void deleteData() {
		LOGGER.debug(selectedRow);
		
		Object value = tableModel.getValueAt(selectedRow, TABLE_CELL_ID_PERSON);
		
		LOGGER.debug(value);
		if (value instanceof AddressVo) {
			try {
				AddressVo addressVo = (AddressVo)value;
				addressBookInterface.deleteAddress(addressVo);
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}

			initData();
		}
	}
	
	// 추가 버튼 눌렀을 때 수행
	void jButton_Add_ActionListener(ActionEvent e) {
		insertData();
		initData();
	}
	
	// 변경 버튼 눌렀을 때 수행
	void jButton_Update_ActionListener(ActionEvent e) {
	    // 현재 테이블에서 선택된 row 확인
	    selectedRow = jTable.getSelectedRow();
	    if (selectedRow < 0) {
	        // 아무것도 선택 안 되었으면 경고
	        JOptionPane.showMessageDialog(
	            this,
	            "변경할 행을 선택하세요.",
	            "경고",
	            JOptionPane.WARNING_MESSAGE
	        );
	        return;
	    }
	    
		updateData();
		initData();
	}
	
	// 삭제 버튼 눌렀을 때 수행
	void jButton_Delete_ActionListener(ActionEvent e) {
	    // 현재 테이블에서 선택된 row 확인
	    selectedRow = jTable.getSelectedRow();
	    if (selectedRow < 0) {
	        // 아무것도 선택 안 되었으면 경고
	        JOptionPane.showMessageDialog(
	            this,
	            "삭제할 행을 선택하세요.",
	            "경고",
	            JOptionPane.WARNING_MESSAGE
	        );
	        return;
	    }

	    // 정말 삭제할지 확인
	    int confirm = JOptionPane.showConfirmDialog(
	        this,
	        "정말 삭제하시겠습니까?",
	        "삭제 확인",
	        JOptionPane.YES_NO_OPTION
	    );
	    if (confirm != JOptionPane.YES_OPTION) {
	        return;
	    }

	    deleteData();
	    initData();
	}
	
	void jTable_MouseListener(MouseEvent e) {
		
		try {
	        int newSelectedRow = jTable.getSelectedRow();
	        if (newSelectedRow < 0) {
	            return;
	        }

	        // 선택된 행 다시 클릭 → 입력 필드 초기화
	        if (newSelectedRow == lastSelectedRow) {
	            // 필드값 초기화
	            jTextField_Name.setText("");
	            jSpinner.setValue(0); // 기본값 0
	            buttonGroup_Gender.clearSelection(); // 라디오버튼 선택 해제
	            jTextField_Phone.setText("");
	            jTextField_Address.setText("");

	            // 테이블 선택도 해제
	            jTable.clearSelection();
	            
	            // lastSelectedRow를 -1로 리셋
	            lastSelectedRow = -1;
	            
	            return;
	        }
	        
	        // 다른 행 새로 클릭한 경우 → 해당 행의 데이터로 필드를 채움
	        lastSelectedRow = newSelectedRow;  // 갱신
	        
	        LOGGER.debug(personList.get(newSelectedRow));
	        AddressVo onePerson = personList.get(newSelectedRow);

	        jTextField_Name.setText(onePerson.getName());
	        jSpinner.setValue(onePerson.getAge());

	        if (onePerson.getGender() == Gender.toGender("남")) {
	            jRadioButton_Male.setSelected(true);
	        }
	        else {
	            jRadioButton_Female.setSelected(true);
	        }

	        jTextField_Phone.setText(onePerson.getPhone());
	        jTextField_Address.setText(onePerson.getAddress());

	        LOGGER.debug(newSelectedRow);
	    }
	    catch (Exception ex) {
	        LOGGER.error(ex.getMessage(), ex);
	    }	
	} 
}

class AddressBookLayoutPanel_jButton_Add_ActionListener implements ActionListener {
	private AddressBookLayoutPanel adaptee;
	
	public AddressBookLayoutPanel_jButton_Add_ActionListener(AddressBookLayoutPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Add_ActionListener(e);
	}
}

class AddressBookLayoutPanel_jButton_Update_ActionListener implements ActionListener {
	private AddressBookLayoutPanel adaptee;
	
	public AddressBookLayoutPanel_jButton_Update_ActionListener(AddressBookLayoutPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Update_ActionListener(e);
	}
}

class AddressBookLayoutPanel_jButton_Delete_ActionListener implements ActionListener {
	private AddressBookLayoutPanel adaptee;

	public AddressBookLayoutPanel_jButton_Delete_ActionListener(AddressBookLayoutPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Delete_ActionListener(e);
	}
}

class AddressBookLayoutPanel_jTable_MouseListener extends MouseAdapter {
    private AddressBookLayoutPanel adaptee;
    
    public AddressBookLayoutPanel_jTable_MouseListener(AddressBookLayoutPanel adaptee) {
        this.adaptee = adaptee;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        adaptee.jTable_MouseListener(e);
    }
}

