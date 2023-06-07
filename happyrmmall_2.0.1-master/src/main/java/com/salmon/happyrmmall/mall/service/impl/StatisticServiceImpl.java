package com.salmon.happyrmmall.mall.service.impl;

import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.dao.OrderMapper;
import com.salmon.happyrmmall.mall.dao.ProductMapper;
import com.salmon.happyrmmall.mall.dao.UserMapper;
import com.salmon.happyrmmall.mall.pojo.Order;
import com.salmon.happyrmmall.mall.service.IStatisticService;
import com.salmon.happyrmmall.mall.vo.CountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 2022/4/17 - 2:21
 * Created by Salmon
 */
@Service("iStatisticService")
public class StatisticServiceImpl implements IStatisticService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderMapper orderMapper;

    @Override
    public ServerResponse getCountInformation() {
        int userCount = userMapper.selectUserCount();
        int productCount = productMapper.selectProductCount();
        int orderCount = orderMapper.selectOrderCount();
        CountVo countVo = new CountVo();
        countVo.setUserCount(userCount);
        countVo.setProductCount(productCount);
        countVo.setOrderCount(orderCount);
        return ServerResponse.createBySuccess(countVo);
    }
}
