package com.km.wiki.service.impl;

import com.km.wiki.dto.CategoryDTO;
import com.km.wiki.entity.Category;
import com.km.wiki.repository.CategoryRepository;
import com.km.wiki.repository.WikiRepository;
import com.km.wiki.service.CategoryService;
import com.km.wiki.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类服务实现类
 * 实现分类相关的业务逻辑
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WikiRepository wikiRepository;

    /**
     * 创建分类
     *
     * @param categoryDTO 分类创建信息
     * @return 创建的分类
     */
    @Override
    @Transactional
    public CategoryVO createCategory(CategoryDTO.CreateRequest categoryDTO) {
        // 检查知识库是否存在
        if (!wikiRepository.existsById(categoryDTO.getWikiId())) {
            throw new RuntimeException("知识库不存在");
        }

        // 检查slug是否在同一知识库中已存在
        if (categoryRepository.existsByWikiIdAndSlug(categoryDTO.getWikiId(), categoryDTO.getSlug())) {
            throw new RuntimeException("分类标识符在该知识库中已存在");
        }

        // 如果有父分类，检查父分类是否存在
        if (categoryDTO.getParentId() != null) {
            if (!categoryRepository.existsById(categoryDTO.getParentId())) {
                throw new RuntimeException("父分类不存在");
            }
        }

        // 创建分类
        Category category = new Category();
        category.setWikiId(categoryDTO.getWikiId());
        category.setParentId(categoryDTO.getParentId());
        category.setName(categoryDTO.getName());
        category.setSlug(categoryDTO.getSlug());
        category.setSortOrder(categoryDTO.getSortOrder() != null ? categoryDTO.getSortOrder() : 0);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        Category savedCategory = categoryRepository.save(category);
        return convertToVO(savedCategory);
    }

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类视图对象
     */
    @Override
    public CategoryVO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));
        return convertToVO(category);
    }

    /**
     * 获取知识库的分类列表（树形结构）
     *
     * @param wikiId 知识库ID
     * @return 分类树形列表
     */
    @Override
    public List<CategoryVO> getCategoryTree(Long wikiId) {
        List<Category> categories = categoryRepository.findByWikiIdOrderBySortOrderAsc(wikiId);
        return buildCategoryTree(categories, null);
    }

    /**
     * 获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @Override
    public List<CategoryVO> getChildCategories(Long parentId) {
        List<Category> categories = categoryRepository.findByParentIdOrderBySortOrderAsc(parentId);
        return categories.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 更新分类
     *
     * @param id          分类ID
     * @param categoryDTO 分类更新信息
     * @return 更新后的分类
     */
    @Override
    @Transactional
    public CategoryVO updateCategory(Long id, CategoryDTO.UpdateRequest categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));

        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        if (categoryDTO.getSlug() != null) {
            // 检查新的slug是否已存在
            if (!category.getSlug().equals(categoryDTO.getSlug()) &&
                    categoryRepository.existsByWikiIdAndSlug(category.getWikiId(), categoryDTO.getSlug())) {
                throw new RuntimeException("分类标识符在该知识库中已存在");
            }
            category.setSlug(categoryDTO.getSlug());
        }
        if (categoryDTO.getParentId() != null) {
            // 检查是否将自己设为父分类
            if (categoryDTO.getParentId().equals(id)) {
                throw new RuntimeException("不能将自己设为父分类");
            }
            // 检查父分类是否存在
            if (!categoryRepository.existsById(categoryDTO.getParentId())) {
                throw new RuntimeException("父分类不存在");
            }
            category.setParentId(categoryDTO.getParentId());
        }
        if (categoryDTO.getSortOrder() != null) {
            category.setSortOrder(categoryDTO.getSortOrder());
        }

        category.setUpdatedAt(LocalDateTime.now());
        Category updatedCategory = categoryRepository.save(category);
        return convertToVO(updatedCategory);
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));

        // 检查是否有子分类
        List<Category> children = categoryRepository.findByParentIdOrderBySortOrderAsc(id);
        if (!children.isEmpty()) {
            throw new RuntimeException("该分类下存在子分类，无法删除");
        }

        categoryRepository.delete(category);
    }

    /**
     * 根据ID获取分类
     *
     * @param id 分类ID
     * @return 分类实体
     */
    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));
    }

    /**
     * 获取分类详情
     *
     * @param id 分类ID
     * @return 分类视图对象
     */
    @Override
    public CategoryVO getCategoryDetail(Long id) {
        return getCategoryById(id);
    }

    /**
     * 移动分类
     *
     * @param id       分类ID
     * @param parentId 新的父分类ID
     */
    @Override
    @Transactional
    public void moveCategory(Long id, Long parentId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));

        // 检查新的父分类是否存在
        if (parentId != null && !categoryRepository.existsById(parentId)) {
            throw new RuntimeException("父分类不存在");
        }

        category.setParentId(parentId);
        category.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }

    /**
     * 构建分类树
     *
     * @param categories 分类列表
     * @param parentId   父分类ID
     * @return 树形结构
     */
    private List<CategoryVO> buildCategoryTree(List<Category> categories, Long parentId) {
        List<CategoryVO> tree = new ArrayList<>();

        for (Category category : categories) {
            if ((parentId == null && category.getParentId() == null) ||
                    (parentId != null && parentId.equals(category.getParentId()))) {
                CategoryVO vo = convertToVO(category);

                // 递归构建子树
                vo.setChildren(buildCategoryTree(categories, category.getId()));

                tree.add(vo);
            }
        }

        return tree;
    }

    /**
     * 将分类实体转换为视图对象
     *
     * @param category 分类实体
     * @return 分类视图对象
     */
    private CategoryVO convertToVO(Category category) {
        CategoryVO vo = new CategoryVO();
        vo.setId(category.getId());
        vo.setWikiId(category.getWikiId());
        vo.setParentId(category.getParentId());
        vo.setName(category.getName());
        vo.setSlug(category.getSlug());
        vo.setSortOrder(category.getSortOrder());
        vo.setCreatedAt(category.getCreatedAt());
        vo.setUpdatedAt(category.getUpdatedAt());
        return vo;
    }
}
