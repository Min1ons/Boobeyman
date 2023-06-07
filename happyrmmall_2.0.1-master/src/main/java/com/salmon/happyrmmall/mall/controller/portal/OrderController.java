package com.salmon.happyrmmall.mall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.salmon.happyrmmall.mall.common.Const;
import com.salmon.happyrmmall.mall.common.ResponseCode;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.pojo.User;
import com.salmon.happyrmmall.mall.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * @date 2020-12-5 - 16:06
 * Created by Salmon
 */
@Api(tags = "OrderController", description = "订单管理")
@RestController
@RequestMapping("/order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService iOrderService;


    //TODO 发现一个bug当购物车中商品被部分选择时，下单后依旧会清空全部购物车购买所有商品
    //已解决！
    /**
     * 创建订单
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "create.do",method = RequestMethod.POST)
    public ServerResponse create(HttpSession session, Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iOrderService.createOrder(user.getId(),shippingId);
    }

    /**
     * 取消订单
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancel.do",method = RequestMethod.DELETE)
    public ServerResponse cancel(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iOrderService.cancel(user.getId(), orderNo);
    }


    /**
     * 在订单确认页，获取购物车中已经被选中的商品信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_order_cart_product.do",method = RequestMethod.GET)
    public ServerResponse getOrderCartProduct(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iOrderService.getOrderCartProduct(user.getId());
    }

    /**
     * 获取某一个订单详情
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    public ServerResponse detail(HttpSession session,Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iOrderService.getOrderDetail(user.getId(), orderNo);
    }

    /**
     * 分页显示当前用户所有订单信息，用于我的订单页面
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ApiOperation("分页显示当前用户所有订单信息，用于我的订单页面")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return iOrderService.getOrderList(user.getId(), pageNum, pageSize);
    }








    /**
     * 支付函数
     * @param session
     * @param orderNo
     * @param request
     * @return
     */
    @RequestMapping(value = "pay.do",method = RequestMethod.POST)
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(orderNo, user.getId(), path);
    }

    /**
     * 支付宝回调函数
     * @param request
     * @return
     */
    @RequestMapping(value = "alipay_callback.do")
    public Object alipayCallback(HttpServletRequest request){
        logger.info("开始进行支付宝回调");

        Map<String,String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for(Iterator iter = requestParams.keySet().iterator();iter.hasNext();){
            String name = (String)iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for(int i = 0;i < values.length; i++){
                valueStr = (i == values.length -1)?valueStr + values[i]:valueStr + values[i]+",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        //验证回调的正确性，确定是支付宝发送的回调，且要避免重复通知
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8",Configs.getSignType());

            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求，验证不通过");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常", e);
            e.printStackTrace();
        }

        //todo 验证各种数据

        ServerResponse serverResponse = iOrderService.aliCallback(params);
        if(serverResponse.isSuccess()){
            logger.info("回调成功");
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }

        logger.info("回调失败");
        return Const.AlipayCallback.RESPONSE_FAILED;
    }


    /**
     * 查询支付状态
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "query_order_pay_status.do",method = RequestMethod.GET)
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(),orderNo);
        if(serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }











}
