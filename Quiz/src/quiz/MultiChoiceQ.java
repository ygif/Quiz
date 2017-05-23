package quiz;

/**
 * This class is used to represent a multple choice question
 * with five options.
 * 
 * @author FlightGuy
 */

public class MultiChoiceQ extends Question{
	
	String[] answers;
	String correctAnswer;
	int numOfOptions;
	
	/**
	 * The constructor creates an object of MultiChoiceQ with its text, number in test, answers to choose from, and which answer is correct.
	 * @param questionText  The text to be displayed for the question to be answered.
	 * @param num           The number the question is in the test.
	 * @param options       An array represent all of the possible answers to the question. 
	 * @param correctAnswer The array index in the options array that is the correct answer to the question.
	 */
	MultiChoiceQ(String questionText, int num, String[] options, String correctAnswer) {
		super(questionText, num);
		numOfOptions = options.length;
		answers = new String[options.length];
		for (int i = 0; i < options.length; i++) {
			answers[i] = options[i];
		}
		this.correctAnswer = correctAnswer;
	}
	
	MultiChoiceQ(String questionText, int num, String[] options, String correctAnswer, int p) {
		super(questionText, num, p);
		numOfOptions = options.length;
		answers = new String[options.length];
		for (int i = 0; i < options.length; i++) {
			answers[i] = options[i];
		}
		this.correctAnswer = correctAnswer;
	}
	
	/**
	 * This method returns the correct answer to the question.
	 * @return The question's correct answer
	 */
	@Override
	public String getCorrectAnswer(){
		return correctAnswer;
	}
	
	/**
	 * This method returns the answer at the specified index.
	 * @param index The index of the answer to be returned in the answers array.
	 * @return The answer at the specified index. 
	 */
	@Override
	public String getAnswer(int index){
		return (index >= answers.length) ? "" : answers[index];
	}
}
