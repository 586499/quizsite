package com.quizsite.quiz;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Controller

public class teacherController {
	
	 @Autowired
	    private CsvRepository csvRepository;

	    public List<Csv> getAllCsv() {
	        return csvRepository.findAll();
	    }
	    
	    @Autowired
	    private CsvGeneratorUtil csvGeneratorUtil;
	 

		  @GetMapping("/teacher")
	    public String displayUpload(){ 
	        return "teacher-file"; 
	    }

	    @PostMapping("/teacher/student")
	    public String uploadStudentInfo(@RequestParam("studentInfoFile") MultipartFile file) throws IOException {
	        saveFile(file, "studentInfo.csv");
	        return "redirect:/teacher"; 
	    }

	    @PostMapping("/teacher/vocab")
	    public String uploadVocab(@RequestParam("vocab") MultipartFile file) throws IOException {
	        saveFile(file, "vocabulary.csv");
	        return "redirect:/teacher"; 
	    }

	    
	    @GetMapping("/teacher/download")
	    public ResponseEntity<byte[]> generateCsvFile() {
	        List<Csv> csvQuiz = csvRepository.findAll();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", "quizResult.csv");

	        String csvData = "\uFEFF" + csvGeneratorUtil.generateCsv(csvQuiz); 
	        byte[] csvBytes = csvData.getBytes(StandardCharsets.UTF_8); //Friend
	        
	      

	        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
	    }
	
	    
	    
	    
	    
	    private void saveFile(MultipartFile file, String fileName) throws IOException {
	        String projectPath = new File("").getAbsolutePath();
	        File uploadsPath = new File(projectPath, "uploads");

	        if (!uploadsPath.exists()) {
	            uploadsPath.mkdir();
	        }

	        File dest = new File(uploadsPath, fileName);
	        file.transferTo(dest);
	    }}
	    
	    
	    
	
		
