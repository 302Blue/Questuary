package model.fixed;

public class Question {

	// *************************************************
	// Fields

	String name = "Estuary Question";
	String question;
	String[] answers;
	String correctAnswer;
	String reason;
	int index = 0;

	// *************************************************
	// Constructor

	/**
	 * Constructor for the question object
	 * 
	 * @param q
	 *            - the question statement
	 * @param ans
	 *            - the possible answers that will be incorrect
	 * @param cAns
	 *            - the answer that is the correct answer
	 * @param res
	 *            - reason why the correct answer is correct
	 */
	public Question(String q, String[] ans, String cAns, String res) {
		question = q;
		answers = ans;
		correctAnswer = cAns;
		reason = res;
	}
	

	// *************************************************
	// Methods

	/**
	 * Increments up in the possible answers
	 */
	public void up() {
		if (index > 0) {
			index--;
		}
		System.out.println("Index: " + index);
	}

	/**
	 * Increments down in the possible answers
	 */
	public void down() {
		if (index < 2) {
			index++;
		}
		System.out.println("Index: " + index);
	}

	/**
	 * Submits the answer
	 * 
	 * @return String - the correct answer
	 */
	public boolean right() {
		System.out.println("Chose Answer: " + answers[index]);
		System.out.println("Correct Answer: " + correctAnswer);
		System.out.println("Correct: " + answers[index].equalsIgnoreCase(correctAnswer));
		return (answers[index].equalsIgnoreCase(correctAnswer));
	}
	

	// *************************************************
	// Getters

	/**
	 * Index of the possible answer
	 * 
	 * @return int - the index in the possible answer array
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the name of the answer
	 * 
	 * @return String - name of the answer
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the question to be displayed
	 * 
	 * @return String - question being displayed
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Gets the possible answers array
	 * 
	 * @return String - array of strings of possible answers
	 */
	public String[] getAnswers() {
		return answers;
	}

	/**
	 * Gets the first option for the answer
	 * 
	 * @return String - first string in the answers array
	 */
	public String getA1() {
		return answers[0];
	}

	/**
	 * Gets the second option for the answer
	 * 
	 * @return String - second string in the answers array
	 */
	public String getA2() {
		return answers[1];
	}

	/**
	 * Get the third option for the answer
	 * 
	 * @return String - third string in the answers array
	 */
	public String getA3() {
		return answers[2];
	}

	/**
	 * Gets the correct answer for the question
	 * 
	 * @return String - the correct answer
	 */
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	/**
	 * Get the reason why the correct answer is correct
	 * 
	 * @return String - reasoning of the correct answer
	 */
	public String getReason() {
		return reason;
	}
}
