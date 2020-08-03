package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class GoodsService {

	@Autowired
	 private GoodsMapper gm;
	@Autowired
	 private GoodstypeMapper tm;
	

	public PageInfo<GoodsVo> findUserPage(GoodsVo vo, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<GoodsVo> list = gm.findAll(vo);
		PageInfo<GoodsVo> info = new PageInfo<>(list);
		return info;
	}



	public Goods existUserWithUserName(String name) {
		// TODO Auto-generated method stub
		Goods record = new Goods();
		record.setName(name);
		return gm.selectOne(record);
	}



	public void updateUser(Goods goods) {
		gm.updateByPrimaryKeySelective(goods);
		
	}



	public void addUser(Goods goods) {
		gm.insert(goods);
		
	}



	public List<Goodstype> selectAll() {
		// TODO Auto-generated method stub
		return tm.selectAll();
	}


}
