package com.barunsw.ojt.mjg.day08;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddressBookLayoutPanel extends JPanel {
	
	private JScrollPane jScrollPane = new JScrollPane();        // 주소록 스크롤
    private JTable jTable = new JTable();                       // 주소록 테이블
    private JSpinner jSpinner = new JSpinner();                 // 나이 Spinner
    
    private JPanel jPanel_Form = new JPanel();                     // 입력 패널 따로
    private JTextField jTextField_Name = new JTextField(10);       // 이름 입력 필드
    private JTextField jTextField_Phone = new JTextField(10);      // 전화번호 입력 필드
    private JTextField jTextField_Address = new JTextField(15);    // 주소 입력 필드

    private JRadioButton jRadioButton_Male = new JRadioButton("남자");   // 남자 라디오 버튼
    private JRadioButton jRadioButton_Female = new JRadioButton("여자"); // 여자 라디오 버튼
    private ButtonGroup buttonGroup_Gender = new ButtonGroup();         // 성별 그룹
    private JPanel jPanel_Gender = new JPanel();                        // 라디오 버튼을 묶을 패널
    
    private JPanel jPanel_Command = new JPanel();               // 버튼 패널 따로
    private JButton jButton_Add = new JButton("추가");           // 추가 버튼
    private JButton jButton_Update = new JButton("변경");        // 변경 버튼
    private JButton jButton_Delete = new JButton("삭제");        // 삭제 버튼
    
    private JLabel jLabel_Name = new JLabel("이름");             // 이름 라벨
    private JLabel jLabel_Age = new JLabel("나이");              // 나이 라벨
    private JLabel jLabel_Gender = new JLabel("성별");           // 성별 라벨
    private JLabel jLabel_Phone = new JLabel("전화번호");         // 전화번호 라벨
    private JLabel jLabel_Address = new JLabel("주소");          // 주소 라벨 
    
    private JPanel jPanel_Result = new JPanel();                // 결과 패널(테이블)
    
    private JScrollPane jScrollPane_Table = new JScrollPane(jTable);   // 테이블 및 스크롤 페인
    
    private Dimension BUTTON_SIZE = new Dimension(60, 22);

    private GridBagLayout gridBagLayout = new GridBagLayout();  // GridBagLayout 사용

    public AddressBookLayoutPanel() {
        try {
            initComponent();
            initTable();
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
            new Insets(5, 5, 5, 5), 0, 0
        ));

        jPanel_Form.add(jTextField_Phone, new GridBagConstraints(
            1, 3, 5, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 5, 5), 0, 0
        ));

        // 주소 라벨 및 텍스트 필드
        jPanel_Form.add(jLabel_Address, new GridBagConstraints(
            0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            new Insets(5, 5, 5, 5), 0, 0
        ));

        jPanel_Form.add(jTextField_Address, new GridBagConstraints(
            1, 4, 5, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            new Insets(5, 0, 5, 5), 0, 0
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
            new Insets(0, 5, 5, 5), 0, 0
        ));

        jPanel_Command.add(jButton_Delete, new GridBagConstraints(
            2, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 5, 5), 0, 0
        ));
        
        // 삭제 버튼 기본 숨김
    	// jButton_Delete.setVisible(false);  
        
        // 삭제 버튼 이벤트
        jButton_Delete.addActionListener(new AddressBookLayoutPanel_jButton_Delete_ActionListener(this));
        
        // 테이블 스크롤
		this.add(jScrollPane_Table, new GridBagConstraints(
            0, 6, 7, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 5, 5), 0, 0
        ));

    }

    private void initTable() {
        // 테이블 컬럼명 정의
        String[] columns = {"이름", "나이", "성별", "전화번호", "주소"};

        // 테이블 모델 생성 (초기 데이터는 0개, 빈 데이터)
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        tableModel.setColumnIdentifiers(columns);

        // JTable에 테이블 모델 설정하여 테이블 구성
        jTable.setModel(tableModel);
        
        // 임의의 데이터 추가
        tableModel.addRow(new Object[]{"문종근", 26, "남자", "010-1111-1111", "경기도 성남시 분당구"});
        tableModel.addRow(new Object[]{"신사임당", 43, "남자", "010-2222-2222", "경기도 성남시 수정구"});
        tableModel.addRow(new Object[]{"조예빈", 25, "여자", "010-3333-3333", "서울특별시 강남구"});
        
        // 각 컬럼의 기본 가로 크기 설정
        jTable.getColumnModel().getColumn(0).setPreferredWidth(75);   // 이름
        jTable.getColumnModel().getColumn(1).setPreferredWidth(50);   // 나이
        jTable.getColumnModel().getColumn(2).setPreferredWidth(50);   // 성별
        jTable.getColumnModel().getColumn(3).setPreferredWidth(150);  // 전화번호
        jTable.getColumnModel().getColumn(4).setPreferredWidth(150);  // 주소
        
        // 마우스 리스너 추가 (테이블 셀 클릭 시 삭제 버튼이 표시)
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    // 삭제 버튼의 위치를 클릭된 행의 "주소" 셀 오른쪽 끝으로 이동
                    Rectangle rect = jTable.getCellRect(row, 4, true);
                    jButton_Delete.setBounds(rect.x + rect.width - 60, rect.y + jScrollPane.getY(), 60, rect.height);
                    jButton_Delete.setVisible(true);
                }
            }
        });
    }
    
    // 선택된 행 삭제
    void jButton_Delete_actionPerformed(ActionEvent e) {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.removeRow(selectedRow);      // 선택된 행 삭제
            jButton_Delete.setVisible(false);  // 삭제 후 버튼 숨김
        }
    }
}

class AddressBookLayoutPanel_jButton_Delete_ActionListener implements ActionListener {
	private AddressBookLayoutPanel adaptee;

	public AddressBookLayoutPanel_jButton_Delete_ActionListener(AddressBookLayoutPanel adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Delete_actionPerformed(e);
	}
}
