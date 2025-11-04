package com.quizsite.quiz;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Csv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String name;
    private String studentID;
    private String subject;
    private int score;
    private double percent;
    private String grade;

    public Csv() {}


    public Long getId() { 
    	return id; }
    
    public void setId(Long id) { 
    	this.id = id; }

    public String getName() { 
    	return name; }
    
    public void setName(String name) { 
    	this.name = name; }

    public String getStudentID() {
    	return studentID; }
    
    public void setStudentID(String studentID) { 
    	this.studentID = studentID; }

    public String getSubject() { 
    	return subject; }
    
    public void setSubject(String subject) {
    	this.subject = subject; }

    public int getScore() { 
    	return score; }
    
    public void setScore(int score) {
    	this.score = score; }

    public double getPercent() { 
    	return percent; }
    
    public void setPercent(double percent) {
    	this.percent = percent; }

    public String getGrade() { 
    	return grade; }
    
    public void setGrade(String grade) { 
    	this.grade = grade; }
}
