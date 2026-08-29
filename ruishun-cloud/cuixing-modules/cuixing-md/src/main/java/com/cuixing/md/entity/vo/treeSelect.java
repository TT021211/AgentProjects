package com.cuixing.md.entity.vo;

import com.cuixing.md.entity.MdProduct;
import com.cuixing.md.entity.MdProductType;
import com.cuixing.md.entity.MdWorkgroup;
import com.cuixing.md.entity.MdWorkstation;

import java.io.Serializable;
import java.math.BigInteger;

public class treeSelect implements Serializable {
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String lable;

	public treeSelect(MdWorkgroup workgroup) {
		this.id=workgroup.getId();
		this.lable=workgroup.getWorkgroupName();
	}

	public treeSelect(MdWorkstation mdWorkstation)
	{
		this.id= mdWorkstation.getId();
		this.lable=mdWorkstation.getWorkstationName();
	}

	public treeSelect(MdProductType mdProductType)
	{
		this.id=mdProductType.getId();
		this.lable=mdProductType.getTypeName();
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

}
