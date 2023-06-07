package com.salmon.happyrmmall.mall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.salmon.happyrmmall.mall.common.Const;
import com.salmon.happyrmmall.mall.common.ResponseCode;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.pojo.Shipping;
import com.salmon.happyrmmall.mall.pojo.User;
import com.salmon.happyrmmall.mall.service.IShippingService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @date 2020-12-5 - 12:19
 * Created by Salmon
 */
@Api(tags = "ShippingController", description = "收货地址管理")
@RestController
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    /**
     * 添加收货地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    public ServerResponse add(HttpSession session, Shipping shipping){  //springmvc的对象绑定
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping);

    }

    /**
     * 删除收货地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "del.do",method = RequestMethod.DELETE)
    public ServerResponse del(HttpSession session, Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(), shippingId);

    }

    /**
     * 更新收货地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping(value = "update.do",method = RequestMethod.PUT)
    public ServerResponse update(HttpSession session, Shipping shipping){  //springmvc的对象绑定
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(), shipping);
    }

    /**
     * 查询收货地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping(value = "select.do",method = RequestMethod.GET)
    public ServerResponse<Shipping> select(HttpSession session, Integer shippingId){  //springmvc的对象绑定
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(), shippingId);

    }

    /**
     * 分页列表查询所有当前用户收货地址
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         HttpSession session){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(), pageNum, pageSize);
    }
















}
