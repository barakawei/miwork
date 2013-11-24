package com.barakawei.lightwork.domain;

import com.barakawei.lightwork.util.JsonUtil;
import nl.bstoi.poiparser.api.strategy.annotations.Cell;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-1-30
 * Time: 下午7:32
 * To change this template use File | Settings | File Templates.
 */
public class ZipperExcel {

    @Cell(columnNumber = 0)
    private String name;

    @Cell(columnNumber = 1)
    private String material;

    @Cell(columnNumber = 2)
    private String spec;

    @Cell(columnNumber = 3)
    private String type;

    @Cell(columnNumber = 4)
    private String model_145;

    @Cell(columnNumber = 5)
    private String model_150;

    @Cell(columnNumber = 6)
    private String model_155;
    @Cell(columnNumber = 7)
    private String model_160;
    @Cell(columnNumber = 8)
    private String model_165;
    @Cell(columnNumber = 9)
    private String model_170;
    @Cell(columnNumber = 10)
    private String model_175;
    @Cell(columnNumber = 11)
    private String model_180;
    @Cell(columnNumber = 12)
    private String model_185;
    @Cell(columnNumber = 13)
    private String model_190;
    @Cell(columnNumber = 14)
    private String model_195;
    @Cell(columnNumber = 15)
    private String model_200;
    @Cell(columnNumber = 16)
    private String model_205;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getModel_145() {
        return model_145;
    }

    public void setModel_145(String model_145) {
        this.model_145 = model_145;
    }

    public String getModel_150() {
        return model_150;
    }

    public void setModel_150(String model_150) {
        this.model_150 = model_150;
    }

    public String getModel_155() {
        return model_155;
    }

    public void setModel_155(String model_155) {
        this.model_155 = model_155;
    }

    public String getModel_160() {
        return model_160;
    }

    public void setModel_160(String model_160) {
        this.model_160 = model_160;
    }

    public String getModel_165() {
        return model_165;
    }

    public void setModel_165(String model_165) {
        this.model_165 = model_165;
    }

    public String getModel_170() {
        return model_170;
    }

    public void setModel_170(String model_170) {
        this.model_170 = model_170;
    }

    public String getModel_175() {
        return model_175;
    }

    public void setModel_175(String model_175) {
        this.model_175 = model_175;
    }

    public String getModel_180() {
        return model_180;
    }

    public void setModel_180(String model_180) {
        this.model_180 = model_180;
    }

    public String getModel_185() {
        return model_185;
    }

    public void setModel_185(String model_185) {
        this.model_185 = model_185;
    }

    public String getModel_190() {
        return model_190;
    }

    public void setModel_190(String model_190) {
        this.model_190 = model_190;
    }

    public String getModel_195() {
        return model_195;
    }

    public void setModel_195(String model_195) {
        this.model_195 = model_195;
    }

    public String getModel_200() {
        return model_200;
    }

    public void setModel_200(String model_200) {
        this.model_200 = model_200;
    }

    public String getModel_205() {
        return model_205;
    }

    public void setModel_205(String model_205) {
        this.model_205 = model_205;
    }

    public static void convertFromExcel(Purchasing p, List<Zipper> zippers, List<ZipperExcel> zes) {
        String type = "";
        String chest ="";
        List<String> modelName = new ArrayList<String>();
        List<Model> models = new ArrayList<Model>();
        for (int i = 0; i < zes.size(); i++) {
            ZipperExcel ze = zes.get(i);
            if(StringUtils.equals(ze.getType(),"selected")){
                modelName.add(ze.getModel_145());
                modelName.add(ze.getModel_150());
                modelName.add(ze.getModel_155());
                modelName.add(ze.getModel_160());
                modelName.add(ze.getModel_165());
                modelName.add(ze.getModel_170());
                modelName.add(ze.getModel_175());
                modelName.add(ze.getModel_180());
                modelName.add(ze.getModel_185());
                modelName.add(ze.getModel_190());
                modelName.add(ze.getModel_195());
                modelName.add(ze.getModel_200());
                modelName.add(ze.getModel_205());
                continue;
            }
            
            if (ze.getName() != null && ze.getMaterial() == null && ze.getSpec() == null&&!StringUtils.contains(ze.getName(),"净胸围")&&!StringUtils.contains(ze.getName(),"备注")) {
                type = ze.getName();
                chest ="";
                models.add(new Model(modelName.get(0), "model_145", ze.getModel_145() == null ? "" : ze.getModel_145(), type,0));
                models.add(new Model(modelName.get(1), "model_150", ze.getModel_150() == null ? "" : ze.getModel_150(), type,0));
                models.add(new Model(modelName.get(2), "model_155", ze.getModel_155() == null ? "" : ze.getModel_155(), type,0));
                models.add(new Model(modelName.get(3), "model_160", ze.getModel_160() == null ? "" : ze.getModel_160(), type,0));
                models.add(new Model(modelName.get(4), "model_165", ze.getModel_165() == null ? "" : ze.getModel_165(), type,0));
                models.add(new Model(modelName.get(5), "model_170", ze.getModel_170() == null ? "" : ze.getModel_170(), type,0));
                models.add(new Model(modelName.get(6), "model_175", ze.getModel_175() == null ? "" : ze.getModel_175(), type,0));
                models.add(new Model(modelName.get(7), "model_180", ze.getModel_180() == null ? "" : ze.getModel_180(), type,0));
                models.add(new Model(modelName.get(8), "model_185", ze.getModel_185() == null ? "" : ze.getModel_185(), type,0));
                models.add(new Model(modelName.get(9), "model_190", ze.getModel_190() == null ? "" : ze.getModel_190(), type,0));
                models.add(new Model(modelName.get(10), "model_195", ze.getModel_195() == null ? "" : ze.getModel_195(), type,0));
                models.add(new Model(modelName.get(11), "model_200", ze.getModel_200() == null ? "" : ze.getModel_200(), type,0));
                models.add(new Model(modelName.get(12), "model_205", ze.getModel_205() == null ? "" : ze.getModel_205(), type,0));
                continue;
            }
            
            if(StringUtils.contains(ze.getName(),"净胸围")&&StringUtils.contains(ze.getType(),"是")){
            	chest = ze.getName();
            	models.add(new Model(modelName.get(0), "model_145", ze.getModel_145() == null ? "" : ze.getModel_145(), type,1));
                //models.add(new Model(modelName.get(1), "model_150", ze.getModel_150() == null ? "" : ze.getModel_150(), type,1));
                models.add(new Model(modelName.get(2), "model_155", ze.getModel_155() == null ? "" : ze.getModel_155(), type,1));
                //models.add(new Model(modelName.get(3), "model_160", ze.getModel_160() == null ? "" : ze.getModel_160(), type,1));
                models.add(new Model(modelName.get(4), "model_165", ze.getModel_165() == null ? "" : ze.getModel_165(), type,1));
                //models.add(new Model(modelName.get(5), "model_170", ze.getModel_170() == null ? "" : ze.getModel_170(), type,1));
                models.add(new Model(modelName.get(6), "model_175", ze.getModel_175() == null ? "" : ze.getModel_175(), type,1));
                //models.add(new Model(modelName.get(7), "model_180", ze.getModel_180() == null ? "" : ze.getModel_180(), type,1));
                models.add(new Model(modelName.get(8), "model_185", ze.getModel_185() == null ? "" : ze.getModel_185(), type,1));
                //models.add(new Model(modelName.get(9), "model_190", ze.getModel_190() == null ? "" : ze.getModel_190(), type,1));
                models.add(new Model(modelName.get(10), "model_195", ze.getModel_195() == null ? "" : ze.getModel_195(), type,1));
                //models.add(new Model(modelName.get(11), "model_200", ze.getModel_200() == null ? "" : ze.getModel_200(), type,1));
                models.add(new Model(modelName.get(12), "model_205", ze.getModel_205() == null ? "" : ze.getModel_205(), type,1));
                continue;
            }
            if (i >= 3&& !StringUtils.contains(ze.getName(), "备注") && !StringUtils.contains(ze.getName(), "面料实际缩率")&&!StringUtils.contains(ze.getName(),"净胸围")) {
                Zipper z = new Zipper();
                if(StringUtils.contains(chest,"净胸围")){
                	z.setChest(1);
                }else{
                	z.setChest(0);
                }
                z.setPosition(type);
                z.setName(ze.getName());
                z.setPurchasing(p);
                z.setMaterial(ze.getMaterial());
                z.setSpec(ze.getSpec());
                List<Model> zipperCount = new ArrayList<Model>();
                if(z.getChest()!=1){
                zipperCount.add(new Model(modelName.get(0), "model_145", ze.getModel_145() == null ? "" : ze.getModel_145(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(1), "model_150", ze.getModel_150() == null ? "" : ze.getModel_150(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(2), "model_155", ze.getModel_155() == null ? "" : ze.getModel_155(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(3), "model_160", ze.getModel_160() == null ? "" : ze.getModel_160(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(4), "model_165", ze.getModel_165() == null ? "" : ze.getModel_165(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(5), "model_170", ze.getModel_170() == null ? "" : ze.getModel_170(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(6), "model_175", ze.getModel_175() == null ? "" : ze.getModel_175(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(7), "model_180", ze.getModel_180() == null ? "" : ze.getModel_180(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(8), "model_185", ze.getModel_185() == null ? "" : ze.getModel_185(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(9), "model_190", ze.getModel_190() == null ? "" : ze.getModel_190(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(10), "model_195", ze.getModel_195() == null ? "" : ze.getModel_195(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(11), "model_200", ze.getModel_200() == null ? "" : ze.getModel_200(), type,z.getChest()));
                zipperCount.add(new Model(modelName.get(12), "model_205", ze.getModel_205() == null ? "" : ze.getModel_205(), type,z.getChest()));
                }else{
                	zipperCount.add(new Model(modelName.get(0), "model_145", ze.getModel_145() == null ? "" : ze.getModel_145(), type,z.getChest()));
                    zipperCount.add(new Model(modelName.get(2), "model_155", ze.getModel_155() == null ? "" : ze.getModel_155(), type,z.getChest()));
                    zipperCount.add(new Model(modelName.get(4), "model_165", ze.getModel_165() == null ? "" : ze.getModel_165(), type,z.getChest()));
                    zipperCount.add(new Model(modelName.get(6), "model_175", ze.getModel_175() == null ? "" : ze.getModel_175(), type,z.getChest()));
                    zipperCount.add(new Model(modelName.get(8), "model_185", ze.getModel_185() == null ? "" : ze.getModel_185(), type,z.getChest()));
                    zipperCount.add(new Model(modelName.get(10), "model_195", ze.getModel_195() == null ? "" : ze.getModel_195(), type,z.getChest()));
                    zipperCount.add(new Model(modelName.get(12), "model_205", ze.getModel_205() == null ? "" : ze.getModel_205(), type,z.getChest()));
                }
                z.setZipperCountList(zipperCount);
                zippers.add(z);
            }
            if (StringUtils.contains(ze.getName(), "备注")) {
            	p.setRemark(ze.getName());
            }
            if (StringUtils.contains(ze.getName(), "面料实际缩率")) {
                p.setActualShrinkage(ze.getMaterial());
                p.setZipperShrinkage(ze.getType());
                break;
            }
        }
        p.setCountDetailList(models);
    }
}
