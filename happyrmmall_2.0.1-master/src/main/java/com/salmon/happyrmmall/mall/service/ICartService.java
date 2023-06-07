package com.salmon.happyrmmall.mall.service;

import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.vo.CartVo;

/**
 * @date 2020-12-4 - 13:03
 * Created by Salmon
 */
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVo> list (Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
