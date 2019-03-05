package com.chainsys.coursemanagement.model;

public class EmployeeTopic {

	private int id;
	private Topic topic;
	private Status status;
	private Employee employee;
	private int topiccount;
	public int getTopiccount() {
		return topiccount;
	}
	public void setTopiccount(int topiccount) {
		this.topiccount = topiccount;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
