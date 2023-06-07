package com.salmon.happyrmmall.mall.controller.backend;

import com.salmon.happyrmmall.mall.common.Const;
import com.salmon.happyrmmall.mall.common.ResponseCode;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.pojo.User;
import com.salmon.happyrmmall.mall.service.IProductService;
import com.salmon.happyrmmall.mall.service.IStatisticService;
import com.salmon.happyrmmall.mall.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @date 2022/4/17 - 2:13
 * Created by Salmon
 */

@Api(tags = "StatisticManageController", description = "管理员-统计信息展示")
@RestController
@RequestMapping("/manage/statistic")
public class StatisticManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IStatisticService iStatisticService;

    @RequestMapping(value = "base_count.do",method = RequestMethod.GET)
    public ServerResponse base_count(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iStatisticService.getCountInformation();
        }else {
            return ServerResponse.createByErrorMessage("无操作权限，需要管理员权限");
        }
    }
}
