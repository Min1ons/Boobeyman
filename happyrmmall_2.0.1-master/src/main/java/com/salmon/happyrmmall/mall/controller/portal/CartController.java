package com.salmon.happyrmmall.mall.controller.portal;

import com.salmon.happyrmmall.mall.common.Const;
import com.salmon.happyrmmall.mall.common.ResponseCode;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.pojo.User;
import com.salmon.happyrmmall.mall.service.ICartService;
import com.salmon.happyrmmall.mall.vo.CartVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @date 2020-12-4 - 12:59
 * Created by Salmon
 */
@Api(tags = "CartController", description = "购物车管理")
@RestController
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    /**
     * 返回当前用户购物车中所有商品
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse<CartVo> list(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    /**
     * 增加购物车
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), productId, count);
    }

    /**
     * 更新购物车
     * @param session
     * @param count
     * @param productId
     * @return
     */
    @RequestMapping(value = "update.do",method = RequestMethod.PUT)
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count);
    }

    /**
     * 刪除數據
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping(value = "delete_product.do",method = RequestMethod.DELETE)
    public ServerResponse<CartVo> deleteProduct(HttpSession session, String productIds){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    /**
     * 全选
     * @param session
     * @return
     */
    @RequestMapping(value = "select_all.do",method = RequestMethod.POST)
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),null,Const.Cart.CHECKED);
    }

    /**
     * 全部反选
     * @param session
     * @return
     */
    @RequestMapping(value = "un_select_all.do",method = RequestMethod.POST)
    public ServerResponse<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }

    /**
     * 单个商品单选
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    public ServerResponse<CartVo> select(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    /**
     * 单个商品反选
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "un_select.do",method = RequestMethod.POST)
    public ServerResponse<CartVo> unSelect(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    /**
     * 查询购物车商品数量
     * @param session
     * @return
     */
    @RequestMapping(value = "get_cart_product_count.do",method = RequestMethod.GET)
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }

}
