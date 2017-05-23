package quiz;

/**
 * This class serves as class that defines properties and
 * methods all types of questions should have.
 * 
 * @author FlightGuy
 *
 */

public abstract class Question {
	
	public String lastAnswer;
	public int num;
	public String text;
	int pointValue;
	static int totalPoints = 0;
	
	Question(String questionText, int num){
		lastAnswer = "";
		text = questionText;
		this.num = num;
		pointValue = 1;
		totalPoints++;
	}
	
	Question(String questionText, int num, int p){
		lastAnswer = "";
		text = questionText;
		this.num = num;
		pointValue = p;
		totalPoints += p;
	}
	
	public void setSelectedAnswer(String ans){
		lastAnswer = ans;
	}
	
	public abstract String getCorrectAnswer();
	
	public abstract String getAnswer(int index);
}
