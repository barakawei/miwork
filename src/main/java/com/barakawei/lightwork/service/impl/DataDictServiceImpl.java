package com.barakawei.lightwork.service.impl;

import com.barakawei.lightwork.dao.DataDictDao;
import com.barakawei.lightwork.domain.DataDict;
import com.barakawei.lightwork.domain.Zipper;
import com.barakawei.lightwork.service.DataDictService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-1-19
 * Time: 下午10:00
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DataDictServiceImpl implements DataDictService{

    @Autowired
    private DataDictDao dataDictDao;

    @Override
    public void saveDataDict(DataDict dataDict) {
        dataDictDao.save(dataDict);
    }

    @Override
    public DataDict findDataDictById(String id) {
        return dataDictDao.findOne(id);
    }

    @Override
    public DataDict findDataDict() {
        DataDict dd = new DataDict();
        return dd;
    }

    @Override
    public List<DataDict> findDataDictByType(String type,String value) {
        return dataDictDao.findByTypeAndValueLike(type, value);
    }

    @Override
    public void saveDataDictByZipper(Zipper zipper) {
        saveDataDict(zipper.getPosition(),DataDict.POSITION);
        saveDataDict(zipper.getName(),DataDict.NAME);
        saveDataDict(zipper.getMaterial(),DataDict.MATERIAL);
        saveDataDict(zipper.getSpec(),DataDict.SPEC);
        saveDataDict(zipper.getModel(),DataDict.MODEL);
    }

    private void saveDataDict(String value,String type){
        value = StringUtils.trim(value);
        List<DataDict> d = dataDictDao.findByValueAndType(value, type);
        if(CollectionUtils.isEmpty(d)){
            DataDict dd = new DataDict();
            if(StringUtils.equals(DataDict.POSITION,type)){
                dd.setTypeName("部位");
            }else if(StringUtils.equals(DataDict.NAME,type)){
                dd.setTypeName("名称");
            }else if(StringUtils.equals(DataDict.MATERIAL,type)){
                dd.setTypeName("材质");
            }else if (StringUtils.equals(DataDict.SPEC,type)){
                dd.setTypeName("规格");
            }else if (StringUtils.equals(DataDict.MODEL,type)){
                dd.setTypeName("型号");
            }
            dd.setType(type);
            dd.setValue(value);
            dataDictDao.save(dd);
        }
    }
}
