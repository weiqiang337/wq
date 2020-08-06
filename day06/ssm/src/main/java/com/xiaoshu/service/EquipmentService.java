package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.EquipmentMapper;
import com.xiaoshu.dao.ModleMapper;
import com.xiaoshu.entity.Equipment;
import com.xiaoshu.entity.Modle;
import com.xiaoshu.entity.ModleVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class EquipmentService {

	@Autowired
	EquipmentMapper equipmentMapper;
	
	@Autowired
	ModleMapper modleMapper;

	public List<Modle> findAll() {
		// TODO Auto-generated method stub
		return modleMapper.selectAll();
	}

	public PageInfo<Equipment> findEquipmentPage(Equipment equipment, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<Equipment> userList = equipmentMapper.findEquipmentAll(equipment);
		PageInfo<Equipment> pageInfo = new PageInfo<Equipment>(userList);
		return pageInfo;
	}

	public Equipment existEquipmentWithName(String name) {
		// TODO Auto-generated method stub
		List<Equipment> userList = equipmentMapper.findName(name);
		return userList.isEmpty()?null:userList.get(0);
	}

	public void addEquipment(Equipment equipment) {
		// TODO Auto-generated method stub
		equipmentMapper.insert(equipment);
	}

	public void updateEquipment(Equipment equipment) {
		// TODO Auto-generated method stub
		equipmentMapper.updateByPrimaryKey(equipment);
	}

	public void deleteEquipment(int parseInt) {
		// TODO Auto-generated method stub
		equipmentMapper.deleteByPrimaryKey(parseInt);
	}

	public List<ModleVo> findModleVo() {
		// TODO Auto-generated method stub
		return equipmentMapper.findModleVo();
	}
}
