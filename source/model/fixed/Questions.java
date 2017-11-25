package model.fixed;

//import java.util.ArrayList;

public class Questions {
	private String question;
	private String[] answers;
	private String correctAnswer;
	
	public Questions(String question, String[] answers, String correctAnswer ) {
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	
	public static void main(String[] args) {
		
		//ArrayList<Questions> questions = new ArrayList<Questions>();
		
		Questions question1 = new Questions("What is your name?", new String[]{"Bob", "Dick", "Harry", "Jane"}, "Bob");
		Questions question2 = new Questions("What is your age?", new String[]{"Akash", "Andrew", "David", "Matt"}, "Matt");		
		
		
	}
}
