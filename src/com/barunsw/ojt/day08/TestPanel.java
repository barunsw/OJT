package com.barunsw.ojt.day08;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.barunsw.ojt.day07.AddressBookInterface;
import com.barunsw.ojt.day07.MemAddressBookImpl;

public class TestPanel extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPanel.class);

	private int columnIdx = 0;
	private final int TABLE_COLUMN_SEQ 		= columnIdx++; 
	private final int TABLE_COLUMN_NAME 	= columnIdx++; 
	private final int TABLE_COLUMN_AGE 		= columnIdx++; 
	private final int TABLE_COLUMN_ADDRESS 	= columnIdx++; 
	private final int TABLE_COLUMN_REMARKS 	= columnIdx++; 
	
	private GridBagLayout gridBagLayout = new GridBagLayout();
	
	private JScrollPane jScrollPane_Table = new JScrollPane();
	
	private JTable jTable_Result = new JTable();
	
	private CommonTableModel tableModel = new CommonTableModel();
	
	private AddressBookInterface addressBookIf = new MemAddressBookImpl();
	
	
	public TestPanel() {
		try {
			initComponent();
			initTable();
			initData();
		}
		catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	private void initComponent() throws Exception {
		this.setLayout(gridBagLayout);

		this.add(jScrollPane_Table, 
				new GridBagConstraints(0, 0, 1, 1,
						1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		
		jScrollPane_Table.getViewport().add(jTable_Result);
	}
	
	private void initTable() {
		Vector<String> columnData = new Vector<>();
		columnData.add("번호");
		columnData.add("이름");
		columnData.add("나이");
		columnData.add("주소");
		//columnData.add("비고");
		
		tableModel.setColumn(columnData);
		tableModel.setCellEditable(TABLE_COLUMN_SEQ);
//		tableModel.setCellEditable(TABLE_COLUMN_REMARKS);
//		
		jTable_Result.setModel(tableModel);
		jTable_Result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable_Result.getColumnModel().getColumn(TABLE_COLUMN_AGE)
			.setCellRenderer(new AgeCellRenderer());		
		jTable_Result.getColumnModel().getColumn(TABLE_COLUMN_SEQ)
			.setCellEditor(new NumberCellEditor());
		
		jTable_Result.setRowHeight(32);
			
		jTable_Result.addMouseListener(new TestPanel_jTable_Result_MouseAdapter(this));
	}
	
	private void initData() {
		Vector oneData = new Vector();
		oneData.add("1");
		oneData.add("홍길동");
		oneData.add(30);
		oneData.add("서울시");
		//oneData.add(Boolean.TRUE);
		
		Person onePerson = new Person();
		onePerson.setAge(1);
		onePerson.setName("홍길동");
		
		oneData.add(onePerson);
		
		tableModel.addData(oneData);

		oneData = new Vector();
		oneData.add("2");
		oneData.add("유관순");
		oneData.add(20);
		oneData.add("경기도");
		//oneData.add(Boolean.FALSE);

		onePerson = new Person();
		onePerson.setAge(1);
		onePerson.setName("홍길동");
		
		oneData.add(onePerson);
		
		tableModel.addData(oneData);	
		
		// tableModel의 변경을 알린다.
		tableModel.fireTableDataChanged();
	}
	
	void jTable_Result_mouseReleased(MouseEvent e) {
		int selectedRow = jTable_Result.getSelectedRow();
		if (selectedRow >= 0) {
			String name = (String)tableModel.getValueAt(selectedRow, TABLE_COLUMN_NAME);
			LOGGER.debug(String.format("--- selectedRow:%s, name:%s", selectedRow, name));

			Person onePerson = (Person)tableModel.getValueAt(selectedRow, TABLE_COLUMN_REMARKS);
			LOGGER.debug(String.format("--- selectedRow:%s, person:%s", selectedRow, onePerson));
		}
	}
}

class TestPanel_jTable_Result_MouseAdapter extends MouseAdapter {
	private TestPanel adaptee;
	
	public TestPanel_jTable_Result_MouseAdapter(TestPanel adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		adaptee.jTable_Result_mouseReleased(e);
	}
}