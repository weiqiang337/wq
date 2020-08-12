package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ContentCategoryMapper;
import com.xiaoshu.dao.ContentMapper;
import com.xiaoshu.entity.Content;
import com.xiaoshu.entity.ContentCategory;
import com.xiaoshu.entity.ContentVo;

@Service
public class ContentService {

	@Autowired
	ContentMapper contentMapper;

	// 新增
	public void addContent(Content content) throws Exception {
		contentMapper.insert(content);
	};

	// 修改
	public void updateContent(Content content) throws Exception {
		contentMapper.updateByPrimaryKeySelective(content);
	};

	// 删除
	public void deleteContent(Integer id) throws Exception {
		contentMapper.deleteByPrimaryKey(id);
	};

	

	public PageInfo<ContentVo> findUserPage(ContentVo contentVo, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		List<ContentVo> userList = contentMapper.findAll(contentVo);
		PageInfo<ContentVo> pageInfo = new PageInfo<ContentVo>(userList);
		return pageInfo;
	}


}
