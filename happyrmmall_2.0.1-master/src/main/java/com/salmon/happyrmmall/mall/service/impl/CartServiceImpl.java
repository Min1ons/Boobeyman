package com.salmon.happyrmmall.mall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.salmon.happyrmmall.mall.common.Const;
import com.salmon.happyrmmall.mall.common.ResponseCode;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.dao.CartMapper;
import com.salmon.happyrmmall.mall.dao.ProductMapper;
import com.salmon.happyrmmall.mall.pojo.Cart;
import com.salmon.happyrmmall.mall.pojo.Product;
import com.salmon.happyrmmall.mall.service.ICartService;
import com.salmon.happyrmmall.mall.util.BigDecimalUtil;
import com.salmon.happyrmmall.mall.util.PropertiesUtil;
import com.salmon.happyrmmall.mall.vo.CartProductVo;
import com.salmon.happyrmmall.mall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

/**
 * @date 2020-12-4 - 13:03
 * Created by Salmon
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 加入购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> add(Integer userId,Integer productId,Integer count){
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart == null){
            //此产品不在这个购物车里，需要新增一个此产品记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        }else {
            //此产品已存在购物车里，就产品数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }

        return this.list(userId);
    }

    /**
     * 更新购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count){
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart != null){
            cart.setQuantity(count);
            //修复了一个bug，商品在购物车中无法更新购买数量
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);
    }

    /**
     * 删除某用户购物车的产品
     * @param userId
     * @param productIds
     * @return
     */
    public ServerResponse<CartVo> deleteProduct(Integer userId,String productIds){
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId, productList);
        return this.list(userId);

    }

    /**
     * 查詢列表
     * @param userId
     * @return
     */
    public ServerResponse<CartVo> list (Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        if(cartVo == null){
            return ServerResponse.createByErrorMessage("购物车中的此商品不存在");
        }else {
            return ServerResponse.createBySuccess(cartVo);
        }
    }


    /**
     * 选择或者反选所有的
     * @param userId
     * @return
     */
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked){
        cartMapper.checkedOrUncheckedProduct(userId, productId,checked);
        return this.list(userId);
    }

    /**
     * 查询购物车商品数量
     * @param userId
     * @return
     */
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));

    }






        /**
         * 按照要求更新购物车，返回更新好的购物车vo对象
         * @param userId
         * @return
         */
        private CartVo getCartVoLimit(Integer userId){
            CartVo cartVo = new CartVo();
            List<Cart> cartList = cartMapper.selectCartByUserId(userId);
            List<CartProductVo> cartProductVoList = Lists.newArrayList();

            //解决空指针异常
            BigDecimal cartTotalPrice = new BigDecimal("0");


            if(CollectionUtils.isNotEmpty(cartList)){
                for(Cart cartItem : cartList){
                    CartProductVo cartProductVo = new CartProductVo();
                    cartProductVo.setId(cartItem.getId());
                    cartProductVo.setUserId(userId);
                    cartProductVo.setProductId(cartItem.getProductId());

                    Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                    if(product != null){
                        cartProductVo.setProductMainImage(product.getMainImage());
                        cartProductVo.setProductName(product.getName());
                        cartProductVo.setProductSubtitle(product.getSubtitle());
                        cartProductVo.setProductStatus(product.getStatus());
                        cartProductVo.setProductPrice(product.getPrice());
                        cartProductVo.setProductStock(product.getStock());
                        //判断库存
                        int buyLimitCount = 0;
                        if(product.getStock() >= cartItem.getQuantity()){
                            //库存充足的时候
                            buyLimitCount = cartItem.getQuantity();
                            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                        }else{
                            buyLimitCount = product.getStock();
                            cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                            //购物车中更新有效库存
                            Cart cartForQuantity = new Cart();
                            cartForQuantity.setId(cartItem.getId());
                            cartForQuantity.setQuantity(buyLimitCount);
                            cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                        }
                        cartProductVo.setQuantity(buyLimitCount);
                        //计算总价
                        cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                        cartProductVo.setProductChecked(cartItem.getChecked());
                    } else {
                        return null; //当前购物车中商品不存在
                    }

                    if(cartItem.getChecked() == Const.Cart.CHECKED){
                        //如果已经勾选,增加到整个的购物车总价中

                        //解决空指针异常
                        if(cartProductVo.getProductTotalPrice() == null){
                            return null;
                        }else {
                            cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                        }
                    }
                    cartProductVoList.add(cartProductVo);
                }
            }
            cartVo.setCartTotalPrice(cartTotalPrice);
            cartVo.setCartProductVoList(cartProductVoList);
            cartVo.setAllChecked(this.getAllCheckedStatus(userId));
            cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

            return cartVo;
        }

    /**
     * 查看是否为全选
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }

        //此查询查询的是：当userId确定时未勾选的购物车商品个数，所以为0时全选，不为0时不是全选
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }

















}
