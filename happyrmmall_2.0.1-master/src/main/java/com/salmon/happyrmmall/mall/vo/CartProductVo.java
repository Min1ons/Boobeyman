package com.salmon.happyrmmall.mall.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 将product表与cart表结合起来
 * @date 2020-12-4 - 13:18
 * Created by Salmon
 */

@ApiModel
public class CartProductVo implements Serializable {

    //结合了产品和购物车的一个抽象对象
    private Integer Id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private String productName;
    private String ProductSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;

    private BigDecimal productTotalPrice;
    //如果这里不添加new BigDecimal("0")，
    // 则当找不到购物车里的商品id时会出错，比如管理员账户ID
    // cartServiceImol的getCartVoLimit方法里cartTotalPrice会报空指针异常！！！

    private Integer productStock;
    private Integer productChecked;//此商品是否勾选

    private String limitQuantity;//限制数量的一个返回结果

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return ProductSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        ProductSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
