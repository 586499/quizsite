package com.quizsite.quiz;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.servlet.http.HttpSession;

@Controller


public class ChiQuizController {
	
    @Autowired
    private EmailSenderService senderService; 
    
    @Autowired
    private CsvRepository csvRepository;
    
	@GetMapping("/quiz/chinese")
    public String displayChiQuiz(Model model, HttpSession session) {
		
		List<String> word = new ArrayList<>(); 
		List<String> pronun = new ArrayList<>(); 
		
		String filePath = "uploads/vocabulary.csv"; 
		String line; 
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { br.readLine();
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
            	
                String[] columns = line.split(",");
                word.add(columns[0].trim()); 
                pronun.add(columns[1].trim()); 
            }
	}
		   } catch (Exception e) {
		        e.printStackTrace();
		    }
        
        Random r= new Random();
        
        List<ChiQuestion> quiz = new ArrayList<>(); 
        
        Set<String> usedWord = new HashSet<String>();
        
        for(int k=0; k<5; k++) {
        	
        	int r1; 
        	
       do { 
    	   r1 = r.nextInt(word.size());  } 
       
       while (usedWord.contains(word.get(r1))) ; 
       
        	usedWord.add(word.get(r1)); 
       
        String question = word.get(r1); 
        String correctAnswer = pronun.get(r1); 
        
        
        List<String> options = new ArrayList<>();
        options.add(correctAnswer); 
        
        while(options.size()<3) { 
        String opt = pronun.get(r.nextInt(pronun.size())); 
        
        	if(!opt.equals(correctAnswer)) {
        		options.add(opt); 
        	}
        }
        
     
        Collections.shuffle(options, r);
        
        quiz.add(new ChiQuestion(question, options, correctAnswer));
        
        }
        
        
        
        
        
       Set<String> usedPronun = new HashSet<String>();
        
        for(int j=0; j<5; j++) {
        	
        	int r2; 
        	
       do { 
    	   r2 = r.nextInt(pronun.size());  } 
       
       while (usedPronun.contains(pronun.get(r2))) ; 
       
        	usedPronun.add(pronun.get(r2)); 
       
        String question = pronun.get(r2); 
        String correctAnswer = word.get(r2); 
        
        
        List<String> options = new ArrayList<>();
        options.add(correctAnswer); 
        
        while(options.size()<3) { 
        String opt = word.get(r.nextInt(word.size())); 
        
        	if(!opt.equals(correctAnswer)) {
        		options.add(opt); 
        	}
        }
        
        Collections.shuffle(options, r);
        
        quiz.add(new ChiQuestion(question, options, correctAnswer)); 
        } 
	
        
        session.setAttribute("questions", quiz); 
        model.addAttribute("quiz" , quiz); 
        
    	return "chinese-quiz.html"; 
    }
		
		
	
	
	
	@PostMapping("/quiz/chinese/submit")

	public String gradeQuiz(@RequestParam Map<String, String> answers, Model model, HttpSession session) {

		@SuppressWarnings("unchecked")
		List<ChiQuestion> questions = (List<ChiQuestion>)  session.getAttribute("questions");
		
	
		if (questions == null) {
		    return "redirect:/quiz/chinese"; 
		}
		List<ChiQuestion> wrongQuestions = new ArrayList<>();
		
		
		int score=0; 
		double percent=0; 
		String grade = null; 
		int total = questions.size();
		
		for(int i=0; i< questions.size(); i++) { 
			
		String questionKey = "q" +i; 
		String studentAnswer = answers.get(questionKey); 
		

			
			
			if (studentAnswer != null && studentAnswer.equals(questions.get(i).getCorrectAnswer())) {
		
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
	       String subject = "中文測驗成績通知 - " + name;
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
	    record.setSubject("Chinese");
	    record.setScore(score); 
	    record.setPercent(percent);
	    record.setGrade(grade); 
	    csvRepository.save(record);

	   model.addAttribute("score", score);
	   model.addAttribute("wrongQuestions", wrongQuestions); 
	   model.addAttribute("percent", percent); 
	   model.addAttribute("grade", grade); 
	   model.addAttribute("total", total);
		
	
	 return "chinese-result"; 
		
	}}




		
	

