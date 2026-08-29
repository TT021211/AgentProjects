package com.cuixing.mes.service;

import java.math.BigInteger;
import com.cuixing.mes.entity.MesEnamellingWorkorder;
import com.cuixing.mes.entity.vo.MesWorkorderKanban;

import java.util.List;

 /**
 * 漆包工单;(mes_enamelling_workorder)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
public interface IMesEnamellingWorkorderService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesEnamellingWorkorder queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mesEnamellingWorkorder 筛选条件
     * @return 查询结果
     */
    List<MesEnamellingWorkorder> selectMesEnamellingWorkorderList(MesEnamellingWorkorder mesEnamellingWorkorder);
    /** 
     * 新增数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 实例对象
     */
    int insert(MesEnamellingWorkorder mesEnamellingWorkorder);
    /** 
     * 更新数据
     *
     * @param mesEnamellingWorkorder 实例对象
     * @return 实例对象
     */
    int update(MesEnamellingWorkorder mesEnamellingWorkorder);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 排序-优先
      * @return
      */
     int updateSortById(BigInteger id);

     int cancelById(BigInteger id);

     int insertWorkorder(MesEnamellingWorkorder mesEnamellingWorkorder);

     int cancelInsertWorkorder(BigInteger id);

     int cancelWorkorder(BigInteger id);

     int updateStatusById(BigInteger id, String status);

     MesEnamellingWorkorder queryByWorkorderId(BigInteger workorderId);

     /**
      *  通过机台id查询该机台所有已排产、生产中列表*
      * @param equipmentId
      * @return
      */
     List<MesEnamellingWorkorder> getListByEquipmentId(BigInteger equipmentId, String axleNumber);

     MesWorkorderKanban getEnamellingSummaryData();
 }