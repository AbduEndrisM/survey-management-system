package com.mum.groupproject.survey.domain;

import java.util.UUID;

import javax.persistence.Id;

public class Rate extends QuestionActivity{
	
	
	private static final long serialVersionUID = 1L;


	@Id
	private String id = UUID.randomUUID().toString();
	
	
	private int value;

	public Rate(Question question, Survey survey,int value) {
		super(question, survey);
		this.value = value;
	}
	
	

	public int getValue() {
		return value;
	}



	public void setValue(int value) {
		this.value = value;
	}
	
	



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	@Override
	String createActivity(QuestionActivity activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String updateActivity(QuestionActivity activity) {
		// TODO Auto-generated method stub
		return null;
	}

}
