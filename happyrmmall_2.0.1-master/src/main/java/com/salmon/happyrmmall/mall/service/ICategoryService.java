package com.salmon.happyrmmall.mall.service;

import com.salmon.happyrmmall.mall.common.ServerResponse;
import com.salmon.happyrmmall.mall.pojo.Category;

import java.util.List;

/**
 * @date 2020-12-2 - 17:40
 * Created by Salmon
 */

public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>>  selectCategoryAndChildrenById(Integer categoryId);
}
