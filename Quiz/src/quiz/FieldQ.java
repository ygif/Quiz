package quiz;

public class FieldQ extends Question{
	
	String correctAnswer;

	FieldQ(String questionText, int num, String correctAnswer) {
		super(questionText, num);
		this.correctAnswer = correctAnswer;
	}
	
	FieldQ(String questionText, int num, String correctAnswer, int p) {
		super(questionText, num, p);
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
	 * This method returns an answer.
	 * @return An answer
	 */
	@Override
	public String getAnswer(int index) {
		return "";
	}
}
