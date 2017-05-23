package quiz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

	/*
	 * @author FlightGuy
	 * @param answerPanel The panel used to display the buttons and fields required for the question type.
	 * @param questionText The text displayed that shows the question to be answered.
	 * @param input The field used to input answers for FieldQ.
	 * @param proceed A button used to tell the program to check if the inputed answer is correct.
	 */
	private static final long serialVersionUID = -5953309348378798904L;
	
	private JPanel contentPane;
	private int width = 450;
	private int height = 300;
	private JPanel answerPanel;
	
	Test test;
	JLabel questionNum;
	JTextArea questionText;
	JTextField input;
	JRadioButton[] rb;
	JButton proceed;
	JButton prev;
	JButton next;
	ButtonGroup group;
	
	private String setFile(){
		JFileChooser fc = new JFileChooser("bin");
		fc.showOpenDialog(null);
		return fc.getSelectedFile().getPath();
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		Handler hand = new Handler();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setBounds((int)(dim.getWidth()/2) - (width/2),
				(int)(dim.getHeight()/2) - (height/2), width, height);
		setResizable(false);
		setTitle("Test");
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 30, 5, 30));
		contentPane.setLayout(new BorderLayout(0, 10));
		
		JPanel infoPanel = new JPanel();
		test = new Test(QuestionUtil.genTestQuestionsFromTxtFile(setFile()));
		
		questionNum = new JLabel("Question #" + (test.qNum + 1));
		questionNum.setFocusable(false);
		questionText = new JTextArea(test.questions[test.qNum].text);
		questionText.setEditable(false);
		questionText.setFocusable(false);
		
		infoPanel.add(questionNum);
		infoPanel.add(questionText);
		contentPane.add(infoPanel, BorderLayout.NORTH);
		
		rb = new JRadioButton[5];
		rb[0] = new JRadioButton("A: " + test.questions[test.qNum].getAnswer(0));
		rb[1] = new JRadioButton("B: " + test.questions[test.qNum].getAnswer(1));
		rb[2] = new JRadioButton("C: " + test.questions[test.qNum].getAnswer(2));
		rb[3] = new JRadioButton("D: " + test.questions[test.qNum].getAnswer(3));
		rb[4] = new JRadioButton("E: " + test.questions[test.qNum].getAnswer(4));
		
		input = new JTextField(10);
		input.addActionListener(hand);
		input.addKeyListener(hand);
		
		proceed = new JButton("Continue");
		
		prev = new JButton("Previous");
		
		next = new JButton("Next");
		
		for (int i = 0; i < rb.length; i++) {
			rb[i].addActionListener(hand);
			rb[i].addFocusListener(hand);
			rb[i].addKeyListener(hand);
		}
		proceed.addActionListener(hand);
		proceed.addKeyListener(hand);
		
		prev.addActionListener(hand);
		
		next.addActionListener(hand);

		group = new ButtonGroup();
		for (int i = 0; i < rb.length; i++) {
			group.add(rb[i]);
		}
		
		answerPanel =  new JPanel();
		setAnswerPanel();
		
		JPanel btns = new JPanel();
		btns.add(prev);
		btns.add(proceed);
		btns.add(next);
		
		if(test.questions[test.qNum] instanceof MultiChoiceQ){
			rb[0].setSelected(true);
			rb[0].requestFocusInWindow();
		}
		
		contentPane.add(btns, BorderLayout.SOUTH);
		contentPane.add(answerPanel, BorderLayout.CENTER);

		setContentPane(contentPane);
	}
	
	/**
	 * This method is the program's main loop where it cycles through each 
	 * question in the questions array and calculates the user's grade once
	 * the last question is answered.
	 */
	void update(){//update GUI to next Question
		if(test.qNum < test.questions.length && test.qNum >= 0){
			questionNum.setText("Question #" + (test.qNum + 1));
			questionText.setText(test.questions[test.qNum].text);
			
			if(test.questions[test.qNum] instanceof MultiChoiceQ){
				answerPanel.removeAll();
				rb[0].setText("A: " + test.questions[test.qNum].getAnswer(0));
				rb[1].setText("B: " + test.questions[test.qNum].getAnswer(1));
				rb[2].setText("C: " + test.questions[test.qNum].getAnswer(2));
				rb[3].setText("D: " + test.questions[test.qNum].getAnswer(3));
				rb[4].setText("E: " + test.questions[test.qNum].getAnswer(4));
				setAnswerPanel();
				group.clearSelection();
			}else{
				input.setText("");
				answerPanel.removeAll();
				setAnswerPanel();
			}
			
		}else if(test.qNum == test.questions.length && test.checkIfAllQAreAnswered() == false){
			int x = JOptionPane.showInternalOptionDialog(contentPane, "You have not answered every question.\nWould you still like to end the test?", "End the test",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if(x == 0){
				JOptionPane.showInternalMessageDialog(contentPane, "You have finished the test!\nYour grade is: "
						+ test.score());
				System.exit(0);
			}else{
				update();
			}
		}else if(test.qNum == test.questions.length){
			JOptionPane.showInternalMessageDialog(contentPane, "You have finished the test!\nYour grade is: "
								+ test.score());
			System.exit(0);
		}
	}
	
	/**
	 * This method sets the correct answer panel for the next question.
	 */
	private void setAnswerPanel(){
		if(test.questions[test.qNum] instanceof MultiChoiceQ){
			answerPanel.setLayout(new GridLayout(5, 2));
			for (int i = 0; i < rb.length; i++) {
				answerPanel.add(rb[i]);
			}
			adjustButtons();
		}else{
			answerPanel.setLayout(new FlowLayout());
			answerPanel.add(input);
			input.requestFocusInWindow();
		}
		answerPanel.repaint();
	}
	
	/**
	 * This method checks whether a button in a MultioChoiceQ question
	 * provides an answer and removes it if it does not.
	 */
	private void adjustButtons() {
		for (int i = 0; i < rb.length; i++) {
			if(rb[i].getText().length() == 3) {
				answerPanel.remove(rb[i]);
			}
		}
		answerPanel.repaint();
	}
	
	/**
	 * This class handles all user inputs using
	 * the ActionListener and KeyListner intefaces.
	 */
	class Handler implements ActionListener, KeyListener , FocusListener{
		
		/**
		 * @see #temp
		 * @param temp This variable keeps track of the input answer
		 */
		String temp = "";
		
		public void selectPreviousAnswer(){
			if(test.questions[test.qNum] instanceof MultiChoiceQ){//Integer.parseInt(test.questions[test.qNum].lastAnswer
				int b = (test.questions[test.qNum].lastAnswer.isEmpty()) ? 0 : Character.getNumericValue(test.questions[test.qNum].lastAnswer.charAt(0)) - 10;
				for (int i = 0; i < rb.length; i++) {
					if(i == b){
						rb[i].setSelected(true);
						rb[i].requestFocusInWindow();
					}
				}
			}else if(test.questions[test.qNum] instanceof FieldQ){
				input.setText(test.questions[test.qNum].lastAnswer);
			}
		}
		
		/**
		 * This method is used to handle inputs from MultiChoiceQ and to tell the program to check 
		 * if the answer is correct by pressing the "continue" button.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == rb[0]){
				temp = "A";
				test.questions[test.qNum].setSelectedAnswer("A");
			}else if(e.getSource() == rb[1]){
				temp = "B";
				test.questions[test.qNum].setSelectedAnswer("B");
			}else if(e.getSource() == rb[2]){
				temp = "C";
				test.questions[test.qNum].setSelectedAnswer("C");
			}else if(e.getSource() == rb[3]){
				temp = "D";
				test.questions[test.qNum].setSelectedAnswer("D");
			}else if(e.getSource() == rb[4]){				
				temp = "E";
				test.questions[test.qNum].setSelectedAnswer("E");
			}else if(e.getActionCommand().equals("Previous") && test.qNum > 0){
				test.qNum--;
				update();
				selectPreviousAnswer();
			}else if(e.getActionCommand().equals("Next") && test.qNum < test.questions.length - 1){
				test.qNum++;
				update();
				selectPreviousAnswer();
			}else if(e.getActionCommand().equals("Continue")){
				if(test.questions[test.qNum] instanceof FieldQ){
					temp = input.getText();
				}
				test.isCorrect(temp);
				update();
				selectPreviousAnswer();
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {
			
		}
		
		/**
		 * This method is used as an alternate way to tell the program 
		 * to check if the input answer is correct.
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			if(test.questions[test.qNum] instanceof FieldQ){
				temp = input.getText();
				test.questions[test.qNum].setSelectedAnswer(temp);
			}else if(e.getSource() == rb[0]){
				temp = "A";
				test.questions[test.qNum].setSelectedAnswer("A");
			}else if(e.getSource() == rb[1]){
				temp = "B";
				test.questions[test.qNum].setSelectedAnswer("B");
			}else if(e.getSource() == rb[2]){
				temp = "C";
				test.questions[test.qNum].setSelectedAnswer("C");
			}else if(e.getSource() == rb[3]){
				temp = "D";
				test.questions[test.qNum].setSelectedAnswer("D");
			}else if(e.getSource() == rb[4]){				
				temp = "E";
				test.questions[test.qNum].setSelectedAnswer("E");
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				test.isCorrect(temp);
				update();
				selectPreviousAnswer();
			}else if(e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_LEFT && test.qNum > 0){
				test.qNum--;
				update();
				selectPreviousAnswer();
			}else if(e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_RIGHT){
				test.qNum++;
				update();
				selectPreviousAnswer();
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void focusGained(FocusEvent e) {
			if(test.questions[test.qNum] instanceof MultiChoiceQ){
				if(e.getSource() == rb[0]){
					test.questions[test.qNum].setSelectedAnswer(Integer.toString(0));
				}else if(e.getSource() == rb[1]){
					test.questions[test.qNum].setSelectedAnswer(Integer.toString(1));
				}else if(e.getSource() == rb[2]){
					test.questions[test.qNum].setSelectedAnswer(Integer.toString(2));
				}else if(e.getSource() == rb[3]){
					test.questions[test.qNum].setSelectedAnswer(Integer.toString(3));
				}else if(e.getSource() == rb[4]){				
					test.questions[test.qNum].setSelectedAnswer(Integer.toString(4));
				}
				selectPreviousAnswer();
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
