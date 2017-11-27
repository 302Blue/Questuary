package model.fixed;

//import java.util.ArrayList;

public class Question {

	// *************************************************
	// Fields

	private String question;
	private String[] answers;
	private String correctAnswer;

	// *************************************************
	// Constructor

	public Question(String question, String[] answers, String correctAnswer) {
		this.question = question;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}

	// *************************************************
	// Methods

	public boolean checkAnswer(int index) {
		if (answers[index-1] == correctAnswer) {
			return true;
		} else {
			return false;
		}
	}

	// *************************************************
	// Getters

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
