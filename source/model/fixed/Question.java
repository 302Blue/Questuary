package model.fixed;

//import java.util.ArrayList;

public class Question {
	private String question;
	private String[] answers;
	private String correctAnswer;
	
	public Question(String question, String[] answers, String correctAnswer ) {
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}
	
	public static void main(String[] args) {
		
		//ArrayList<Questions> questions = new ArrayList<Questions>();
		
		//Questions question1 = new Questions("What is your name?", new String[]{"Bob", "Dick", "Harry", "Jane"}, "Bob");
		//Questions question2 = new Questions("What is your age?", new String[]{"Akash", "Andrew", "David", "Matt"}, "Matt");		
		
		
	}
	
	/**
	 * Getter for question
	 * 
	 * @return String- returns the question
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Getter for answers
	 * 
	 * @return String[]- returns the list of answers
	 */
	public String[] getAnswers() {
		return answers;
	}
	
	/**
	 * Getter for Correct answer
	 * 
	 * @return String- returns the Correct answer
	 */
	public String getCorrectAnswer() {
		return correctAnswer;
	}
}
