package com.boo.demo.service.impl;

import com.boo.demo.entity.JobAndTrigger;
import com.boo.demo.mapper.JobMapper;
import com.boo.demo.service.IJobAndTriggerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JobAndTriggerImpl implements IJobAndTriggerService {

	@Autowired
	private JobMapper jobMapper;
	
	@Override
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<JobAndTrigger> list = jobMapper.getJobAndTriggerDetails();
		PageInfo<JobAndTrigger> page = new PageInfo<JobAndTrigger>(list);
		return page;
	}

}