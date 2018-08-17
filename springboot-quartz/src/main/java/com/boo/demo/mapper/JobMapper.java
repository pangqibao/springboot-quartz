package com.boo.demo.mapper;

import com.boo.demo.entity.JobAndTrigger;

import java.util.List;

public interface JobMapper {
	List<JobAndTrigger> getJobAndTriggerDetails();
}
