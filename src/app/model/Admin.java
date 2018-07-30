package app.model;

public class Admin {

	private int id;
	private String name, last, other, english, kurs, data;
	private boolean java, python;

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getKurs() {
		return kurs;
	}

	public void setKurs(String kurs) {
		this.kurs = kurs;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isJava() {
		return java;
	}

	public void setJava(boolean java) {
		this.java = java;
	}

	public boolean isPython() {
		return python;
	}

	public void setPython(boolean python) {
		this.python = python;
	}

	public Admin(int id, String name, String last,  boolean java, boolean python, String other, String english, String kurs, String data
			) {

		this.id = id;
		this.name = name;
		this.last = last;
		this.other = other;
		this.english = english;
		this.kurs = kurs;
		this.data = data;
		this.java = java;
		this.python = python;
	}

	public Admin() {

	}

}
