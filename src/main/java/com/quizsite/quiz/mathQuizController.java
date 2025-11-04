package com.quizsite.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import jakarta.servlet.http.HttpSession;


@Controller



public class mathQuizController {
	
	@Autowired
	private EmailSenderService senderService;
	
	 @Autowired
	    private CsvRepository csvRepository;
	
@GetMapping("/quiz/Math")



public String mathQuiz(Model model, HttpSession session) { 
	
	List<Question> questions = new ArrayList<>(); 
	

    for(int c=0; c<10; c++) { 

	
	Random r = new Random(); 
	
	int int1 = r.nextInt(10); 
	int int2 = r.nextInt(10); 
	
	String[] operators = {"+","-"}; 
	String oper = operators[r.nextInt(operators.length)]; 
	
	int answer = 0; 
	int temp; 
	
	
	if (oper.equals("+")) { 
	 answer = int1 + int2; }
	else if (oper.equals("-") && int1 > int2){ 
	 answer = int1 - int2; }
	else if (oper.equals("-") && int1 < int2){ 
	 temp = int1; 
	 int1 = int2; 
	 int2 = temp; 

	answer = int1 - int2; 
	} 
	
    String mathQ = int1 + " " + oper + " " + int2 + " = ?";
    questions.add(new Question(mathQ, answer));
    }
    System.out.println("Questions generated: " + questions.size()); 
    
    
    session.setAttribute("questions", questions); 
    model.addAttribute("questions", questions); 
   
	return "math-quiz"; 

}

@PostMapping("/quiz/math/submit")

public String gradeQuiz(@RequestParam("answers")List<Integer> answers, Model model, HttpSession session){ 

	@SuppressWarnings("unchecked")
	List<Question> questions = (List<Question>) session.getAttribute("questions");

	if (questions == null) {
	    return "redirect:/quiz/Math"; 
	}
	List<Question> wrongQuestions = new ArrayList<>();
	
	
	int score=0; 
	double percent=0; 
	String grade = null; 
	int total = questions.size();
	
	
	for(int i=0; i< answers.size(); i++) { 
		
		Integer studentAnswer = (i < answers.size() ? answers.get(i) : null);
		
		if (studentAnswer != null && questions.get(i).getAnswer() == studentAnswer) 
		{	
	        score++;
		} else {
			wrongQuestions.add(questions.get(i));
		}
	}
		   percent = ((double)score/10)*100;
     
     if(score > 8) { 
    	 grade = "優"; 
     }
     else if (score > 6) { 
    	 grade = "甲"; 
     }
     else if (score > 4) { 
    	 grade = "乙"; 
     }
     else { 
    	 grade ="丁";
     }
     
   
     String studentID = (String) session.getAttribute("studentID");
     String name = (String) session.getAttribute("name");
     String toEmail = (String) session.getAttribute("gmail");
   
     
     
   
 

   
   if(toEmail != null) {
       String subject = "數學測驗成績通知 - " + name;
       String body = "您好 " + name + "家長，\n\n" +
                     "您的孩子在數學測驗的成績如下：\n" +
                     "Score: " + score + "/" + total + "\n" +
                     "Percentage: " + String.format("%.2f", percent) + "%\n" +
                     "等級: " + grade + "\n\n" ;
       senderService.sendEmail(toEmail, subject, body);
   }
   
    Csv record = new Csv(); 
    record.setName(name); 
    record.setStudentID(studentID);
    record.setSubject("Math");
    record.setScore(score); 
    record.setPercent(percent);
    record.setGrade(grade); 
    csvRepository.save(record);
   
   model.addAttribute("score", score);
   model.addAttribute("wrongQuestions", wrongQuestions); 
   model.addAttribute("percent", percent); 
   model.addAttribute("grade", grade); 
   model.addAttribute("total", total);

	 
return "math-result"; 

}} 





