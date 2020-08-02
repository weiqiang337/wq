package com.xiaoshu.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.DeviceVo;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.service.DeviceService;
import com.xiaoshu.service.DevicetypeService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Controller
@RequestMapping("device")
public class DeviceController {
	@Autowired
	private DeviceService userService;
	@Autowired
	private DevicetypeService roleService ;
	@Autowired
	private OperationService operationService;
	@Autowired
	private JedisPool jedisPool;
	@RequestMapping("deviceIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Devicetype> roleList = roleService.findAll();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		List<String> list = new ArrayList<String>();
		list.add("禁用");
		list.add("启用");
		
		request.setAttribute("list", list);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "device";
	}
	@RequestMapping(value="DeviceList",method=RequestMethod.POST)
	public void DeviceList(Device device,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Device> deviceList= userService.findUserPage(device,pageNum,pageSize);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",deviceList.getTotal() );
			jsonObj.put("rows", deviceList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	// 新增或修改
	@RequestMapping("reserveadd")
	public void reserveadd(Devicetype sss,HttpServletRequest request,Device device,HttpServletResponse response){
		Integer userId = device.getId();
		JSONObject result=new JSONObject();
		try {
			if (userId != null) {   // userId不为空 说明是修改
					userService.updateUser(device);
					result.put("success", true);
					result.put("success", true);
			}else {   // 添加
				if(userService.existUserWithUserName(device.getDevicename())==null){  // 没有重复可以添加
					userService.addUser(device);
					sss.setDevicetypeid(1);
					sss.setTypename("太空飞行");
					roleService.add(sss);
					Jedis jedis = jedisPool.getResource();
					System.out.println(sss.getTypename());
					jedis.set(sss.getTypename(),sss.getDevicetypeid()+"");
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	@RequestMapping("delUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try{
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				userService.deleteUser(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	@RequestMapping("zzUser")
	public void zzUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			List<DeviceVo> lists=userService.fandzz();
			result.put("lists", lists);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("outDevice")
	public void outDevice(HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			List<Device> list = userService.findAll();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row0 = sheet.createRow(0);
			String[] title={"产品编号","设备名称","设备类型","产品内存","颜色","价格","状态","时间"};
			for (int i = 0; i < title.length; i++) {
				row0.createCell(i).setCellValue(title[i]);
			}
			for (int i = 0; i < list.size(); i++) {
				Device d = list.get(i);
				HSSFRow row = sheet.createRow(i+1);
				row.createCell(0).setCellValue(d.getId());
				row.createCell(1).setCellValue(d.getDevicetype().getTypename());
				row.createCell(2).setCellValue(d.getDevicename());
				row.createCell(3).setCellValue(d.getDeviceram());
				row.createCell(4).setCellValue(d.getColor());
				row.createCell(5).setCellValue(d.getPrice());
				row.createCell(6).setCellValue(d.getStatus());
				row.createCell(7).setCellValue(TimeUtil.formatTime(d.getCreatetime(), "yyyy-MM-dd"));
			}
			OutputStream out= new FileOutputStream(new File("F://ss.xls"));
			wb.write(out);
			wb.close();
			out.close();
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	@RequestMapping("inDevice")
	public void inDevice(HttpServletResponse response,MultipartFile file){
		JSONObject result=new JSONObject();
		try {
			HSSFWorkbook wb=new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			for (int i = 1; i < lastRowNum+1; i++) {
				HSSFRow row = sheet.getRow(i);
				Device d=new Device();
				String typename = row.getCell(0).getStringCellValue();
				Integer devicetypeid=roleService.findId(typename);
				d.setDevicetypeid(devicetypeid);
				String devicename = row.getCell(1).getStringCellValue();
				d.setDevicename(devicename);
				String deviceram = row.getCell(2).getStringCellValue();
				d.setDeviceram(deviceram);
				String color = row.getCell(1).getStringCellValue();
				d.setColor(color);
				String price = row.getCell(1).getStringCellValue();
				d.setPrice(price);
				String status = row.getCell(1).getStringCellValue();
				d.setStatus(status);
				String createtime = row.getCell(1).getStringCellValue();
				d.setCreatetime(TimeUtil.ParseTime(createtime, "yyyy-MM-dd"));
				userService.addUser(d);
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
}
