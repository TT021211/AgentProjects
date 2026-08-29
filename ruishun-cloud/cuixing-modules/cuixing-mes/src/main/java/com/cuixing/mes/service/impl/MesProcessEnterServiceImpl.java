package com.cuixing.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.mes.entity.MesProcessEnter;
import com.cuixing.mes.mapper.MesProcessEnterMapper;
import com.cuixing.mes.service.IMesProcessEnterService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import java.math.BigInteger;
import java.util.List;
 /**
 * 工艺录入;(mes_process_enter)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-6
 */
@Service
public class MesProcessEnterServiceImpl implements IMesProcessEnterService{

    @Autowired
    private MesProcessEnterMapper mesProcessEnterMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MesProcessEnter queryById(BigInteger id){
        return mesProcessEnterMapper.queryById(id);
    }

     @Override
     public MesProcessEnter getInfoByEquipmentIdAndSpecification(BigInteger equipmentId,String specification) {
         return mesProcessEnterMapper.getInfoByEquipmentIdAndSpecification(equipmentId,specification);
     }

     @Override
     public MesProcessEnter getInfoByEquipmentCodeAndOther(String equipmentCode, String productSpecification, String productModel,String attr1) {
         String[] split = productModel.split("-");
         String productModelPrefix = split[0];
         String substring = attr1.substring(0, 1);
         String position = "";
         if (substring.equals("左")){
             position = "左边";
         }else if (substring.equals("右")){
             position = "右边";
         }else{
             throw new RuntimeException("位置信息错误!!!");
         }
         return mesProcessEnterMapper.getInfoByEquipmentCodeAndOther(equipmentCode,productSpecification,productModelPrefix,position);
     }

     /**
     * 分页查询
     *
     * @param mesProcessEnter 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MesProcessEnter> selectMesProcessEnterList(MesProcessEnter mesProcessEnter){
       return mesProcessEnterMapper.selectMesProcessEnterList(mesProcessEnter);
    }
    
    /** 
     * 新增数据
     *
     * @param mesProcessEnter 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(MesProcessEnter mesProcessEnter){
    	mesProcessEnter.setCreateTime(DateUtils.getNowDate());
		mesProcessEnter.setCreateBy(SecurityUtils.getUsername());
        return mesProcessEnterMapper.insert(mesProcessEnter);
    }
    
    /** 
     * 更新数据
     *
     * @param mesProcessEnter 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MesProcessEnter mesProcessEnter){
        mesProcessEnter.setUpdateTime(DateUtils.getNowDate());
		mesProcessEnter.setUpdateBy(SecurityUtils.getUsername());
        return mesProcessEnterMapper.update(mesProcessEnter);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mesProcessEnterMapper.deleteByIds(ids);
    }
}