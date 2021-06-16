package com.myapp.task.common.validation;

import com.myapp.task.common.cmmcode.ResultCode;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskValidationServiceImpl implements TaskValidationService {
	@Override
	public boolean isNull(Object obj) {
		if(obj != null) {
			if( obj instanceof String) {
				return obj.toString().trim().length() < 1;
			} else if ( obj instanceof Object[] ) {
				return Array.getLength(obj) == 0;
			} else if ( obj instanceof List) {
				return ((List<?>) obj).isEmpty();
			} else if ( obj instanceof Map) {
				return ((Map<?, ?>) obj).isEmpty();
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> validationCheckResult(Map<String, Object> checkResult) {
		Map<String, Object> resultMap = new HashMap<>(checkResult);

		if(!this.isNull(resultMap) && !this.isNull(resultMap.get(ResultCode.MSG))) {
			return this.errorResult(resultMap.get(ResultCode.MSG).toString());
		}

		resultMap.put(ResultCode.RESULT, ResultCode.SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> errorResult(String errorMsg) {
		Map<String, Object> errorResultMap = new HashMap<>();
		errorResultMap.put(ResultCode.RESULT, ResultCode.ERROR);
		errorResultMap.put(ResultCode.MSG, errorMsg);
		return errorResultMap;
	}
}
