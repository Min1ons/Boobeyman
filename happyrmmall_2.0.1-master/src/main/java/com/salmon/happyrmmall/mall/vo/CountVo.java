package com.salmon.happyrmmall.mall.vo;

import io.swagger.annotations.ApiModel;

/**
 * @date 2022/4/17 - 2:25
 * Created by Salmon
 */

@ApiModel
public class CountVo {

    private Integer userCount;

    private Integer productCount;

    private Integer orderCount;

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    @Override
    public String toString() {
        return "CountVo{" +
                "userCount=" + userCount +
                ", productCount=" + productCount +
                ", orderCount=" + orderCount +
                '}';
    }
}
