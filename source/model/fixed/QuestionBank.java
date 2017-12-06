package model.fixed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 *         Creates an instance of a question
 */
public class QuestionBank {

	// *************************************************
	// Fields

	ArrayList<Question> questions = new ArrayList<Question>();

	// *************************************************
	// Constructor
	
	/**
	 * Constructor for QuestionBank object
	 */
	public QuestionBank() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("Questions.txt");
			br = new BufferedReader(fr);
			String text;
			while ((text = br.readLine()) != null) {
				String question = text;
				String a1 = br.readLine();
				String a2 = br.readLine();
				String a3 = br.readLine();
				String cAns = br.readLine();
				String[] answers = { a1, a2, a3 };
				String reason = br.readLine();
				questions.add(new Question(question, answers, cAns, reason));
			}
			
		} catch (IOException e) {
			System.out.println("Error with file reading");
			e.printStackTrace();
			
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				System.out.println("Error in closing bufferedreader or filereader");
				e.printStackTrace();
			}
		}
	}
	

	// *************************************************
	// Methods

	/**
	 * Randomly picks a question from the ArrayList of questions
	 * 
	 * @param num
	 *            - Index in questions array to return
	 * 
	 * @return Question - Question object to be used
	 */
	public Question pickQuestion(int num) {
		int size = questions.size();
		Random r = new Random(System.nanoTime());
		int index = r.nextInt(num);
		return questions.get((index) % size);
	}
}
