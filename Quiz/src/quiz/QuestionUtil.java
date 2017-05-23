package quiz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionUtil {
	
	public static Question[] genTestQuestions(){
		int qNum = 1;
		Question[] questions = new Question[3];
		
		String[] a = new String[5];
		String[] b = new String[5];
		String[] c = new String[5];
		
		a[0] = "10";
		a[1] = "2";
		a[2] = "25";
		a[3] = "6";
		a[4] = "9";
		
		b[0] = "3";
		b[1] = "25";
		b[2] = "10";
		b[3] = "5";
		b[4] = "0";
		
		c[0] = "GTX 1060";
		c[1] = "GTX 970";
		c[2] = "GTX 980 Ti";
		c[3] = "GTX 960";
		c[4] = "GTX GTX 680";
		
		questions[0] = new FieldQ("What is 5 + 5?", qNum, "10");
		questions[1] = new MultiChoiceQ("What is 5 * 5?",qNum + 1, b, "B");
		questions[2] = new MultiChoiceQ("Which of these GPUs is faster than the Nvidia Titan Black?", qNum + 2, c, "C");
		return questions;
	}
	
	public static Question[] genTestQuestionsFromTxtFile(String path){
		ArrayList<Question> q = new ArrayList<Question>();
		
		Scanner scan = null;
		try {
			scan = new Scanner(new BufferedReader(new FileReader(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The file does not exist");
		} finally {
			int i = 1;
			while(scan.hasNextLine()) {
				String s = scan.nextLine();
				if(!s.startsWith("//") && !s.equals("")){
					String[] tks = s.split(";");
					if(tks[0].equals("FQ") && tks.length == 4){//where "FQ" is the type FieldQ
						q.add(new FieldQ(tks[1], i, tks[2], Integer.parseInt(tks[3])));
					} else if(tks[0].equals("FQ")) {
						q.add(new FieldQ(tks[1], i, tks[2]));
					} else if(tks[0].equals("MCQ") && tks.length == 5) {//This is MultiChoiceQ
						q.add(new MultiChoiceQ(tks[1], i, parse(tks[2]), tks[3].toUpperCase(), Integer.parseInt(tks[4])));
					} else if(tks[0].equals("MCQ")) {//This is MultiChoiceQ
						q.add(new MultiChoiceQ(tks[1], i, parse(tks[2]), tks[3].toUpperCase()));
					}
					i++;
				}
			}
			scan.close();
		}
		
		Question[] t = new Question[q.size()];
		for (int i = 0; i < q.size(); i++) {
			t[i] = q.get(i);
		}
		return t;											
	}
	
	private static String[] parse(String s){
		String[] sa = s.split("/");
		if(sa.length < 2){
			System.err.println("WARNING: Multi Choice Questions should have at least two options.");
		}
		return sa;
	}
}
