package com.barakawei.lightwork.service;

import com.barakawei.lightwork.domain.SearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.barakawei.lightwork.domain.Purchasing;

import java.util.List;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Transactional
public interface PurchasingService {

	
	void createPurchasing(Purchasing purchasing);


	Purchasing findPurchasingById(String id);


	void updatePurchasing(Purchasing purchasing);


	void deletePurchasingById(String id);
	
	
	Page<Purchasing> findPurchasings(SearchForm sf);

    void complete(Purchasing purchasing);

    Purchasing findTaskByCurrentUser(String id);

    List<Purchasing> findAll();

}
