package com.salmon.happyrmmall.mall.dao;

import com.salmon.happyrmmall.mall.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndOrderNo(@Param("userId")Integer userId,@Param("orderNo")Long orderNo);

    Order selectByOrderNo(Long orderNo);

    List<Order> selectByUserId(Integer userId);

    List<Order> selectAllOrder();

    List<Order> selectByStatusAndLessCreateTime(@Param("status") Integer status, @Param("createTime") Date createTime);

    //<![CDATA[   添加内容   ]]> 跳过xml检测
    int closeTimeoutOrder(Integer id);

    int selectOrderCount();
}