package com.chainsys.coursemanagement.validate;

import com.chainsys.coursemanagement.model.Project;

public class ProjectValidation {

	public static boolean addProjectValidation(Project project) {
		boolean validationResult = true;
		if (project.getDepartment().getId() < 0) {
			validationResult = false;
		} else {
			if (project.getManager().getId() < 0) {
				validationResult = false;
			}
			

		}
		return validationResult;
	}

}
