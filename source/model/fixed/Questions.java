package model.fixed;

import java.util.ArrayList;

public class Questions {

	// *************************************************
	// Fields

	private static ArrayList<Question> questions = new ArrayList<Question>();

	// *************************************************
	// Constructor

	public Questions() {
		Question question1 = new Question("What is your name?", new String[] { "Bob", "Dick", "Harry", "Jane" }, "Bob");
		questions.add(question1);
	}

	// *************************************************
	// Methods

	public Question pickQuestion(int numCollectible) {
		int questionIndex = numCollectible / 3;
		return questions.get(0);        //TODO: when there are more questions questionIndex is passed into questions.get(questionIndex)
		//TODO: this method will not work
	}

}
