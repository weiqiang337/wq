package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Equipment;
import com.xiaoshu.entity.EquipmentExample;
import com.xiaoshu.entity.ModleVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EquipmentMapper extends BaseMapper<Equipment> {
    long countByExample(EquipmentExample example);

    int deleteByExample(EquipmentExample example);

    List<Equipment> selectByExample(EquipmentExample example);

    int updateByExampleSelective(@Param("record") Equipment record, @Param("example") EquipmentExample example);

    int updateByExample(@Param("record") Equipment record, @Param("example") EquipmentExample example);

	List<Equipment> findEquipmentAll(Equipment equipment);

	List<Equipment> findName(String name);

	List<ModleVo> findModleVo();
}