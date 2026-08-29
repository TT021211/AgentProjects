package com.cuixing.common.core.web.page;

import java.io.Serializable;
import java.util.List;

public class WmsTableDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    private Double totalNetWeight;

    private Double totalTareWeight;

    private Integer count;

    /**
     * 表格数据对象
     */
    public WmsTableDataInfo()
    {

    }

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public WmsTableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Double getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(Double totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public Double getTotalTareWeight() {
        return totalTareWeight;
    }

    public void setTotalTareWeight(Double totalTareWeight) {
        this.totalTareWeight = totalTareWeight;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
