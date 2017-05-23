package quiz;

import java.text.NumberFormat;

public class Test {
	
	Question[] questions;
	boolean[] correctQuestions;
	int qNum;
	
	Test(Question[] q){
		questions = q;
		correctQuestions = new boolean[questions.length];
		qNum = 0;
	}
	
	public String score(){
		double pts = 0;
		for (int i = 0; i < correctQuestions.length; i++) {
			if(correctQuestions[i]){
				pts += questions[i].pointValue;
			}
		}
		NumberFormat nf = NumberFormat.getPercentInstance();
		return nf.format((pts/(double)Question.totalPoints));
	}
	
	public void isCorrect(String ans){
		if(ans == null || ans.isEmpty()){
			System.out.println("Put in an answer.");
		}else if(ans.equals(questions[qNum].getCorrectAnswer())){
			correctQuestions[qNum] = true;
			System.out.println("You got the right answer.");
			qNum++;
		}else{
			correctQuestions[qNum] = false;
			System.out.println("You got the wrong answer.");
			qNum++;
		}
	}
	
	public boolean checkIfAllQAreAnswered(){
		for(int i = 0; i < questions.length; i++){
			if(questions[i].lastAnswer.isEmpty()){
				qNum = i;
				return false;
			}
		}
		return true;
	}
}
