package com.salmon.happyrmmall.mall.service;

import com.github.pagehelper.PageInfo;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.pojo.User;

/**
 * @date 2020-12-1 - 13:15
 * Created by Salmon
 */

public interface IUserService {

     ServerResponse<User> login(String username, String password);

     ServerResponse<String> register(User user);

     ServerResponse<String> checkValid(String str,String type);

     ServerResponse selectQuestion(String username);

     ServerResponse<String> checkAnswer(String username,String question,String answer);

     ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

     ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

     ServerResponse<User> updateInformation(User user);

     ServerResponse<User> getInformation(Integer userId);

     ServerResponse checkAdminRole(User user);

     ServerResponse<PageInfo> getUserList(int pageNum, int pageSize);

}
