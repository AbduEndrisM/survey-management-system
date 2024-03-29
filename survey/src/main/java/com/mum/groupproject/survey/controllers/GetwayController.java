package com.mum.groupproject.survey.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mum.groupproject.survey.domain.Admin;
import com.mum.groupproject.survey.domain.Answer;
import com.mum.groupproject.survey.domain.Choice;
import com.mum.groupproject.survey.domain.Question;
import com.mum.groupproject.survey.domain.QuestionType;
import com.mum.groupproject.survey.domain.Survey;
import com.mum.groupproject.survey.iservice.IChoice;
import com.mum.groupproject.survey.iservice.IQuestion;
import com.mum.groupproject.survey.iservice.IQuestionType;
import com.mum.groupproject.survey.iservice.IRate;
import com.mum.groupproject.survey.iservice.ISurvey;
import com.mum.groupproject.survey.utility.Messages;
import com.mum.groupproject.survey.utility.Test;
import com.mum.groupproject.survey.utility.ToResuse;

@Controller
@RequestMapping("admin")
public class GetwayController {

	@Autowired
	private ISurvey surveyService;

	@Autowired
	private IQuestion questionService;

	@Autowired
	private IQuestionType typeService;

	@Autowired
	private IChoice choiceService;
	
	@Autowired
	private IRate rateService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String adminAccess() {
		return "back-End/Admin/AdminLogin";
	}

	@RequestMapping(value = "/{option}", method = RequestMethod.GET)
	public String pageNavigation(@PathVariable("option") String option, ModelMap model,HttpSession session) {
		String sourceId = option.contains("_") ? option.split("_")[1] : "";

		if (option.equals("survey")) {
			List<Survey> list = surveyService.allSurveys();
			model.addAttribute("surveys", list);
			return "back-End/Admin/home";
		} else if (option.contains("questions")) {
			Survey survey = surveyService.findOne(sourceId);
			List<Question> questions = questionService.surveyQuestion(survey);
			session.setAttribute("surveyId", survey.getId());
			model.addAttribute("types", typeService.allTypes());
			model.addAttribute("surveyId", sourceId);
			model.addAttribute("questions", questions);
			return "back-End/Admin/questions";
		} else if (option.contains("choice")) {
			Question question = questionService.findOne(sourceId);
			List<Choice> list = choiceService.questionChoices(question);
			model.addAttribute("choices", list);
			model.addAttribute("question", question);
			return "back-End/Admin/choices";
		} else if (option.equals("questionTypes")) {
			List<QuestionType> types = typeService.allTypes();
			model.addAttribute("types", types);
			return "back-End/Admin/questionTypes";
		} else if (option.equals("home")) {
			List<Survey> list = surveyService.allSurveys();
			model.addAttribute("surveys", list);
			return "back-End/client/home";
		} else if (option.contains("surveyDetails")) {
			Survey survey = surveyService.findOne(sourceId);
			List<Question> list = questionService.surveyQuestion(survey);
			Map<Question, List<Choice>> map = new HashMap<>();
			model.addAttribute("survey", survey);
			for (Question q : list) {
				if (q.getQuestionType().getName().equals("MC")) {
					map.put(q, choiceService.questionChoices(q));
				} else {
					map.put(q, null);
				}
			}
			model.addAttribute("questions", map);
			return "back-End/client/surveyQuestions";
		} else if (option.contains("submissions")) {
			
			Survey survey = surveyService.findOne(sourceId);
			model.addAttribute("survey", survey);
			model.addAttribute("mce", questionService.questionByType("MC", survey));
			model.addAttribute("oe", questionService.questionByType("OE", survey));
			model.addAttribute("rates", rateService.rateQuestions(survey));
			return "back-End/Admin/submissions";
		}else if(option.equals("profile")){
			Admin admin = (Admin)session.getAttribute("admin");
			return "back-End/Admin/profile";
		}else {
			return "redirect:/";
		}
	}

	

}
