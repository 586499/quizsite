package com.quizsite.quiz;

import java.util.List;

public class ChiQuestion {

		private String text; 
		private List<String> options; 
		private String correctAnswer; 
	
		
	public ChiQuestion(String text, List<String> options, String correctAnswer) {

		this.text = text; 
		this.options = options; 
		this.correctAnswer = correctAnswer; 


	}

	public String getText() { 
		return text; 
	}

	public List<String> getOptions(){ 
		return options; 
	}

	public String getCorrectAnswer() { 
		return correctAnswer; 
	}
		}


