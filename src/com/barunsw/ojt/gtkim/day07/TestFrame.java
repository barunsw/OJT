package com.barunsw.ojt.gtkim.day07;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestFrame extends JFrame {
		static final Logger LOGGER = LogManager.getLogger(TestFrame.class);
		
		public static final String FLOW_LAYOUT    = "flowLayout";
		public static final String BORDER_LAYOUT  = "borderLayout";
		public static final String GRID_LAYOUT 	  = "gridLayout";
		public static final String CARD_LAYOUT 	  = "cardLayout";
		public static final String USER_LAYOUT 	  = "userLayout";
		public static final String GRIDBAG_LAYOUT = "gridbagLayout";
		
		public static final int WIDTH  = 720;
		public static final int HEIGHT = 640;
		
		private static final Font MENU_FONT = new Font("바탕", Font.BOLD, 16);
		
		private JMenuBar jMenuBar_Main         = new JMenuBar();
		private JMenu jMenu_one		           = null;
		private List<JMenuItem> jMenuItem_List = new ArrayList<JMenuItem>();
		
		private TestPanelLayout testPanel = new TestPanelLayout();
		
		private Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		private int xPos = (scrSize.width - TestFrame.WIDTH) / 2;
		private int yPos = (scrSize.height - TestFrame.HEIGHT) / 2;
		
		public TestFrame() {
			try {
				initComponent();
			}
			catch (Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
			}
		}
		
		private void initComponent() {
			this.setTitle("Swing Frame2");
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			// 메뉴바 초기화
			initMenuBar();
			this.setJMenuBar(jMenuBar_Main);
			
			// 메인 패널 부착!
			this.setContentPane(testPanel);

			this.setBounds(new Rectangle(xPos, yPos, TestFrame.WIDTH, TestFrame.HEIGHT));
			this.setVisible(true);
			this.addWindowListener(new TestFrame_this_WindowAdapter(this));
		}
		
		private void initMenuBar() {
			jMenu_one = new JMenu("레이아웃 설정");
			
			jMenuItem_List.add(new JMenuItem(TestFrame.FLOW_LAYOUT));
			jMenuItem_List.add(new JMenuItem(TestFrame.BORDER_LAYOUT));
			jMenuItem_List.add(new JMenuItem(TestFrame.GRID_LAYOUT));
			jMenuItem_List.add(new JMenuItem(TestFrame.CARD_LAYOUT));
			jMenuItem_List.add(new JMenuItem(TestFrame.USER_LAYOUT));
			jMenuItem_List.add(new JMenuItem(TestFrame.GRIDBAG_LAYOUT));
			
			jMenu_one.setFont(MENU_FONT);
			
			for(JMenuItem list : jMenuItem_List) {
				list.setFont(MENU_FONT);
				// 아이템마다 클릭시 수행할 액션 리스너 생성 
				list.addActionListener(new TestFrame_this_Menu_ActionListener(this));
				jMenu_one.addSeparator();
				jMenu_one.add(list);				
			}
			jMenuBar_Main.add(jMenu_one);
		}
		
		void panelLayoutChange(String layout) {
			this.getContentPane().removeAll();
			//패널을 교체
			this.setContentPane(new TestPanelLayout(layout));
			this.setVisible(true);
		}
		
		void windowClosing(WindowEvent e) {
			LOGGER.debug("종료 버튼이 선택되었습니다.");
			
			int result = JOptionPane.showConfirmDialog(this,
					"정말 종료 하시겠습니까?", "Exit", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		}
}

class TestFrame_this_WindowAdapter extends WindowAdapter {
	private TestFrame adaptee;
	
	public TestFrame_this_WindowAdapter(TestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		adaptee.windowClosing(e);
	}
}

class TestFrame_this_Menu_ActionListener implements ActionListener{
	private TestFrame adaptee;
	
	public TestFrame_this_Menu_ActionListener(TestFrame adaptee) {
		this.adaptee = adaptee;
	}
	
	@Override
	 public void actionPerformed(ActionEvent e) {
		TestFrame.LOGGER.debug("패널을 교체합니다.");
		JMenuItem item = (JMenuItem)e.getSource();
		String compare = item.getText();
		String panelLayout = "";
		if (compare.equals(TestFrame.FLOW_LAYOUT)) {
			panelLayout = TestFrame.FLOW_LAYOUT;
		}
		else if (compare.equals(TestFrame.BORDER_LAYOUT)) {
			panelLayout = TestFrame.BORDER_LAYOUT;
		}
		else if (compare.equals(TestFrame.GRID_LAYOUT)) {
			panelLayout = TestFrame.GRID_LAYOUT;
		}
		else if (compare.equals(TestFrame.USER_LAYOUT)) {
			panelLayout = TestFrame.USER_LAYOUT;
		}
		else if (compare.equals(TestFrame.CARD_LAYOUT)) {
			panelLayout = TestFrame.CARD_LAYOUT;
		}
		else if (compare.equals(TestFrame.GRIDBAG_LAYOUT)) {
			panelLayout = TestFrame.GRIDBAG_LAYOUT;
		}
		adaptee.panelLayoutChange(panelLayout);
	}
}
