package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoshu.dao.ContentCategoryMapper;
import com.xiaoshu.entity.ContentCategory;

@Service
public class ContentCategoryService {
		
	@Autowired
	ContentCategoryMapper contentCategoryMapper;
		public List<ContentCategory> findcat() {
			// TODO Auto-generated method stub
			return contentCategoryMapper.findcat();
		}

	
}
