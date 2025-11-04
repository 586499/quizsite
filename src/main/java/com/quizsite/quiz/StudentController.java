package com.quizsite.quiz;
import java.io.BufferedReader;
import jakarta.servlet.http.HttpSession; 

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.ui.Model;

@Controller

public class StudentController {

 @GetMapping("/student")

public String displayLogin(){ 
		return"student-login";
 }
 
	@PostMapping("/student/login")
public String startLogin(@RequestParam String studentID, Model model, HttpSession session ) {
		
		String projectPath= new File("").getAbsolutePath(); 
		File uploadsFolder = new File(projectPath + "/uploads"); 
	    File csvFile = new File(uploadsFolder, "studentInfo.csv");

	    if (!csvFile.exists()) {
	        model.addAttribute("error", "Student CSV file not found at: " + csvFile.getAbsolutePath());
	        return "student-login";
	    }
		
		boolean match = false; 
		String matchedName = ""; 
		String matchedGmail ="";
		
		try(BufferedReader br= new BufferedReader(new FileReader(csvFile))){ 
			String line; 
			while ((line = br.readLine())!=null) {
				
				String[] parts = line.split(",");
				if(parts.length >=3 ) {
					String csvID = parts[0].trim(); 
					String csvName = parts[1].trim();
					String csvGmail = parts[2].trim(); 
	 
				
				
				if(studentID.equals(csvID)){ 
					match = true; 
					matchedName = csvName; 
					matchedGmail= csvGmail;
					break; 
				}}
				
		    }}
		
		catch (IOException e) {
		        e.printStackTrace();
		        model.addAttribute("error", "Unable to read student info.");
		        return "student-login";
		    }
		    
		if(match){ 
		    model.addAttribute("studentID", studentID); 
		    model.addAttribute("name", matchedName); 
		    model.addAttribute("gmail", matchedGmail); 
		    session.setAttribute("studentID", studentID);
		    session.setAttribute("name", matchedName); 
		    session.setAttribute("gmail", matchedGmail); 
		    return "subject-selection";
		} else { 
		    model.addAttribute("error", "Invalid ID"); 
		    System.out.println("Set session gmail: " + matchedGmail);
		    return "student-login"; 
		}}
	
	@GetMapping("/quiz/math")
public String displayMathQuiz(){ 
		return"math-quiz";
	}

	
	@GetMapping("/student/login")
	public String studentLogin() {
		return "subject-selection"; 
	}


}
	

