package com.cuixing.md.entity.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MdProductVo {
    private List<String> specList;
    Map<String, Map<String, List<String>>> batchNumberMap = new HashMap<>(); // 改为嵌套Map
    private Map<String,String> materialCode;
    private Map<String,String> axialType;
    private Map<String,String> coefficient;

    public List<String> getSpecList() {
        return specList;
    }

    public void setSpecList(List<String> specList) {
        this.specList = specList;
    }

    public Map<String, Map<String, List<String>>> getBatchNumberMap() {
        return batchNumberMap;
    }

    public void setBatchNumberMap(Map<String, Map<String, List<String>>> batchNumberMap) {
        this.batchNumberMap = batchNumberMap;
    }

    public Map<String, String> getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(Map<String, String> materialCode) {
        this.materialCode = materialCode;
    }

    public Map<String, String> getAxialType() {
        return axialType;
    }

    public void setAxialType(Map<String, String> axialType) {
        this.axialType = axialType;
    }

    public Map<String, String> getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Map<String, String> coefficient) {
        this.coefficient = coefficient;
    }


}
