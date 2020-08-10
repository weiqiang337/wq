package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Major;

@Service
public class MajorService {
@Autowired
StudentMapper studentMapper;
@Autowired
MajorMapper majorMapper;
public List<Major> findMajor() {
	return majorMapper.selectAll();
}
public void add(Major major) {
	majorMapper.add(major);
}
}
