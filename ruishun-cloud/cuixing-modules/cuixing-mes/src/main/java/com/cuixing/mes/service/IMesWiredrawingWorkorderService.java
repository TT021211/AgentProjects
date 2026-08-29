package com.cuixing.mes.service;

import java.math.BigInteger;
import com.cuixing.mes.entity.MesWiredrawingWorkorder;
import com.cuixing.mes.entity.vo.MoldGaugeVo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

 /**
 * 拉丝工单;(mes_wiredrawing_workorder)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
public interface IMesWiredrawingWorkorderService{
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    MesWiredrawingWorkorder queryById(BigInteger id);
    /**
     * 分页查询
     *
     * @param mesWiredrawingWorkorder 筛选条件
     * @return 查询结果
     */
    List<MesWiredrawingWorkorder> selectMesWiredrawingWorkorderList(MesWiredrawingWorkorder mesWiredrawingWorkorder);
    /**
     * 新增数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 实例对象
     */
    int insert(MesWiredrawingWorkorder mesWiredrawingWorkorder) throws ParseException;
    /**
     * 更新数据
     *
     * @param mesWiredrawingWorkorder 实例对象
     * @return 实例对象
     */
    int update(MesWiredrawingWorkorder mesWiredrawingWorkorder);
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 排序-优先
      */
     int updateSortById(BigInteger id);

     int cancelById(BigInteger id);

     int updateStatusById(BigInteger id, String status);

     MesWiredrawingWorkorder getInfoByWiredrawingCode(String wiredrawingCode);

     int batchUpdate(ArrayList<MesWiredrawingWorkorder> mesWiredrawingWorkorders);
 }
