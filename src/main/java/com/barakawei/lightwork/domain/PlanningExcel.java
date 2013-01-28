package com.barakawei.lightwork.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-1-27
 * Time: 上午8:31
 * To change this template use File | Settings | File Templates.
 */
public class PlanningExcel {
    //计划人
    private String planningUser;

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

    //面料实际缩率
    private String actualShrinkage;

    //拉链缩率
    private String zipperShrinkage;

    //供应时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date applyTime;


    //确认时间
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date confirmTime;
}
