package com.salmon.happyrmmall.mall;

import com.salmon.happyrmmall.mall.dao.OrderItemMapper;
import com.salmon.happyrmmall.mall.dao.OrderMapper;
import com.salmon.happyrmmall.mall.dao.ProductMapper;
import com.salmon.happyrmmall.mall.dao.UserMapper;
import com.salmon.happyrmmall.mall.pojo.Order;
import com.salmon.happyrmmall.mall.pojo.OrderItem;
import com.salmon.happyrmmall.mall.service.IOrderService;
import com.salmon.happyrmmall.mall.vo.CountVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class MallApplicationTests {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void set(){
        redisTemplate.opsForValue().set("Key1","Value1");
    }

    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    UserMapper userMapper;


    @Autowired
    OrderMapper orderMapper;

    @Test
    public void test(){
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(Long.valueOf("1491753014256"));
        for (OrderItem orderItem : orderItemList){
            System.out.println(productMapper.selectStockByProductId(orderItem.getProductId()));
        }
        iOrderService.cancel(1,Long.valueOf("1491753014256"));
    }

    @Test
    public void test1(){
        int userCount = userMapper.selectUserCount();
        int productCount = productMapper.selectProductCount();
        int orderCount = orderMapper.selectOrderCount();
        CountVo countVo = new CountVo();
        countVo.setUserCount(userCount);
        countVo.setProductCount(productCount);
        countVo.setOrderCount(orderCount);
        System.out.println(countVo);
    }
}
