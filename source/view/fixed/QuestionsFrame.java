package view.fixed;

import java.awt.Label;
import java.awt.TextField;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.*;

import model.fixed.Question;

public class QuestionsFrame {
	
	JFrame f;
	String[] answers;
	private static ArrayList<JRadioButton> options = new ArrayList<JRadioButton>();
	private static ListIterator<JRadioButton> iter;
	private static boolean upFlag;
	private static boolean downFlag;

	public QuestionsFrame(Question question) {
		
		answers = question.getAnswers();
		JFrame f = new JFrame(question.getQuestion());
		//f.add(new TextField(question));
		//f.setTitle(question);
		//JLabel l = new JLabel(question);
		//Label question2 = new Label("Question: " + question);
		
		JRadioButton a = new JRadioButton("A) " + answers[0]);
		JRadioButton b = new JRadioButton("B) " + answers[1]);
		JRadioButton c = new JRadioButton("C) " + answers[2]);
		JRadioButton d = new JRadioButton("D) " + answers[3]);
		
		a.setBounds(50, 50, 200, 30);
		b.setBounds(50, 100, 200, 30);
		c.setBounds(50, 150, 200, 30);
		d.setBounds(50, 200, 200, 30);
		ButtonGroup bg = new ButtonGroup();
		//f.add(l);
		bg.add(a);
		bg.add(b);
		bg.add(c);
		bg.add(d);
		f.add(a);
		f.add(b);
		f.add(c);
		f.add(d);
		//f.add(question2);
		f.setSize(600, 300);
		f.setLayout(null);
		f.setVisible(true);
		a.setSelected(true);
		options.add(a);
		options.add(b);
		options.add(c);
		options.add(d);
		iter = options.listIterator();
		//iter.next();
		upFlag = true;
		downFlag = true;
	}
	/*
	public static void main(String[] args) {
		
		//QuestionsFrame q = new QuestionsFrame("Who will people remember better 100 years from now and why do you think so?","Roger", "Lionel", "Iniesta", "Ronaldinho");
		
		q.down(); //a
		q.down(); //b
		q.up();	  //a
		q.down(); //b
		q.down(); //c
		q.down(); //should not work
		//q.down();
		q.up(); 	 //c
		q.up();  //b
		q.up();  //a 
	}
	*/
	public static void down() {
		if (downFlag) {
			iter.next();
		}
		if(iter.hasNext()) {
			JRadioButton j = iter.next();
			j.setSelected(true);
			System.out.println("Go down a button");
		}
		downFlag = false;
		upFlag = true;
	}
	
	public static void up() {
		if (upFlag) {
			iter.previous();
		}
		if(iter.hasPrevious()) {
			JRadioButton j = iter.previous();
			j.setSelected(true);
			System.out.println("Go up a button");
		}
		upFlag = false;
		downFlag = true;
	}
}