package com.cuixing.mes.service;

import java.math.BigInteger;
import com.cuixing.mes.entity.MesProductWorkorder;
import com.cuixing.mes.entity.vo.KanbanPage;
import com.cuixing.mes.entity.vo.MesCountWorkorderKanban;

import java.text.ParseException;
import java.util.List;

 /**
 * 生产工单;(mes_product_workorder)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
public interface IMesProductWorkorderService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProductWorkorder queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mesProductWorkorder 筛选条件
     * @return 查询结果
     */
    List<MesProductWorkorder> selectMesProductWorkorderList(MesProductWorkorder mesProductWorkorder);
    /** 
     * 新增数据
     *
     * @param mesProductWorkorder 实例对象
     * @return 实例对象
     */
    int insert(MesProductWorkorder mesProductWorkorder) throws ParseException;
    /** 
     * 更新数据
     *
     * @param mesProductWorkorder 实例对象
     * @return 实例对象
     */
    int update(MesProductWorkorder mesProductWorkorder);
    /** 
     * 通过主键批量删除数据
     * 
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     int updateStatus(BigInteger id, String status);

     MesProductWorkorder queryByWorkorderCode(String workorderCode);

     int cancelById(BigInteger id);

     MesCountWorkorderKanban getMesWorkOrdersData();

     KanbanPage getKanbanPage();
 }