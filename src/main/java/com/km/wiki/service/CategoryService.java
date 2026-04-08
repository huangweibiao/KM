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
     * @param wikiId     知识库ID
     * @param categoryDTO 分类数据传输对象
     * @return 创建后的分类实体
     */
    Category createCategory(Long wikiId, CategoryDTO categoryDTO);

    /**
     * 更新分类
     *
     * @param id          分类ID
     * @param categoryDTO 分类数据传输对象
     * @return 更新后的分类实体
     */
    Category updateCategory(Long id, CategoryDTO categoryDTO);

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
     * 获取知识库的分类树形列表
     *
     * @param wikiId 知识库ID
     * @return 分类树形列表
     */
    List List<CategoryVO> getCategoryTree(Long wikiId);

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
