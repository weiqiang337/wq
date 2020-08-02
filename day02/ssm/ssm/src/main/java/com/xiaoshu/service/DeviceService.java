package com.xiaoshu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceExample;
import com.xiaoshu.entity.DeviceVo;

@Service
public class DeviceService {
	@Autowired
	DeviceMapper userMapper;

//	// 查询所有
//	public List<User> findUser(User t) throws Exception {
//		return userMapper.select(t);
//	};
//
//	// 数量
//	public int countUser(User t) throws Exception {
//		return userMapper.selectCount(t);
//	};
//
//	// 通过ID查询
//	public User findOneUser(Integer id) throws Exception {
//		return userMapper.selectByPrimaryKey(id);
//	};
//
	// 新增
	public void addUser(Device t) throws Exception {
		userMapper.insert(t);
	};

	// 修改
	public void updateUser(Device t) throws Exception {
		userMapper.updateByPrimaryKeySelective(t);
	};
	// 删除delectUser
	public void deleteUser(Integer id) throws Exception {
		userMapper.deleteByPrimaryKey(id);
	};

	public PageInfo<Device> findUserPage(Device device, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> map= new HashMap<String,Object>();
		map.put("devicename", device.getDevicename());
		map.put("status", device.getStatus());
		List<Device> studentList = userMapper.findAll(map);
		PageInfo<Device> pageInfo = new PageInfo<Device>(studentList);
		return pageInfo;
	}
	// 通过用户名判断是否存在，（新增时不能重名）
	public Device existUserWithUserName(String devicename) throws Exception {
		DeviceExample example = new DeviceExample();
		com.xiaoshu.entity.DeviceExample.Criteria criteria = example.createCriteria();
		criteria.andDevicenameEqualTo(devicename);
		List<Device> userList = userMapper.selectByExample(example);
		return userList.isEmpty()?null:userList.get(0);
	}

	public List<DeviceVo> fandzz() {
		return userMapper.findzz();
	}

	public List<Device> findAll() {
		List<Device> list = userMapper.findAll(null);
		return list;
	}


}
