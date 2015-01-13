/* Author: Daniel Wester
 * 
 * Date: 10/25/2012
 * 
 * Course: Comp 2247-01, Fall 2012
 * 
 * Assignment: PGM2
 * 
 * Description: The program will test out the Drop Out Stack class. The push
 * method overrides the LinearStack push method. To knock the bottom string off
 * the stack, I used another stack and pushed them all onto a temp stack. Then
 * I knocked the newest one off and put them back onto the main stack. In 
 * addition the Drop Out Stack has it's default is set to 10. There is another
 * constructor to set it to a user defined size. There is a setSize that is 
 * used by the constructor the stack, and a getSize that returns the max size
 * of the stack. The program also tests the pop, isEmpty, peek, and size, all
 * from the LinkedStack.
 */

package jss2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import jss2.exceptions.*;

public class DropOutTest extends JFrame {
	
	DropOutStack<String> stack = new DropOutStack<String>();
	
	JTextArea descriptionJTA, outputJTA;
	JTextField inputJTF, setSizeJTF, errorJTF;
	JLabel inputJL, outputJL, maxJL;
	JButton pushJB, popJB, setSizeJB, peekJB, isEmptyJB, sizeJB, exitJB;
	JPanel northPanel, northPanelBottom, centerPanel, southPanel, southPanelTop, southPanelBottom;
	
	public DropOutTest() {
		
		//Description Fields
		descriptionJTA = new JTextArea(" This program will test out the Drop Out Stack and all of the different methods.\n");
		descriptionJTA.setLineWrap(true);
		descriptionJTA.setEditable(false);
		descriptionJTA.setBackground(null);
		inputJL = new JLabel("This will put the string in the stack");
		outputJL = new JLabel("The contents of the stack");
		maxJL = new JLabel("Max Stack Size: " + stack.getMaxStackSize(), SwingConstants.CENTER);
		
		//Input and output Fields
		inputJTF = new JTextField();
		inputJTF.addActionListener(new PushButtonHandler());
		inputJTF.addFocusListener(new InputFocusHandler());
		errorJTF = new JTextField();
		errorJTF.setEditable(false);
		setSizeJTF = new JTextField("Input the stack size here.");
		setSizeJTF.addActionListener(new SetSizeButtonHandler());
		setSizeJTF.addFocusListener(new SetSizeFocusHandler());
		
		outputJTA = new JTextArea();
		outputJTA.setEditable(false);
		outputJTA.setLineWrap(true);
		outputJTA.setFont(new Font("Arial",Font.PLAIN,20));
		outputJTA.setBackground(Color.WHITE);
		
		//The buttons
		pushJB = new JButton ("Push");
		pushJB.addActionListener(new PushButtonHandler());
		popJB = new JButton ("Pop");
		popJB.addActionListener(new PopButtonHandler());
		setSizeJB = new JButton ("Set Stack Size");
		setSizeJB.addActionListener(new SetSizeButtonHandler());
		peekJB = new JButton ("Peek");
		peekJB.addActionListener(new PeekButtonHandler());
		isEmptyJB = new JButton ("isEmpty");
		isEmptyJB.addActionListener(new EmptyButtonHandler());
		sizeJB = new JButton ("Size");
		sizeJB.addActionListener(new SizeButtonHandler());
		exitJB = new JButton ("Exit");
		exitJB.addActionListener(new ExitButtonHandler());
		
		//Building the GUI
		northPanel = new JPanel(new GridLayout (2,1));
		northPanelBottom = new JPanel(new GridLayout (1,2));
		centerPanel = new JPanel(new GridLayout(1,1));
		southPanel = new JPanel(new GridLayout (2,1));
		southPanelTop = new JPanel(new GridLayout (3,1));
		southPanelBottom = new JPanel(new GridLayout (3,3));
		
		northPanel.add(descriptionJTA);
		northPanelBottom.add(outputJL);
		northPanelBottom.add(maxJL);
		northPanel.add(northPanelBottom);
		centerPanel.add(outputJTA);
		southPanelTop.add(errorJTF);
		southPanelTop.add(inputJL);
		southPanelTop.add(inputJTF);
		southPanelBottom.add(pushJB);
		southPanelBottom.add(popJB);
		southPanelBottom.add(setSizeJTF);
		southPanelBottom.add(peekJB);
		southPanelBottom.add(isEmptyJB);
		southPanelBottom.add(setSizeJB);
		southPanelBottom.add(sizeJB);
		southPanelBottom.add(exitJB);
		southPanel.add(southPanelTop);
		southPanel.add(southPanelBottom);
		
		//Display the GUI
		setLayout(new BorderLayout());
		setSize(450,450);
		setTitle("Drop Out Stack Test Program");
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
	}
	
	//Push Button will put the string on the top of the stack.
	private class PushButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			errorJTF.setText("");
			errorJTF.setForeground(Color.BLACK);
			
			if (inputJTF.getText().equals("")){
				return;
			}
			stack.push(inputJTF.getText());
			outputJTA.setText(stack.toString());
		}
	}
	
	//Pop Button will remove the top string on the stack.
	private class PopButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			errorJTF.setText("");
			errorJTF.setForeground(Color.BLACK);
			
			try {
				stack.pop();
			} catch (EmptyCollectionException ece){
				errorJTF.setForeground(Color.RED);
				errorJTF.setText(ece.getMessage());
			}
			outputJTA.setText(stack.toString());
		}
	}
	
	//Size Button Handler will set the max size of the stack.
	private class SetSizeButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			errorJTF.setText("");
			errorJTF.setForeground(Color.BLACK);
			
			if (setSizeJTF.getText().equals("")){
				errorJTF.setText("A number hasn't been entered to set the size");
				return;
			}
			try {
				stack = new DropOutStack<String>(Integer.parseInt(setSizeJTF.getText()));
				setSizeJTF.setText("");
			} catch (NumberFormatException nfe) {
				errorJTF.setForeground(Color.RED);
				errorJTF.setText(setSizeJTF.getText() + " is not a valid size. Only numbers greater than 0 work.");
				return;
			}
			
			maxJL.setText("Max Stack Size: " + stack.getMaxStackSize());
			outputJTA.setText(stack.toString());
		}
	}
	
	//Peek Button says what's at the top of the stack.
	private class PeekButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			errorJTF.setText("");
			errorJTF.setForeground(Color.BLACK);
			
			try {
				errorJTF.setText(stack.peek() + " is on the top of the stack.");
			} catch (EmptyCollectionException ece) {
				errorJTF.setForeground(Color.RED);
				errorJTF.setText(ece.getMessage());
			}
		}
	}
	
	//Empty Button tests the isEmpty whether the stack is empty or not.
	private class EmptyButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			errorJTF.setText("");
			errorJTF.setForeground(Color.BLACK);
			
			if (stack.isEmpty())
				errorJTF.setText("The Stack is empty.");
			else
				errorJTF.setText("The Stack is not empty.");
		}
	}
	
	//Size Button tells how many things are in the stack
	private class SizeButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			errorJTF.setText("");
			errorJTF.setForeground(Color.BLACK);
			
			errorJTF.setText("The stack currently has " + stack.size() + " things in it.");
		}
	}
	
	//Focus to select all the text in the input field
	private class InputFocusHandler extends FocusAdapter {
		public void focusGained(FocusEvent fe) {
			JTextField text = (JTextField) fe.getSource();
			text.selectAll();
		} public void focusLost(FocusEvent fe) {
		}
	}
	
	//Focus to respond to max size field
	private class SetSizeFocusHandler extends FocusAdapter {
		public void focusGained(FocusEvent fe) {
			JTextField text = (JTextField) fe.getSource();
			text.setText("");
		} public void focusLost(FocusEvent fe) {
			JTextField text = (JTextField) fe.getSource();
			text.setText("Input the stack size here.");
		}
	}
	
	//Exit Button
	private class ExitButtonHandler implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}
	
	//Main
	public static void main(String[] args){
		new DropOutTest();		
	}
}