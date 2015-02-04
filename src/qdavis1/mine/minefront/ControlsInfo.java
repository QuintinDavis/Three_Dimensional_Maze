package qdavis1.mine.minefront;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
	ControlsInfo is a JFrame that displays help text and includes a JButton to return to the Main Menu
*/
public class ControlsInfo extends JFrame{
	private static final long serialVersionUID = 1L;
	/**
	Initializes the settings for the ControlsInfo JFrame and content
	 */
	public ControlsInfo(){
	setTitle("Controls/Information");
	setSize(new Dimension(400,500));
	setLayout(new BorderLayout());
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	JButton mainMenu = new JButton("Main Menu");
	mainMenu.setForeground(Color.BLUE.darker().darker());
	mainMenu.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            dispose();
            Launcher lan = new Launcher();
            lan.start();
        }
    });
	JTextArea information = new JTextArea();
	information.setEditable(false);
	String tab= "     ";// \t is too much
	String t = "MAIN MENU:\n"+
			tab+"Use arrow keys or click to make selections. "+
			"Change the texture by using the Fire and Ice portal or the Nuclear Fallout portal.\n\n"+
			"IN GAME:\n"+
			tab+"Transverse through the mazes as quick as possible to find the lost art. "
					+ "Heads towards the green areas on the maps to retrieve the lost art. "
					+ "The walls should be avoided because of their firery and radiant natures. "
					+ "The outer boundary walls can not be destroyed by bombs, but all other walls "
					+ "are destructable.\n"+
			tab+"W: Forward\n"+
			tab+"S: Reverse\n"+
			tab+"D: Right\n"+
			tab+"A: Left\n"+
			tab+"SpaceBar: Jump\n"+
			tab+"C: Prone\n"+
			tab+"B: Bomb\n"+
			tab+"M: Map\n"+
			tab+"H: Toggle HUD\n"+
			tab+"ESC: Main Menu";
	information.setFont(new Font("Veranda", 0, 15));
	information.setForeground(Color.BLUE.darker().darker());
	information.setBackground(Color.LIGHT_GRAY);
	information.setWrapStyleWord(true);
	information.setLineWrap(true);
	information.setText(t);
	add(information);
	add(mainMenu, BorderLayout.SOUTH);
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