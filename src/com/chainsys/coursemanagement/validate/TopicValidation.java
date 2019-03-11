package com.chainsys.coursemanagement.validate;

import com.chainsys.coursemanagement.model.Topic;

public class TopicValidation {

	public static boolean addTopicValidation(Topic topic) {
		boolean validationResult = true;
		if (topic.getCourse().getId() > 0) {
			if (topic.getName().equals(null) || topic.getName().isEmpty()) {
				validationResult = false;
			}
		} else {
			validationResult = false;
		}
		return validationResult;
	}

	public static boolean updateTopicValidation(Topic topic) {
	
		boolean validationResult = true;
		if (topic.getCourse().getId() > 0) {
			if (topic.getId() > 0) {
				if (topic.getName().equals(null) || topic.getName().isEmpty()) {
					validationResult = false;
				} else {
					validationResult = true;
				}
			} else {
				validationResult = false;
			}

		} else {
			validationResult = false;
		}
		return validationResult;
	}
	
	public static boolean removeTopicValidation(Topic topic) {
		boolean validationResult = true;
		if (topic.getCourse().getId() > 0) {
			if (topic.getId() < 0) {
				validationResult = false;
			} 
		} else {
			validationResult = false;
		}
		return validationResult;
	}

}
