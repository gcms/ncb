/* @author Mario J Lorenzo */ package cvm.model;

import java.io.Serializable;

public class Form implements Serializable {

	private String userName;

	private String formTypeSchema;

	private String formInstanceSchema;

	private String formID;

	private String formTypeName;

	public Form(String name, String type, String instance, String name2,String name3) {
		// TODO Auto-generated constructor stub
		super();

		formTypeSchema = type;
		formInstanceSchema = instance;
		formID = name2;
		userName = name;
		formTypeName = name3;

	}

	public Form(String type) {
		// TODO Auto-generated constructor stub
		super();

		formTypeSchema = type;
	}

	public Form(String name, String instance) {
		// TODO Auto-generated constructor stub
		super();

		formInstanceSchema = instance;
		userName = name;

	}

	public String getFormID() {
		return formID;
	}

	public void setFormID(String formID) {
		this.formID = formID;
	}

	public String getFormInstanceSchema() {
		return formInstanceSchema;
	}

	public void setFormInstanceSchema(String formInstanceSchema) {
		this.formInstanceSchema = formInstanceSchema;
	}

	public String getFormTypeSchema() {
		return formTypeSchema;
	}

	public void setFormTypeSchema(String formTypeSchema) {
		this.formTypeSchema = formTypeSchema;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}