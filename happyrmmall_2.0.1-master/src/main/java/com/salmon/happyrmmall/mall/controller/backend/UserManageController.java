package com.salmon.happyrmmall.mall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.salmon.happyrmmall.mall.common.Const;
import com.salmon.happyrmmall.mall.common.ResponseCode;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.pojo.User;
import com.salmon.happyrmmall.mall.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @date 2020-12-2 - 12:38
 * Created by Salmon
 */
@Api(tags = "UserManageController", description = "管理员-用户管理")
@RestController
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登陆的是管理员
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登陆");
            }
        }
        return response;
    }

    /**
     * 查询所有用户信息
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iUserService.getUserList(pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无操作权限，需要管理员权限");
        }
    }
}
