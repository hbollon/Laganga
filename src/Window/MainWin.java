package Window;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;

public class MainWin extends JFrame { 

private JPanel jContentPane = null;
private JPanel jInnerPanel = null;
private JTree jTree = null;

private JTree getJTree() {
	if (jTree == null) {
		jTree = new JTree();
	}
	return jTree;
}

public MainWin() 
{
	super();
	initialize();
	setTree();
}
 
public void initialize()
{
	
	this.setTitle("Laganga");
    this.setSize(1280, 720);
    this.setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());
    ImageIcon img = new ImageIcon("icon.png");
    this.setIconImage(img.getImage());
	this.setContentPane(getJContentPane());
	this.getContentPane().setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
}

public void setTree()
{
	jTree.setSize(300,200);
}
 
private JPanel getJContentPane() {
	if (jContentPane == null) {
		jContentPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jInnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jInnerPanel.add(getJTree());
		jContentPane.add(jInnerPanel, BorderLayout.SOUTH);
	}
	return jContentPane;
}
}