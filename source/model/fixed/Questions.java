package model.fixed;

import java.util.ArrayList;

public class Questions {
	
	private static ArrayList<Question> questions = new ArrayList<Question>();
	
	public Questions() {
		Question question1 = new Question("What is your name?", new String[]{"Bob", "Dick", "Harry", "Jane"}, "Bob");
		questions.add(question1);
	}
	
	public Question pickQuestion(int numCollectible) {
		int questionIndex = numCollectible/3;
		return questions.get(questionIndex);
	}

}
