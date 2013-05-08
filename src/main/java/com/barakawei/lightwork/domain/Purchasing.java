package com.barakawei.lightwork.domain;

import java.util.*;
import javax.persistence.*;

import com.barakawei.lightwork.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author qianxiaowei
 * @date 2013-1-9
 */
@Entity
public class Purchasing {

    public static final String FLOW = "purchasing-flow";
    public static final boolean canFlow  = false;

    @Id
    @GeneratedValue(generator = "system_uuid")
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    private String id;

    //计划人
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "planning_user_id")
    private User planningUser;

    //计划人名字
    private String planningUserName;

    public String getPlanningUserName() {
        return planningUserName;
    }

    public void setPlanningUserName(String planningUserName) {
        this.planningUserName = planningUserName;
    }

    //排料确认
    private String dischargeRecognition;

    //订单名称
    private String orderName;

    //订单编号
    private String orderNumber;

    //序列号
    private String serialNumber;

    //订单数量
    private String orderCount;

    //状态
    private Boolean ongoing;

    //面料实际缩率
    private String actualShrinkage;

    //拉链缩率
    private String zipperShrinkage;

    //单位
    private String company;

    //款号
    private String typeNumber;

    //各个尺寸套数
    @Column(name="count_detail", columnDefinition = "text")
    private String countDetail;

    @Transient
    private List<Model> countDetailList = new ArrayList<Model>();

    //进度
    @Transient
    private Integer progress;

    @Transient
    private String type;

    //面辅料供应时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;


    //确认时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confirmTime;

    //采购预排
    private String planDischarge;

    //计划日期
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planDate;

    //排料核准
    private String confirmName;

    //核准日期
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confirmDate;

    public String getPlanDischarge() {
        return planDischarge;
    }

    public void setPlanDischarge(String planDischarge) {
        this.planDischarge = planDischarge;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getConfirmName() {
        return confirmName;
    }

    public void setConfirmName(String confirmName) {
        this.confirmName = confirmName;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    //开始时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    //结束时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    //采购明细
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "purchasing")
    List<PurchasingDetail> pds = new ArrayList<PurchasingDetail>();

    //拉链明细
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "purchasing")
    List<Zipper> zippers = new ArrayList<Zipper>();

    //待办任务
    @Transient
    List<PurchasingDetail> toDoList = new ArrayList<PurchasingDetail>();

    @Transient
    int pending;

    @Transient
    int complete;

    public String getCountDetail() {
        return countDetail;
    }

    public void setCountDetail(String countDetail) {
        this.countDetail = countDetail;
    }

    public List<Model> getCountDetailList() {
        this.countDetailList = JsonUtil.json2GenericObject(this.countDetail,new TypeReference<List<Model>>(){
        });
        if(this.countDetailList == null || this.countDetailList.isEmpty()){
            List<Model> l = new ArrayList<Model>();
            for(int i=0;i<12;i++){
                l.add(new Model());
            }
        }
        return countDetailList;
    }

    public void setCountDetailList(List<Model> countDetailList) {
        this.countDetailList = countDetailList;
        this.countDetail = JsonUtil.Obj2Json(countDetailList);
    }

    //已完成任务
    @Transient
    List<PurchasingDetail> completedList = new ArrayList<PurchasingDetail>();

    public Integer getProgress() {
        if (this.pds.size() > 0) {
            return complete * 100 / this.pds.size();
        }
        System.out.println(complete);
        return 100;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public List<Zipper> getZippers() {
        return zippers;
    }

    public void setZippers(List<Zipper> zippers) {
        this.zippers = zippers;
    }

    public List<PurchasingDetail> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<PurchasingDetail> toDoList) {
        this.toDoList = toDoList;
    }

    public List<PurchasingDetail> getCompletedList() {
        List<PurchasingDetail> completed = new ArrayList<PurchasingDetail>();
        for (PurchasingDetail pd : this.pds) {
            if (null != pd.getEndTime()) {
                completed.add(pd);
            }
        }
        return completed;
    }

    public void setCompletedList(List<PurchasingDetail> completedList) {
        this.completedList = completedList;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public List<PurchasingDetail> getPds() {
        return pds;
    }

    public void setPds(List<PurchasingDetail> pds) {
        this.pds = pds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getPlanningUser() {
        return planningUser;
    }

    public void setPlanningUser(User planningUser) {
        this.planningUser = planningUser;
    }

    public String getDischargeRecognition() {
        return dischargeRecognition;
    }

    public void setDischargeRecognition(String dischargeRecognition) {
        this.dischargeRecognition = dischargeRecognition;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getActualShrinkage() {
        return actualShrinkage;
    }

    public void setActualShrinkage(String actualShrinkage) {
        this.actualShrinkage = actualShrinkage;
    }

    public String getZipperShrinkage() {
        return zipperShrinkage;
    }

    public void setZipperShrinkage(String zipperShrinkage) {
        this.zipperShrinkage = zipperShrinkage;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(String typeNumber) {
        this.typeNumber = typeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void convertFromExcel(List<Goods> goodses) {
        for (int i = 0; i < goodses.size(); i++) {
            if (i == 0) {
                this.orderName = goodses.get(i).getSpecification();
                continue;
            }
            if (i == 1) {
                this.orderNumber = goodses.get(i).getSpecification();
                this.serialNumber = goodses.get(i).getWidth();
                this.typeNumber = goodses.get(i).getColor();
                continue;
            }
            if (i == 2) {
                this.orderCount= goodses.get(i).getSpecification();
                continue;
            }
            String firstColumn = goodses.get(i).getName();
            if(null != firstColumn && firstColumn.contains("面辅料供应时间") && i >= 4){
                Date date = null;
                try{
                    if(StringUtils.isNotBlank(goodses.get(i).getSpecification())){
                        date = new Date(goodses.get(i).getSpecification());
                    }
                }catch (Exception e){
                }
                this.applyTime = date;
                continue;
            }
            if(null != firstColumn && firstColumn.contains("计划") && i >= 4){
                this.planningUserName = goodses.get(i).getSpecification();
                //this.planDischarge = goodses.get(i).getWidth();
                Date date = null;
                try{
                    if(StringUtils.isNotBlank(goodses.get(i).getWidth())){
                        date = new Date(goodses.get(i).getWidth());
                    }
                }catch(Exception e){
                }
                this.planDate = date;
                continue;
            }
            if(null != firstColumn && firstColumn.contains("排料核准") && i >= 4){
                //this.dischargeRecognition= goodses.get(i).getSpecification();
                this.confirmName= goodses.get(i).getSpecification();
                Date date = null;
                try{
                    if(StringUtils.isNotBlank(goodses.get(i).getWidth())){
                        date = new Date(goodses.get(i).getWidth());
                    }
                }catch(Exception e){
                }
                this.confirmDate=date;
                break;
            }
            if (null!=firstColumn && !firstColumn.contains("面辅料供应时间") && i >= 4) {
                if (goodses.get(i).getName() != null &&
                        goodses.get(i).getSpecification() == null &&
                        goodses.get(i).getColor() == null &&
                        goodses.get(i).getComposition() == null &&
                        goodses.get(i).getConsume() == null &&
                        goodses.get(i).getUnit() == null &&
                        goodses.get(i).getLoss() == null &&
                        goodses.get(i).getPurchasingCount() == null &&
                        goodses.get(i).getType() == null) {
                    type = goodses.get(i).getName();
                    continue;
                }

                Goods goods = new Goods();
                PurchasingDetail pd = new PurchasingDetail();
                BeanUtils.copyProperties(goodses.get(i),goods);
                //goods.setActualConsume(goods.getConsume());
                goods.setType(type);
                pd.setGoods(goods);
                pd.setPlanPurchasingCount(goods.getPurchasingCount());
                pd.setOrderCount(goods.getOrderCount());
                pd.setSpecialRequirements(goods.getDescription());
                this.getPds().add(pd);
            }

        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Purchasing p = (Purchasing) obj;

        return StringUtils.equals(this.id, p.getId());

    }

    public void sort(){
        Map data = new LinkedHashMap();
        for(PurchasingDetail pd:this.getPds()){
            if(!data.containsKey(pd.getGoods().getType())){
                List typeList = new ArrayList();
                typeList.add(pd);
                data.put(pd.getGoods().getType(),typeList);
            }else {
                ((List)data.get(pd.getGoods().getType())).add(pd);
            }
        }
        List pds = new ArrayList();
        Iterator i = data.keySet().iterator();
        while(i.hasNext()){
            pds.addAll((List)data.get(i.next()));
        }
        this.setPds(pds);
    }



    public void sortZipper(){
        Map data = new LinkedHashMap();
        for(Zipper z:this.getZippers()){
            if(!data.containsKey(z.getPosition())){
                List typeList = new ArrayList();
                typeList.add(z);
                data.put(z.getPosition(),typeList);
            }else {
                ((List)data.get(z.getPosition())).add(z);
            }
        }
        List zippers = new ArrayList();
        Iterator i = data.keySet().iterator();
        while(i.hasNext()){
            zippers.addAll((List) data.get(i.next()));
        }
        this.setZippers(zippers);
    }
}

