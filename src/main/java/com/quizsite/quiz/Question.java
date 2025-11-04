package com.quizsite.quiz;

public class Question {

	private String text; 
	private int answer; 
	
public Question(String text, int answer) {

	this.text = text; 
	this.answer = answer; 
	
}

public String getText() { 
	return text; 
}
public int getAnswer() { 
	return answer; 
}
}
