package com.cuixing.mes.mapper;

import com.cuixing.mes.entity.vo.KanbanPage;
import com.cuixing.mes.entity.vo.MesPageWeek;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.mes.entity.MesProductWorkorder;
import org.apache.ibatis.annotations.Param;

/**
 * 生产工单;(mes_product_workorder)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-11
 */
@Mapper
public interface MesProductWorkorderMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesProductWorkorder queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mesProductWorkorder 查询条件
     * @return 对象列表
     */
    List<MesProductWorkorder> selectMesProductWorkorderList(MesProductWorkorder mesProductWorkorder);
    /** 
     * 新增数据
     *
     * @param mesProductWorkorder 实例对象
     * @return 影响行数
     */
    int insert(MesProductWorkorder mesProductWorkorder);
    
    /** 
     * 更新数据
     *
     * @param mesProductWorkorder 实例对象
     * @return 影响行数
     */
    int update(MesProductWorkorder mesProductWorkorder);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

    /**
     * 通过id改变状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(@Param("id") BigInteger id, @Param("status")String status);

    MesProductWorkorder queryByWorkorderCode(@Param("workorderCode")String workorderCode);

    int cancelById(@Param("id")BigInteger id);

    KanbanPage getProductWorkorderNum();

    List<MesPageWeek> getMesProductWorkorderWeek();
}