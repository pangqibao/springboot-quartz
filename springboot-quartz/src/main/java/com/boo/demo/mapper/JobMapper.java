package com.boo.demo.mapper;

import java.util.List;
import java.util.Map;

public interface JobMapper {
	List<Map<String,Object>> getJobAndTriggerDetails();
}
