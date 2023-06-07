package com.salmon.happyrmmall.mall.service;

import com.github.pagehelper.PageInfo;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.vo.OrderVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @date 2020-12-5 - 16:09
 * Created by Salmon
 */


public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

    ServerResponse createOrder(Integer userId,Integer shippingId);

    ServerResponse<String> cancel(Integer userId,Long orderNo);

    //自动关闭超过24小时未付款的订单，返回关闭订单数量
    ServerResponse<Integer> closeTimeoutOrder();

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);


 //-------------------------manager----------------------------------------
    ServerResponse<PageInfo> manageList(int pageNum,int pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);

}
