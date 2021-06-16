package com.myapp.task.common.validation;

import java.util.Map;

public interface TaskValidationService {
	boolean isNull(Object obj);

	Map<String, Object> validationCheckResult(Map<String, Object> checkResult);

	Map<String, Object> errorResult(String errorMsg);
}
