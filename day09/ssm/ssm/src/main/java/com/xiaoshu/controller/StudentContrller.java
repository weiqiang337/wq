package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.StudentVo;
import com.xiaoshu.service.MajorService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.StudentService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("student")
public class StudentContrller {
@Autowired
StudentService studentService;
@Autowired
MajorService majorService;
@Autowired
private OperationService operationService;

@RequestMapping("studentIndex")
public String index(HttpServletRequest request,Integer menuid) throws Exception{
	List<Major> majorList = majorService.findMajor();
	List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
	request.setAttribute("operationList", operationList);
	request.setAttribute("roleList", majorList);
	return "student";
}
@RequestMapping(value="studentList",method=RequestMethod.POST)
public void studentList(Student student,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
	try {
		Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
		Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
		PageInfo<Student> studentList= studentService.findUserPage(student,pageNum,pageSize);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total",studentList.getTotal() );
		jsonObj.put("rows", studentList.getList());
        WriterUtil.write(response,jsonObj.toString());
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
	@RequestMapping("reserveStudent")
	public void reserveStudent(HttpServletRequest request,Student user,HttpServletResponse response) throws Exception{
		Integer sid = user.getSid();
		JSONObject result=new JSONObject();
			if (sid != null) {   // userId不为空 说明是修改
					studentService.updateUser(user);
					result.put("success", true);
				
			}else {   // 添加
					studentService.addUser(user);
					result.put("success", true);
				}
		WriterUtil.write(response, result.toString());

	}
	@RequestMapping("zzStudent")
	public void zzStudent(HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			List<StudentVo> list=studentService.findzz();
			result.put("success", true);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	//删除
	@RequestMapping("deleteStudent")
	public void deleteStudent(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				studentService.del(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	//导入
		@RequestMapping("studentin")
		public void studentin(HttpServletResponse response,MultipartFile file){
			JSONObject result=new JSONObject();
			try {
				HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
				HSSFSheet sheet = wb.getSheetAt(0);
				int lastRowNum = sheet.getLastRowNum();
				for (int i = 1; i < lastRowNum+1; i++) {
					HSSFRow row = sheet.getRow(i);
					Student s= new Student(); 
					String sname = row.getCell(1).getStringCellValue();
					s.setSname(sname);
					String sex = row.getCell(2).getStringCellValue();
					s.setSex(sex);
					String hobby = row.getCell(3).getStringCellValue();
					s.setHobby(hobby);
					String birthday = row.getCell(4).getStringCellValue();
					s.setBirthday(TimeUtil.ParseTime(birthday, "yyyy-MM-dd"));
					String maname = row.getCell(5).getStringCellValue();
					 Integer maid= studentService.majorId(maname);
					s.setMaid(maid);
					studentService.addUser(s);
				}
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
	@RequestMapping("addmajor")
	public void addmajor(Major major,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			majorService.add(major);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，添加专业失败");
		}
		WriterUtil.write(response, result.toString());
	}
	//导出
	@RequestMapping("outStudent")
	public void outStudent(HttpServletResponse response){
	JSONObject result=new JSONObject();
	try {
		List<Student> list = studentService.findAll();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row0 = sheet.createRow(0);
		String[] title={"学生编号","学生姓名","年龄","所在年级","入学时间","所在专业"};
		for (int i = 0; i < title.length; i++) {
			row0.createCell(i).setCellValue(title[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			Student s = list.get(i);
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(s.getSid());
			row.createCell(1).setCellValue(s.getSname());
			row.createCell(2).setCellValue(s.getSex());
			row.createCell(3).setCellValue(s.getHobby());
			row.createCell(4).setCellValue(TimeUtil.formatTime(s.getBirthday(), "yyyy-MM-dd"));
			row.createCell(5).setCellValue(s.getMajor().getManame());
		}
		OutputStream out = new FileOutputStream(new File("F://猴头菇.xls"));
		wb.write(out);
		out.close();
		wb.close();
		result.put("success", true);
	} catch (Exception e) {
		e.printStackTrace();
		result.put("errorMsg", "对不起，导出失败");
	}
	WriterUtil.write(response, result.toString());
}
}
