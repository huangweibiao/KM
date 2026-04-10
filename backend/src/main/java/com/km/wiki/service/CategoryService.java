package com.km.wiki.service;

import com.km.wiki.dto.CategoryDTO;
import com.km.wiki.entity.Category;
import com.km.wiki.vo.CategoryVO;

import java.util.List;

/**
 * 分类服务接口
 * 定义分类相关的业务逻辑操作
 */
public interface CategoryService {

    /**
     * 创建分类
     *
     * @param categoryDTO 分类创建信息
     * @return 创建的分类视图
     */
    CategoryVO createCategory(CategoryDTO.CreateRequest categoryDTO);

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类视图对象
     */
    CategoryVO getCategoryById(Long id);

    /**
     * 获取知识库的分类列表（树形结构）
     *
     * @param wikiId 知识库ID
     * @return 分类树形列表
     */
    List<CategoryVO> getCategoryTree(Long wikiId);

    /**
     * 获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<CategoryVO> getChildCategories(Long parentId);

    /**
     * 更新分类
     *
     * @param id          分类ID
     * @param categoryDTO 分类更新信息
     * @return 更新后的分类视图
     */
    CategoryVO updateCategory(Long id, CategoryDTO.UpdateRequest categoryDTO);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void deleteCategory(Long id);

    /**
     * 根据ID获取分类
     *
     * @param id 分类ID
     * @return 分类实体
     */
    Category findById(Long id);

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类视图对象
     */
    CategoryVO getCategoryDetail(Long id);

    /**
     * 移动分类
     *
     * @param id       分类ID
     * @param parentId 新的父分类ID
     */
    void moveCategory(Long id, Long parentId);
}
