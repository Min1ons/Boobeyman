package com.salmon.happyrmmall.mall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.service.IProductService;
import com.salmon.happyrmmall.mall.vo.ProductDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2020-12-3 - 20:12
 * Created by Salmon
 */
@Api(tags = "ProductController", description = "普通用户查看商品")
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;


    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    /**
     * 一个keyword、类别的模糊查询，
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @ApiOperation("分页keyword/categoryId模糊查询")
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy){

        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
