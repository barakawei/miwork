package com.barakawei.lightwork.controller;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.barakawei.lightwork.dao.ZipperDao;
import com.barakawei.lightwork.domain.*;
import com.barakawei.lightwork.service.DataDictService;
import com.barakawei.lightwork.service.PurchasingDetailService;
import com.barakawei.lightwork.service.PurchasingService;
import com.barakawei.lightwork.util.ExcelParseUtil;
import com.barakawei.lightwork.util.UserContextUtil;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 请假控制器，包含保存、启动流程
 *
 * @author barakawei
 */
@Controller
@RequestMapping(value = "/purchasing")
public class PurchasingController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected PurchasingService purchasingService;

    @Autowired
    protected PurchasingDetailService purchasingDetailService;

    @Autowired
    protected DataDictService dataDictService;

    @Autowired
    protected ZipperDao zipperDao;


    @RequestMapping("/download/{id}")
    public void downloadExcel(HttpServletResponse response,@PathVariable("id") String id){
        Purchasing purchasing = purchasingService.findPurchasingById(id);
        List<PurchasingDetail> pds = purchasing.getPds();
        List<Zipper> zippers = purchasing.getZippers();
        List<Model> countDetailList = purchasing.getCountDetailList();
        List<GoodsGroups> ggs = new ArrayList<GoodsGroups>();
        List<ZipperGroups> zgs = new ArrayList<ZipperGroups>();
        Map<String,ZipperGroups> map = new LinkedHashMap<String,ZipperGroups>();
        String type = "";
        GoodsGroups gg = null;
        String role = UserContextUtil.getCurrentRole().getName();
        for(PurchasingDetail _pd : pds){
            if(!StringUtils.equals(type,_pd.getGoods().getType())){
                type = _pd.getGoods().getType();
                if(gg != null){
                    ggs.add(gg);
                }
                gg = new GoodsGroups();
                gg.setName(type);
            }
            if(gg != null){
                Goods goods = _pd.getGoods();
                goods.setWarehouseCount(_pd.getWarehouseCount());
                goods.setActualPurchasingCount(_pd.getActualPurchasingCount());
                goods.setExpectedArrivalTime(_pd.getExpectedArrivalTime());
                goods.setPlanEntryCount(_pd.getPlanEntryCount());
                goods.setPlanEntryTime(_pd.getPlanEntryTime());
                goods.setShrinkage(_pd.getShrinkage());
                goods.setOrderCount(_pd.getGoods().getOrderCount());
                goods.setPurchasingCount(_pd.getPlanPurchasingCount());
                goods.setDescription(_pd.getSpecialRequirements());
                if("role_technolog".equals(role) || "role_quality".equals(role)||"role_product".equals(role)||"role_normal".equals(role)){
                    goods.setPrice("");
                    goods.setOriPrice("");
                }
                gg.getGoods().add(goods);
            }
        }
        if(gg != null){
            ggs.add(gg);
        }

        //zipper
        String zipperType = "";
        ZipperGroups zg = null;
        ZipperChest chest =null;
        ZipperGroups zipper = new ZipperGroups();
        if(countDetailList == null){
            countDetailList =new ArrayList<Model>();
        }
        for(Model _m : countDetailList){
            if(!StringUtils.equals(zipperType,_m.getPosition())){
                zipperType = _m.getPosition();
                zg = new ZipperGroups();
                map.put(zipperType,zg);
                zg.setName(zipperType);
                if("model_145".equals(_m.getType())){
                    zg.setModel_145(_m.getValue());
                    zipper.setModel_145(_m.getName());
                }
            }else{
            	if(_m.getChest()==1){
            		chest = zg.getChest();
            		chest.setName("净胸围");
	                if("model_145".equals(_m.getType())){
	                	chest.setModel_145(_m.getValue());
	                }else if("model_155".equals(_m.getType())){
	                	chest.setModel_155(_m.getValue());	                 
	                }else if("model_165".equals(_m.getType())){
	                	chest.setModel_165(_m.getValue());
	                  
	                }else if("model_175".equals(_m.getType())){
	                	chest.setModel_175(_m.getValue());
	                    
	                }else if("model_185".equals(_m.getType())){
	                	chest.setModel_185(_m.getValue());
	                   
	                }else if("model_195".equals(_m.getType())){
	                	chest.setModel_195(_m.getValue());
	                   
	                }else if("model_205".equals(_m.getType())){
	                	chest.setModel_205(_m.getValue());
	                   
	                }
	                
            		
            	}else{
	                if("model_145".equals(_m.getType())){
	                    zg.setModel_145(_m.getValue());
	                    zipper.setModel_145(_m.getName());
	                }else if("model_150".equals(_m.getType())){
	                    zg.setModel_150(_m.getValue());
	                    zipper.setModel_150(_m.getName());
	                }else if("model_155".equals(_m.getType())){
	                    zg.setModel_155(_m.getValue());
	                    zipper.setModel_155(_m.getName());
	                }else if("model_160".equals(_m.getType())){
	                    zg.setModel_160(_m.getValue());
	                    zipper.setModel_160(_m.getName());
	                }else if("model_165".equals(_m.getType())){
	                    zg.setModel_165(_m.getValue());
	                    zipper.setModel_165(_m.getName());
	                }else if("model_170".equals(_m.getType())){
	                    zg.setModel_170(_m.getValue());
	                    zipper.setModel_170(_m.getName());
	                }else if("model_175".equals(_m.getType())){
	                    zg.setModel_175(_m.getValue());
	                    zipper.setModel_175(_m.getName());
	                }else if("model_180".equals(_m.getType())){
	                    zg.setModel_180(_m.getValue());
	                    zipper.setModel_180(_m.getName());
	                }else if("model_185".equals(_m.getType())){
	                    zg.setModel_185(_m.getValue());
	                    zipper.setModel_185(_m.getName());
	                }else if("model_190".equals(_m.getType())){
	                    zg.setModel_190(_m.getValue());
	                    zipper.setModel_190(_m.getName());
	                }else if("model_195".equals(_m.getType())){
	                    zg.setModel_195(_m.getValue());
	                    zipper.setModel_195(_m.getName());
	                }else if("model_200".equals(_m.getType())){
	                    zg.setModel_200(_m.getValue());
	                    zipper.setModel_200(_m.getName());
	                }else if("model_205".equals(_m.getType())){
	                    zg.setModel_205(_m.getValue());
	                    zipper.setModel_205(_m.getName());
	                }
            	}

            }
        }

        for(int i=0;i<zippers.size();i++){
            Zipper z = zippers.get(i);
            ZipperExcel zx = new ZipperExcel();
            zx.setMaterial(z.getMaterial());
            zx.setName(z.getName());
            zx.setSpec(z.getSpec());
            zx.setType(z.getPosition());
            List<Model> zipperCountList = z.getZipperCountList();
            for(int j=0;j<zipperCountList.size();j++){
                Model _m = zipperCountList.get(j);
                if(z.getChest()==1){
	                if(j == 0){
	                    zx.setModel_145(_m.getValue());
	                }else if(j == 1){
	                    zx.setModel_155(_m.getValue());
	                }else if(j == 2){
	                    zx.setModel_165(_m.getValue());
	                }else if(j == 3){
	                    zx.setModel_175(_m.getValue());
	                }else if(j == 4){
	                    zx.setModel_185(_m.getValue());
	                }else if(j == 5){
	                    zx.setModel_195(_m.getValue());
	                }else if(j == 6){
	                    zx.setModel_205(_m.getValue());
	                }
                }else{
                	if(j == 0){
	                    zx.setModel_145(_m.getValue());
	                }else if(j == 1){
	                    zx.setModel_150(_m.getValue());
	                }else if(j == 2){
	                    zx.setModel_155(_m.getValue());
	                }else if(j == 3){
	                    zx.setModel_160(_m.getValue());
	                }else if(j == 4){
	                    zx.setModel_165(_m.getValue());
	                }else if(j == 5){
	                    zx.setModel_170(_m.getValue());
	                }else if(j == 6){
	                    zx.setModel_175(_m.getValue());
	                }else if(j == 7){
	                    zx.setModel_180(_m.getValue());
	                }else if(j == 8){
	                    zx.setModel_185(_m.getValue());
	                }else if(j == 9){
	                    zx.setModel_190(_m.getValue());
	                }else if(j == 10){
	                    zx.setModel_195(_m.getValue());
	                }else if(j == 11){
	                    zx.setModel_200(_m.getValue());
	                }else if(j == 12){
	                    zx.setModel_205(_m.getValue());
	                }
                }
            }
            ZipperGroups _zg =  map.get(zx.getType());
            if(_zg != null){
            	if(z.getChest()==1){
            		_zg.getChest().getZipper().add(zx);
            	}else{
            		_zg.getZipper().add(zx);
            	}
            }else{
                ZipperGroups newZg = new ZipperGroups();
                newZg.setName(zx.getType());
                
                if(z.getChest()==1){
                	newZg.getChest().getZipper().add(zx);
            	}else{
            		newZg.getZipper().add(zx);
            	}
                
                map.put(zx.getType(),newZg);
            }
        }
        for(String key : map.keySet()){
            zgs.add(map.get(key));
        }
        Map beans = new HashMap();
        beans.put("purchasing", purchasing);
        beans.put("zipper", zipper);
        beans.put("goodsGroups", ggs);
        beans.put("zipperGroups", zgs);
        SimpleDateFormat sdf =new SimpleDateFormat("MM-dd");
        String date =sdf.format(new Date());
        String fileName = purchasing.getFileName();
        if(StringUtils.isBlank(fileName)){
            fileName = "采购计划_"+date+".xls";
        }
        String template ="purchasing.xls";
        String classpathResourceUrl = "classpath:/" + template;
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(classpathResourceUrl);
        XLSTransformer transformer=new XLSTransformer();

        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            fileName = fileName.replaceAll("%2B","+");
            fileName = fileName.replaceAll("%20"," ");
            fileName = fileName.replaceAll("%2F","/");
            fileName = fileName.replaceAll("%3F","?");
            fileName = fileName.replaceAll("%25","%");
            fileName = fileName.replaceAll("%23","#");
            fileName = fileName.replaceAll("%26","&");
            fileName = fileName.replaceAll("%3D","=");
            fileName = fileName.replaceAll("%3B","；");
            fileName = fileName.replaceAll("%28","(");
            fileName = fileName.replaceAll("%29",")");
            fileName = fileName.replaceAll("%2A","*");
            fileName = fileName.replaceAll("%2D","-");
            fileName = fileName.replaceAll("%2C",",");
            fileName = fileName.replaceAll("%3A",":");
            fileName = fileName.replaceAll("%5F","_");
            fileName = fileName.replaceAll("%5C","\\");
            fileName = fileName.replaceAll("%7E","~");
            fileName = fileName.replaceAll("%5B","[");
            fileName = fileName.replaceAll("%5D","]");
            fileName = fileName.replaceAll("%7C","|");
            HSSFWorkbook workbook=(HSSFWorkbook)transformer.transformXLS(resource.getInputStream(), beans);
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            ServletOutputStream out=response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidFormatException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(MultipartFile file) {
        List<Goods> goodses = ExcelParseUtil.upload(file);
        Purchasing purchasing = new Purchasing();
        purchasing.convertFromExcel(goodses);
        purchasing.setFileName(file.getOriginalFilename());
        purchasingService.createPurchasing(purchasing);
        return this.ajaxDoneClose("导入成功");
    }


    @RequestMapping(value = "/uploadZipper/{id}", method = RequestMethod.POST)
    public ModelAndView uploadZipper(MultipartFile file,@PathVariable("id") String id) {
        Purchasing purchasing = purchasingService.findPurchasingById(id);
        List<ZipperExcel> data = ExcelParseUtil.parseZipper(file);
        List<Zipper> zippers = new ArrayList<Zipper>();
        ZipperExcel.convertFromExcel(purchasing,zippers,data);
        zipperDao.deleteByPurchasing(id);
        for (Zipper _zipper : zippers) {
            zipperDao.save(_zipper);
            dataDictService.saveDataDictByZipper(_zipper);
        }
        return this.ajaxDoneClose("导入成功");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public  String upload() {
        return "purchasing/upload";
    }

    @RequestMapping(value = "/upload/{id}", method = RequestMethod.GET)
    public  ModelAndView uploadZipper(@PathVariable("id") String id) {
        ModelAndView mav = new ModelAndView("purchasing/uploadZipper");
        mav.addObject("purchasingId",id);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {

        return "purchasing/add";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(Purchasing purchasing) {
        purchasingService.createPurchasing(purchasing);
        return this.ajaxDoneClose("添加成功");
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView list(SearchForm sf) {
        ModelAndView mav = new ModelAndView("purchasing/list");
        if(!sf.getSearchMap().containsKey("ongoing")){
            sf.getSearchMap().put("ongoing","1");
        }
        Page<Purchasing> purchasings = purchasingService.findPurchasings(sf);



        mav.addObject("purchasings", purchasings);
        mav.addObject("purchasingList", purchasings.getContent());
        mav.addObject("searchForm", sf);
        return mav;
    }


    @RequestMapping(value = "/task")
    public ModelAndView task(SearchForm sf) {
        ModelAndView mav = new ModelAndView("purchasing/task");
        Page<Purchasing> purchasings = purchasingService.findPurchasings(sf);
        mav.addObject("purchasings", purchasings);
        mav.addObject("searchForm", sf);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        ModelAndView mav = new ModelAndView("purchasing/edit");
        Purchasing purchasing = purchasingService.findPurchasingById(id);
        mav.addObject("purchasing", purchasing);
        return mav;
    }
    private Map group(List<Zipper> list){
    	LinkedMap map = new LinkedMap();
    	for(Zipper z:list){
    		String key = z.getPosition();
    		if(map.containsKey(key)){
    			((List<Zipper>)map.get(key)).add(z);
    		}else{
    			List l =new ArrayList<Zipper>();
    			l.add(z);
    			map.put(key,l );
    		}
    	}
    	return map;
    }
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("id") String id) {
        ModelAndView mav = new ModelAndView("purchasing/show");
        Purchasing p =  purchasingService.findTaskByCurrentUser(id);
        List<Zipper> zippers = p.getZippers();
    	List<Zipper> chest = new ArrayList<Zipper>();
    	List<Zipper> noChest = new ArrayList<Zipper>();
    	for(Zipper z :zippers){
    		if(z.getChest()==1){
    			chest.add(z);
    		}else{
    			noChest.add(z);
    		}
    	}
    	Map noChestMap = group(noChest);
    	Map chestMap = group(chest);
    	List<Zipper> all = new ArrayList();
    	for(Object k : noChestMap.keySet()){
    		all.addAll((List)noChestMap.get(k));
    		Object o = chestMap.get(k);
    		if(o!=null){
    			all.addAll((List)o);
    		}
    		
    	}
        p.setZippers(all);

        mav.addObject("purchasing",p);
        return mav;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Purchasing purchasing) {
        purchasingService.updatePurchasing(purchasing);
        return this.ajaxDoneSuccess("修改成功");
    }

    @RequestMapping(value = "completePurchasing", method = {RequestMethod.POST})
    public ModelAndView completePurchasing(Purchasing purchasing) {
        purchasingService.complete(purchasing);
        return this.ajaxDoneSuccess("修改成功");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("id") String id) {
        purchasingService.deletePurchasingById(id);
        return this.ajaxDoneSuccess("删除成功");
    }
//	/**
//	 * 读取运行中的流程实例
//	 * @return
//	 */
//  @RequestMapping(value = "list/running")
//  public ModelAndView runningList() {
//    ModelAndView mav = new ModelAndView("/oa/leave/running");
//    List<Leave> results = workflowService.findRunningProcessInstaces();
//    mav.addObject("leaves", results);
//    return mav;
//  }
//
//	/**
//	 * 读取运行中的流程实例
//	 * @return
//	 */
//  @RequestMapping(value = "list/finished")
//  public ModelAndView finishedList() {
//    ModelAndView mav = new ModelAndView("/oa/leave/finished");
//    List<Leave> results = workflowService.findFinishedProcessInstaces();
//    mav.addObject("leaves", results);
//    return mav;
//  }
//
//	/**
//	 * 签收任务
//	 */
//  @RequestMapping(value = "task/claim/{id}")
//  public String claim(@PathVariable("id") String taskId, HttpSession session, RedirectAttributes redirectAttributes) {
//    String userId = UserUtil.getUserFromSession(session).getId();
//    taskService.claim(taskId, userId);
//    redirectAttributes.addFlashAttribute("message", "任务已签收");
//    return "redirect:/oa/leave/list/task";
//  }
//
//	/**
//	 * 读取详细数据
//	 * @param id
//	 * @return
//	 */
//  @RequestMapping(value = "detail/{id}")
//  @ResponseBody
//  public Leave getLeave(@PathVariable("id") Long id) {
//    Leave leave = leaveManager.getLeave(id);
//    return leave;
//  }
//
//	/**
//	 * 读取详细数据
//	 * @param id
//	 * @return
//	 */
//  @RequestMapping(value = "detail-with-vars/{id}/{taskId}")
//  @ResponseBody
//  public Leave getLeaveWithVars(@PathVariable("id") Long id, @PathVariable("taskId") String taskId) {
//    Leave leave = leaveManager.getLeave(id);
//    Map<String, Object> variables = taskService.getVariables(taskId);
//    leave.setVariables(variables);
//    return leave;
//  }
//

}
