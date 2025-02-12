package com.barunsw.ojt.mjg.day11;

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
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.ibatis.io.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.barunsw.ojt.common.AddressBookInterface;
import com.barunsw.ojt.vo.AddressVo;
import com.barunsw.ojt.constants.Gender;
import com.barunsw.ojt.constants.SearchType;
import com.barunsw.ojt.mjg.day14.SocketAddressBookImpl;

public class AddressBookLayoutPanel extends JPanel {
	
//	private AddressBookInterface addressBookInterface = new MybatisAddressBookImpl();
	private AddressBookInterface addressBookInterface;

	private static final Logger LOGGER = LogManager.getLogger(AddressBookLayoutPanel.class);
	
	private int selectedRow = 0;
	private int lastSelectedRow = -1;
	private String currentInitial = "";
	
	private final int TABLE_CELL_ID_NAME 		= 0;
	private final int TABLE_CELL_ID_AGE			= 1;
	private final int TABLE_CELL_ID_GENDER		= 2;
	private final int TABLE_CELL_ID_PHONE		= 3;
	private final int TABLE_CELL_ID_ADDRESS	 	= 4;
	private final int TABLE_CELL_ID_PERSON		= 5;	
	
	private List<AddressVo> personList = new ArrayList<>();
	private List<AddressVo> currentFilteredList = new ArrayList<>();
	
	private JScrollPane jScrollPane = new JScrollPane();        // 스크롤
    private JTable jTable = new JTable();                       // 주소록 테이블
    private JTree jTree = new JTree();                          // 트리
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode rootTreeNode = new DefaultMutableTreeNode("주소록");
    
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
    private JPanel jPanel_Command = new JPanel();                // 버튼 패널 따로
    private JButton jButton_Add 	= new JButton("추가");        // 추가 버튼
    private JButton jButton_Update 	= new JButton("변경");        // 변경 버튼
    private JButton jButton_Delete 	= new JButton("삭제");        // 삭제 버튼
    
    // 라벨
    private JLabel jLabel_Name 		= new JLabel("이름");             // 이름 라벨
    private JLabel jLabel_Age 		= new JLabel("나이");             // 나이 라벨
    private JLabel jLabel_Gender 	= new JLabel("성별");             // 성별 라벨
    private JLabel jLabel_Phone 	= new JLabel("전화번호");          // 전화번호 라벨
    private JLabel jLabel_Address 	= new JLabel("주소");             // 주소 라벨 
    
    private JPanel jPanel_Result = new JPanel();                	 // 결과 패널(테이블)
    
    private JScrollPane jScrollPane_Table = new JScrollPane(jTable);   // 테이블 스크롤 페인
    private JScrollPane jScrollPane_Tree = new JScrollPane(jTree);     // 트리 스크롤 페인
    
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
            initTree();
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

            String addressIfClass = properties.getProperty("address_socket");
            Object o = null; // 인스턴스를 저장할 변수

            if (addressIfClass.contains("SocketAddressBookImpl")) {
                // SocketAddressBookImpl을 위한 host, port 정보 가져오기
                String serverHost = properties.getProperty("host"); 				// 호스트
                int serverPort = Integer.parseInt(properties.getProperty("port"));  // 포트

                o = new SocketAddressBookImpl(serverHost, serverPort);  // 직접 생성
            } 

            if (o != null && o instanceof AddressBookInterface) {
                addressBookInterface = (AddressBookInterface) o;  // 인터페이스로 캐스팅
            }
        } 
        catch (IOException ioe) {
            throw new RuntimeException("설정 파일 로드 실패", ioe);
        } 
        catch (ClassNotFoundException cnfe) {
            throw new RuntimeException("클래스를 찾을 수 없습니다: " + cnfe.getMessage(), cnfe);
        } 
        catch (InstantiationException ie) {
            throw new RuntimeException("클래스 인스턴스 생성 실패: " + ie.getMessage(), ie);
        } 
        catch (IllegalAccessException iae) {
            throw new RuntimeException("클래스에 접근할 수 없습니다: " + iae.getMessage(), iae);
        } 
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void initComponent() {
        this.setLayout(gridBagLayout);
        
        // 입력 패널(이름, 나이, 성별, 전화번호, 주소)
        jPanel_Form.setLayout(gridBagLayout);
        
        // 버튼 패널
        jPanel_Command.setLayout(gridBagLayout);
        
        // 결과 패널(트리, 테이블)
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
            0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(0, 5, 5, 5), 0, 0
        ));

        jPanel_Form.add(jTextField_Phone, new GridBagConstraints(
            1, 1, 5, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 5, 5), 0, 0
        ));

        // 주소 라벨 및 텍스트 필드
        jPanel_Form.add(jLabel_Address, new GridBagConstraints(
            0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(0, 5, 5, 5), 0, 0
        ));

        jPanel_Form.add(jTextField_Address, new GridBagConstraints(
            1, 2, 5, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 5, 5), 0, 0
        ));
        
        // 버튼 패널 따로 설정
		this.add(jPanel_Command, new GridBagConstraints(
			0, 3, 7, 1, 1.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 0, 0),	0, 0
		));
		
        // 버튼들
        jPanel_Command.add(jButton_Add, new GridBagConstraints(
            0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.NONE,
            new Insets(0, 5, 5, 5), 0, 0
        ));

        jPanel_Command.add(jButton_Update, new GridBagConstraints(
            1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0
        ));

        jPanel_Command.add(jButton_Delete, new GridBagConstraints(
            2, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0
        ));
        
		// 결과 패널 따로 설정
		this.add(jPanel_Result, new GridBagConstraints(
		    0, 4, 7, 1, 1.0, 1.0,
		    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
		    new Insets(0, 0, 0, 0), 0, 0
		));

		// JTree
		jPanel_Result.add(jScrollPane_Tree, new GridBagConstraints(
		    0, 0, 1, 1, 0.0, 1.0,
		    GridBagConstraints.WEST, GridBagConstraints.BOTH,
		    new Insets(0, 5, 5, 5), 0, 0
		));

		// JTable
		jPanel_Result.add(jScrollPane_Table, new GridBagConstraints(
		    1, 0, 5, 1, 1.0, 1.0,
		    GridBagConstraints.WEST, GridBagConstraints.BOTH,
		    new Insets(0, 0, 5, 5), 0, 0
		));
		
		jScrollPane_Tree.getViewport().add(jTree);
		jScrollPane_Table.getViewport().add(jTable);
		
		// 추가 버튼 눌렀을 때
		jButton_Add.addActionListener(new AddressBookLayoutPanel_jButton_Add_ActionListener(this));
		
		// 변경 버튼 눌렀을 때
		jButton_Update.addActionListener(new AddressBookLayoutPanel_jButton_Update_ActionListener(this));
		
		// 삭제 버튼 눌렀을 때
		jButton_Delete.addActionListener(new AddressBookLayoutPanel_jButton_Delete_ActionListener(this));
		
		// 테이블 필드 클릭했을 때 텍스트 필드에 값 넣기
		jTable.addMouseListener(new AddressBookLayoutPanel_jTable_MouseListener(this));
		
		// 트리 선택
		jTree.addTreeSelectionListener(new AddressBookLayoutPanel_jTree_SelectionListener(this));

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
    
    // 트리 생성
	private void initTree() {
		treeModel = new DefaultTreeModel(rootTreeNode);
		
		jTree.setModel(treeModel);
//		jTree.setRootVisible(false);
		
	    // 데이터를 모두 추가한 후 트리 확장
	    initData();
		
	    // 트리 초기화 후 바로 첫 번째 노드 확장
	    jTree.expandRow(0);
	    
	    // 초성 노드 선택 
	    jTree.addTreeSelectionListener(new AddressBookLayoutPanel_jTree_SelectionListener(this));
	    
	}
	
	// 트리에서 선택한 초성에 따른 테이블 업데이트
	private void updateTable(List<AddressVo> filteredList) {
	    Vector<Vector<Object>> rowData = new Vector<>();
	    currentFilteredList = filteredList;
	    
	    for (AddressVo person : filteredList) {
	        Vector<Object> data = new Vector<>();
	        data.add(person.getName());
	        data.add(person.getAge());
	        data.add(person.getGender());
	        data.add(person.getPhone());
	        data.add(person.getAddress());
	        rowData.add(data);
	    }
	    
	    // 테이블 모델에 새로운 데이터 반영
	    tableModel.setData(rowData);
	    tableModel.fireTableDataChanged();
	}

    
    // DB or 파일에서 조회한 데이터 테이블에 반영
	// 트리 데이터 반영
    private void initData() {
        // DB or 파일에서 전체 목록 조회
    	personList = addressBookInterface.selectAddressList(new AddressVo());
    	currentFilteredList = personList;	
        
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
    	
    	// 주소록 루트트리에 초성 추가
	    String[] initialList = { "ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };

	    for (String initial : initialList) {
	        rootTreeNode.add(new DefaultMutableTreeNode(initial));
	    }
    }
    
	// 데이터 추가
    private void insertData() {
        try {
            String name = jTextField_Name.getText().trim();
            String phone = jTextField_Phone.getText().trim();
            String address = jTextField_Address.getText().trim();
            int age = (Integer) jSpinner.getValue();

            Gender gender = null;
            if (jRadioButton_Male.isSelected()) {
                gender = Gender.MAN;
            } else if (jRadioButton_Female.isSelected()) {
                gender = Gender.WOMAN;
            }

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || gender == null) {
                JOptionPane.showMessageDialog(this, "모든 값을 입력하세요.", "경고", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AddressVo onePerson = new AddressVo();
            onePerson.setName(name);
            onePerson.setAge(age);
            onePerson.setGender(gender);
            onePerson.setPhone(phone);
            onePerson.setAddress(address);

            addressBookInterface.insertAddress(onePerson); // DB에 데이터 삽입

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

	// 데이터 변경
	private void updateData() {
	    try {
	        int selectedRow = jTable.getSelectedRow();
	        if (selectedRow < 0) {
	            JOptionPane.showMessageDialog(this, "변경할 행을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        // 필터링된 리스트에서 선택된 데이터를 가져옴
	        AddressVo selectedPerson = currentFilteredList.get(selectedRow); 

	        // 변경할 값 세팅
	        selectedPerson.setName(jTextField_Name.getText()); 
	        selectedPerson.setAge((Integer) jSpinner.getValue()); 

	        if (jRadioButton_Male.isSelected()) {
	            selectedPerson.setGender(Gender.MAN); 
	        } else if (jRadioButton_Female.isSelected()) {
	            selectedPerson.setGender(Gender.WOMAN); 
	        } else {
	            JOptionPane.showMessageDialog(this, "성별을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        selectedPerson.setPhone(jTextField_Phone.getText()); 
	        selectedPerson.setAddress(jTextField_Address.getText());

	        // 변경된 데이터를 DB에 업데이트
	        addressBookInterface.updateAddress(selectedPerson);

	        refreshFilteredTable();
	    } 
	    catch (Exception ex) {
	        LOGGER.error(ex.getMessage(), ex);
	    }
	}

	// 데이터 삭제
	private void deleteData() {
	    try {
	        int selectedRow = jTable.getSelectedRow();
	        if (selectedRow < 0) {
	            JOptionPane.showMessageDialog(this, "삭제할 행을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        AddressVo selectedPerson = currentFilteredList.get(selectedRow);
	        addressBookInterface.deleteAddress(selectedPerson); // DB에서 삭제

	        refreshFilteredTable(); // 필터링된 상태 유지
	    } catch (Exception ex) {
	        LOGGER.error(ex.getMessage(), ex);
	    }
	}

	// 필터 상태에 따라 테이블을 올바르게 갱신
	private void refreshFilteredTable() {
	    AddressVo paramVo = new AddressVo();

	    // currentInitial이 비어있으면(루트 노드 "주소록" 상태) 전체 데이터 조회
	    if (currentInitial.isEmpty()) {
	        // 전체 조회
	        // searchType / searchWord를 null로 세팅 → <if>문에 걸리지 않음
	        paramVo.setSearchType(null);
	        paramVo.setSearchWord(null);
	    }
	    else {
	        // 초성 필터
	        // 예) ㄱ, ㄴ 등 → searchType="LETTER", searchWord="ㄱ" 등
	        paramVo.setSearchType(SearchType.LETTER);
	        paramVo.setSearchWord(currentInitial);
	    }

	    // 최종적으로 selectAddressList(paramVo) 호출
	    List<AddressVo> filteredList = addressBookInterface.selectAddressList(paramVo);
	    updateTable(filteredList);
	}

	// 추가 버튼 눌렀을 때 수행
	void jButton_Add_ActionListener(ActionEvent e) {
		insertData();
		refreshFilteredTable();
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
		refreshFilteredTable();
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
	    refreshFilteredTable();
	}
	
	void jTable_MouseListener(MouseEvent e) {
		
		try {
	        int newSelectedRow = jTable.getSelectedRow();
	        if (newSelectedRow < 0) {
	            return;
	        }
	        
	        // 필터링된 데이터에서 선택된 행의 데이터를 가져옴
	        AddressVo selectedPerson = currentFilteredList.get(newSelectedRow);

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
	        
//	        LOGGER.debug(personList.get(newSelectedRow));


	        jTextField_Name.setText(selectedPerson.getName());
	        jSpinner.setValue(selectedPerson.getAge());

	        if (selectedPerson.getGender() == Gender.MAN) {
	            jRadioButton_Male.setSelected(true);
	        }
	        else {
	            jRadioButton_Female.setSelected(true);
	        }

	        jTextField_Phone.setText(selectedPerson.getPhone());
	        jTextField_Address.setText(selectedPerson.getAddress());

	        LOGGER.debug(newSelectedRow);
	    }
	    catch (Exception ex) {
	        LOGGER.error(ex.getMessage(), ex);
	    }	
	} 
	
	void jTree_SelectionListener(TreeSelectionEvent e) {
	    try {
	        String selectedNode = e.getPath().getLastPathComponent().toString();
	        String rootNode = "주소록";

	        if (selectedNode.equals(rootNode)) {
	            currentInitial = ""; 
	            LOGGER.info("루트노드다 ~~~~~~~~~~~~~~~~");
	        }
	        else {
	            currentInitial = selectedNode;
	            LOGGER.info("선택된 노드:" + currentInitial + "이다 ~~~~~~~~~~~~~~~~");
	        }

	        refreshFilteredTable(); // 여기서 하나의 select로 처리
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

class AddressBookLayoutPanel_jTree_SelectionListener implements TreeSelectionListener {
    private AddressBookLayoutPanel adaptee;

    public AddressBookLayoutPanel_jTree_SelectionListener(AddressBookLayoutPanel adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        adaptee.jTree_SelectionListener(e);
    }
}


