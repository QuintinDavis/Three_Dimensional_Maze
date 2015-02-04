package qdavis1.mine.minefront;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
A JFrame that provides JComboBoxes to set up a custom maze game.
 */
public class CustomMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	/**
	Initializes the settings for the ControlsInfo JFrame and content
	 */
	public CustomMenu(){
	setTitle("Custom Maze");
	setSize(new Dimension(400,200));
	setLayout(new GridBagLayout());
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	JButton mainMenu = new JButton("Main Menu");
	setBackground(Color.LIGHT_GRAY);
	mainMenu.setForeground(Color.BLUE.darker().darker());
	mainMenu.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            dispose();
            Launcher lan = new Launcher();
            lan.start();
        }
    });
	
	JPanel customize = new JPanel();
	customize.setLayout(new GridBagLayout());
	
	JTextArea hW = new JTextArea("Width & Height");
	JTextArea lB = new JTextArea("#Lives & #Bombs");
	JTextArea dT = new JTextArea("Density & Texture");
	hW.setEditable(false);
	lB.setEditable(false);
	dT.setEditable(false);
	hW.setBackground(null);
	lB.setBackground(null);
	dT.setBackground(null);
	
	Integer[] dataWidth = {20,40,80,100,140,180,200,240,360,500};
	Integer[] dataHeight = {20,40,80,100,140,180,200,240,360,500};
	Integer[] dataLives = {0,1,2,3,4,5,6,7,8};
	Integer[] dataBombsPerLife = {0,1,2,3,4,5,6,7,8};
	String[] dataWallDensity = {"Low","Medium","High"};
	String[] dataTexture = {"Fire & Ice","Nuclear Fallout"};
	final JComboBox width = new JComboBox(dataWidth);
	width.setSelectedIndex(2);
	final JComboBox height = new JComboBox(dataHeight);
	height.setSelectedIndex(3);
	final JComboBox lives = new JComboBox(dataLives);
	lives.setSelectedIndex(4);
	final JComboBox bombsPerLife = new JComboBox(dataBombsPerLife);
	bombsPerLife.setSelectedIndex(4);
	final JComboBox wallDensity = new JComboBox(dataWallDensity);
	wallDensity.setSelectedIndex(1);
	final JComboBox texture = new JComboBox(dataTexture);
	JButton play = new JButton("Play");
	GridBagConstraints hWConstraints = new GridBagConstraints();
	GridBagConstraints lBConstraints = new GridBagConstraints();
	GridBagConstraints dTConstraints = new GridBagConstraints();
	GridBagConstraints widthConstraints = new GridBagConstraints();
	GridBagConstraints heightConstraints = new GridBagConstraints();
	GridBagConstraints livesConstraints = new GridBagConstraints();
	GridBagConstraints bombsPerLifeConstraints = new GridBagConstraints();
	GridBagConstraints wallDensityConstraints = new GridBagConstraints();
	GridBagConstraints textureConstraints = new GridBagConstraints();
	GridBagConstraints playConstraints = new GridBagConstraints();
	GridBagConstraints customizeConstraints = new GridBagConstraints();
	GridBagConstraints mainMenuConstraints = new GridBagConstraints();
	hWConstraints.fill = GridBagConstraints.HORIZONTAL;
	hWConstraints.gridx = 0;
	hWConstraints.gridy = 0;
	lBConstraints.fill = GridBagConstraints.HORIZONTAL;
	lBConstraints.gridx = 0;
	lBConstraints.gridy = 1;
	dTConstraints.fill = GridBagConstraints.HORIZONTAL;
	dTConstraints.gridx = 0;
	dTConstraints.gridy = 2;
	widthConstraints.fill = GridBagConstraints.HORIZONTAL;
	widthConstraints.gridx = 1;
	widthConstraints.gridy = 0;
	heightConstraints.fill = GridBagConstraints.HORIZONTAL;
	heightConstraints.gridx = 2;
	heightConstraints.gridy = 0;
	livesConstraints.fill = GridBagConstraints.HORIZONTAL;
	livesConstraints.gridx = 1;
	livesConstraints.gridy = 1;
	bombsPerLifeConstraints.fill = GridBagConstraints.HORIZONTAL;
	bombsPerLifeConstraints.gridx = 2;
	bombsPerLifeConstraints.gridy = 1;
	wallDensityConstraints.fill = GridBagConstraints.HORIZONTAL;
	wallDensityConstraints.gridx = 1;
	wallDensityConstraints.gridy = 2;
	textureConstraints.fill = GridBagConstraints.HORIZONTAL;
	textureConstraints.gridx = 2;
	textureConstraints.gridy = 2;
	playConstraints.fill = GridBagConstraints.HORIZONTAL;
	playConstraints.gridx = 0;
	playConstraints.gridy = 0;
	customizeConstraints.fill = GridBagConstraints.HORIZONTAL;
	customizeConstraints.gridx = 0;
	customizeConstraints.gridy = 1;
	mainMenuConstraints.fill = GridBagConstraints.HORIZONTAL;
	mainMenuConstraints.gridx = 0;
	mainMenuConstraints.gridy = 2;
	customize.add(hW, hWConstraints);
	customize.add(lB, lBConstraints);
	customize.add(dT, dTConstraints);
	customize.add(width, widthConstraints);
	customize.add(height, heightConstraints);
	customize.add(lives, livesConstraints);
	customize.add(bombsPerLife, bombsPerLifeConstraints);
	customize.add(wallDensity, wallDensityConstraints);
	customize.add(texture, textureConstraints);
	
	play.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            dispose();
            boolean temp = true;
            if(texture.getSelectedItem()=="Fire & Ice"){
            	temp = false;
            }
            int temp1= (Integer)(width.getSelectedItem());
            int temp2= (Integer)(height.getSelectedItem());
            int temp3= (Integer)(lives.getSelectedItem());
            int temp4= (Integer)(bombsPerLife.getSelectedItem());
            int temp5= 2;
            if(wallDensity.getSelectedItem()=="Medium"){
            	temp5=5;
            }else if(wallDensity.getSelectedItem()=="High"){
            	temp5=8;
            }
			new RunGame(5, temp, temp1, temp2, temp3, temp4, temp5);
        }
    });
	play.setForeground(Color.BLUE.darker().darker());
	
	add(play, playConstraints);
	add(customize, customizeConstraints);
	add(mainMenu, mainMenuConstraints);
	setLocationRelativeTo(null);
	setResizable(false);
	try {
		Robot robot = new Robot();

		robot.mouseMove(this.getLocation().x+this.getWidth()/2, this.getLocation().y+this.getHeight()-10);
	} catch (AWTException e1) {
		e1.printStackTrace();
	}
	setVisible(true);

	}
}