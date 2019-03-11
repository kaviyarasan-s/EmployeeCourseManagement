package com.chainsys.coursemanagement.validate;

import com.chainsys.coursemanagement.model.Courses;

public class CourseValidation {

	public static boolean addCoursesValidation(Courses courses) {
		boolean validationResult = false;
		if (!courses.getName().equals(null) && !courses.getName().isEmpty()) {
			validationResult = true;
		}
		return validationResult;
	}

	public static boolean updateCoursesValidation(Courses courses) {
		boolean validationResult = true;
		if (courses.getId() > 0) {
			if (courses.getName().equals(null) || courses.getName().isEmpty()) {
				validationResult = false;
			}
		} else {
			validationResult = false;
		}

		return validationResult;
	}

	public static boolean removeCoursesValidation(Courses courses) {
		boolean validationResult = true;
		if (courses.getId() < 0) {

			validationResult = false;
		}

		return validationResult;
	}

}
