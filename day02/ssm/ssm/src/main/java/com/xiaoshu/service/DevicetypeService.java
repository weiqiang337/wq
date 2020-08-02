package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.DevicetypeMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.Devicetype;

@Service
public class DevicetypeService {


	@Autowired
	DevicetypeMapper ss;

	public List<Devicetype> findAll() {
		return ss.selectAll();
	}


	public void add(Devicetype sss) {
		ss.add(sss);
		
	}


	public Integer findId(String typename) {
		return ss.findId(typename);
	}

}
