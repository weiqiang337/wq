package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Equipment;
import com.xiaoshu.entity.Modle;
import com.xiaoshu.entity.ModleVo;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.service.EquipmentService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("equipment")
public class EquipmentController {

	@Autowired
	EquipmentService equipmentService;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	JedisPool jedisPool;
	
	@RequestMapping("equipmentIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Modle> roleList = equipmentService.findAll();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("roleList", roleList);
		return "equipment";
	}
	@RequestMapping(value="userList",method=RequestMethod.POST)
	public void userList(Equipment equipment,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Equipment> userList= equipmentService.findEquipmentPage(equipment,pageNum,pageSize);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",userList.getTotal() );
			jsonObj.put("rows", userList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	// 新增或修改
		@RequestMapping("reserveUser")
		public void reserveUser(HttpServletRequest request,Equipment equipment,HttpServletResponse response){
			Integer userId = equipment.getId();
			JSONObject result=new JSONObject();
			try {
				if (userId != null) {   // userId不为空 说明是修改
					equipmentService.updateEquipment(equipment);
						result.put("success", true);
					
				}else {   // 添加
					if(equipmentService.existEquipmentWithName(equipment.getName())==null){  // 没有重复可以添加
						equipment.setCreatetime(new Date());
						
						Jedis jedis = jedisPool.getResource();
						jedis.hset("equipment", equipment.getName(), equipment.getPrice().toString());
						
						
						equipmentService.addEquipment(equipment);
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
		
		//删除
		@RequestMapping("deleteUser")
		public void delUser(HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				String[] ids=request.getParameter("ids").split(",");
				for (String id : ids) {
					equipmentService.deleteEquipment(Integer.parseInt(id));
				}
				result.put("success", true);
				result.put("delNums", ids.length);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
		
		//报表
		@RequestMapping("zzEquipment")
		public void zzEquipment(HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				// 统计需要的数据
				List<ModleVo> list = equipmentService.findModleVo();
				
				result.put("list", list);
				
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
}
