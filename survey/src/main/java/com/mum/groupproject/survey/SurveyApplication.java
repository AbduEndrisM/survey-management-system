package com.mum.groupproject.survey;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mum.groupproject.survey.domain.QuestionType;
import com.mum.groupproject.survey.iservice.IQuestion;
import com.mum.groupproject.survey.iservice.IQuestionType;
import com.mum.groupproject.survey.serviceimpl.ChoiceService;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaAuditing
public class SurveyApplication  extends SpringBootServletInitializer{
	
	@Autowired
	 private IQuestionType questionType;

	public static void main(String[] args) {
		SpringApplication.run(SurveyApplication.class, args);
	}

	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory h) {
		return h.getSessionFactory();

	}

	@Bean
	public ChoiceService choiceService() {
		return new ChoiceService();
	}

	

	

	

	

}
