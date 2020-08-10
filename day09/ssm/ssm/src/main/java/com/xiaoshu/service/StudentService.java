package com.xiaoshu.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.dao.StudentMapper;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
@Service
public class StudentService {
@Autowired
MajorMapper majorMapper;
@Autowired
StudentMapper studentMapper;
public PageInfo<Student> findUserPage(Student student, int pageNum, int pageSize) {
	PageHelper.startPage(pageNum, pageSize);
	Map<String,	Object> map = new HashMap<String, Object>();
	map.put("sname", student.getSname());
	map.put("maid", student.getMaid());
	List<Student> studentList = studentMapper.findAll(map);
	PageInfo<Student> pageInfo = new PageInfo<Student>(studentList);
	return pageInfo;
}
	
	//新增
	public void addUser(Student t) throws Exception {
		studentMapper.insert(t);
	};

	// 修改
	public void updateUser(Student t) throws Exception {
		studentMapper.updateByPrimaryKeySelective(t);
	};

	// 删除
		public void del(Integer id) throws Exception {
			studentMapper.deleteByPrimaryKey(id);
		}

		public List<Student> findAll() {
			List<Student> ss = studentMapper.findAll(null);
			return ss;
		}

		public Integer majorId(String maname) {
			return majorMapper.majorId(maname);
		}

		public List<StudentVo> findzz() {
			return studentMapper.findzz();
		};
}
