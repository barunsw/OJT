package com.barunsw.ojt.jyb.day19;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FileExplorer extends JFrame {
	
	
    public FileExplorer() {
    	
        setTitle("File Explorer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        FileExplorerPanel fileExplorerPanel = new FileExplorerPanel();
        add(fileExplorerPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileExplorer explorer = new FileExplorer();
            explorer.setVisible(true);
        });
    }
}
