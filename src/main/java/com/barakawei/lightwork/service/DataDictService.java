package com.barakawei.lightwork.service;

import com.barakawei.lightwork.domain.DataDict;
import com.barakawei.lightwork.domain.Role;
import com.barakawei.lightwork.domain.Zipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Transactional
public interface DataDictService {

	void saveDataDict(DataDict dataDict);

    void saveDataDictByZipper(Zipper zipper);

	DataDict findDataDictById(String id);

    List<DataDict> findDataDictByType(String type,String value);

    DataDict findDataDict();
}
