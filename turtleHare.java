package Home4;

//Name:Charles Corro
//SID: 105037613

import javax.swing.*;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class turtleHare extends JPanel{
	
	Timer hareT;
	Timer turtleT;
	
	int sleepCounter = 0;
	int sleepTime = 5;
	
	int place = 1;
	int win = 0;
	
	JLabel turtle;
	JLabel hare;
	
	boolean sleeping = false;
	
	ImageIcon HareMove;
	ImageIcon HareSleep;
	ImageIcon TurtleMove;
	
	JButton Start;
	JButton Restart;
	
	JTextField SleepingTime;
	JTextField hareSpeed;
	JTextField turtleSpeed;
	
	int HS = 500;
	int TS = 500;
	
	int xHare = 870;
	int yHare = 100;
	int xTurtle = 870;
	int yTurtle = 230;
	
	ArrayList<Point> sleep = new ArrayList<Point>();
	
	public turtleHare() {
		
		setBackground(Color.white);
		
		JLabel sleepingTime = new JLabel("Sleep Time (sec):");
		JLabel hspeed = new JLabel("Hare Speed:");
		JLabel tspeed = new JLabel("Turtle Speed:");
		
		SleepingTime = new JTextField("5");
		hareSpeed = new JTextField("100");
		turtleSpeed = new JTextField("100");
		
		HareMove = new ImageIcon(new ImageIcon("C:\\Users\\Charles Corro\\OneDrive\\Pictures\\bunny.gif").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
		HareSleep = new ImageIcon(new ImageIcon("C:\\Users\\Charles Corro\\OneDrive\\Pictures\\sleepingBunny.gif").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
		TurtleMove = new ImageIcon(new ImageIcon("C:\\Users\\Charles Corro\\OneDrive\\Pictures\\turtle.gif").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
		
		hare = new JLabel(HareMove);
		turtle = new JLabel(TurtleMove);
		
		add(hspeed);
		hareSpeed.setColumns(10);
		add(hareSpeed);
		
		add(tspeed);
		turtleSpeed.setColumns(10);
		add(turtleSpeed);
		
		add(sleepingTime);
		SleepingTime.setColumns(2);
		add(SleepingTime);
		
		add(hare);
		add(turtle);
		
		ML m = new ML();
		addMouseListener(m);
		
		
		hareT = new Timer(Integer.parseInt(hareSpeed.getText()), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(xHare > 0) {

					for(int x = 0;x<sleep.size();x++) {
						if((xHare <= (sleep.get(x).x)+25)&&(xHare >= (sleep.get(x).x)-25)) {
							sleep.remove(x);
							sleeping = true;
						}
					}
					
					if (sleeping) {
						hare.setIcon(HareSleep);
						hareT.stop();
					}
					else {
						xHare -= 25;
					}
				
				}
				if(xHare <= 0) {
					if(place++ == 1) {
						win = 1;
					}
					hareT.stop();
					if(win == 1) {
						JPanel p = new JPanel();
		    			JOptionPane.showMessageDialog(p,"The Hare has won!");
					}
				}
				repaint();
				
			}
		});
		
		turtleT = new Timer(Integer.parseInt(turtleSpeed.getText()), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(xTurtle > 0) {
					
					if(sleeping) {
						sleepCounter++;
						if(sleepCounter == sleepTime) {
							hare.setIcon(HareMove);
							hareT.start();
							sleeping = false;
						}
					}
					else {
						sleepCounter = 0;
					}
					
					xTurtle -= 25;
				}
				if(xTurtle <= 0) {
					if(place++ == 1) {
						win = 2;
					}
					turtleT.stop();
					if(win == 2) {
						JPanel p = new JPanel();
		    			JOptionPane.showMessageDialog(p,"The Turtle has won!");
					}
					else if(win == 0) {
						JPanel p = new JPanel();
		    			JOptionPane.showMessageDialog(p,"It was a tie!");
					}
				}
				repaint();
				
			}
		});
		
		Restart = new JButton("Reset!");
		Restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xHare = 870;
				xTurtle = 870;
				win = 0;
			}
		});
		
		Start = new JButton("Start!");
		Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sleepTime = Integer.parseInt(SleepingTime.getText());
				
				hareT.setDelay(Integer.parseInt(hareSpeed.getText()));
				turtleT.setDelay(Integer.parseInt(turtleSpeed.getText()));
				
				hareT.start();
				turtleT.start();
				
			}
		});
		add(Restart);
		add(Start);
	}
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Turtle & Hare");
		turtleHare panel = new turtleHare();
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		
		frame.setSize(1000, 600);
		frame.setVisible(true);

	}
	
	class ML extends MouseAdapter{
		public void mouseReleased(MouseEvent e) {
			if(sleep.size()< 2) {
				sleep.add(e.getPoint());
				repaint();
			}
			else if( sleep.size() >= 2) {
				sleep.remove(0);
				sleep.add(e.getPoint());
				repaint();
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		for(Point s: sleep) {
			g2.fillOval(s.x, s.y, 10, 10);;
		}
		
		g2.setStroke(new BasicStroke(5));
		
		g2.drawLine( 0, 90, 1000 , 90);
		
		g2.drawLine(  0, 215, 175, 215);
		g2.drawLine( 200, 215, 375,215);
		g2.drawLine( 400, 215, 575,215);
		g2.drawLine( 600, 215, 775,215);
		g2.drawLine( 800, 215, 1000,215);
		
		g2.drawLine(5, 90, 5, 340);
		
		g2.drawLine( 0, 340, 1000, 340);
		hare.setLocation(xHare,yHare);
		turtle.setLocation(xTurtle, yTurtle);
	}
}
