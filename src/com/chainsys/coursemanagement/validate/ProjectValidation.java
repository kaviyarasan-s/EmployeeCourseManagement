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
			} else {
				if (!project.getName().equals(null)
						&& !project.getName().isEmpty()) {
					if (project.getName().matches("[a-zA-Z]+"))
						validationResult = true;
					else
						validationResult = false;
				} else {
					validationResult = false;
				}
			}

		}
		return validationResult;
	}

}
