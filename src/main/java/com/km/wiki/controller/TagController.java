package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.service.TagService;
import com.km.wiki.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器
 * 处理标签相关的HTTP请求
 */
@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取知识库的标签列表
     *
     * @param wikiId 知识库ID
     * @return 标签列表
     */
    @GetMapping("/wikis/{wikiId}/tags")
    public Result<List<TagVO>> getTagsByWikiId(@PathVariable Long wikiId) {
        List<TagVO> tags = tagService.getTagsByWikiId(wikiId);
        return Result.success(tags);
    }

    /**
     * 创建标签
     *
     * @param wikiId  知识库ID
     * @param tagName 标签名称
     * @return 创建的标签
     */
    @PostMapping("/wikis/{wikiId}/tags")
    public Result<TagVO> createTag(@PathVariable Long wikiId, @RequestParam String tagName) {
        TagVO tag = tagService.createTag(wikiId, tagName);
        return Result.success(tag);
    }

    /**
     * 删除标签
     *
     * @param id 标签ID
     * @return 操作结果
     */
    @DeleteMapping("/tags/{id}")
    public Result<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return Result.success();
    }

    /**
     * 为页面添加标签
     *
     * @param id    页面ID
     * @param tagId 标签ID
     * @return 操作结果
     */
    @PostMapping("/pages/{id}/tags")
    public Result<Void> addTagToPage(@PathVariable Long id, @RequestParam Long tagId) {
        tagService.addTagToPage(id, tagId);
        return Result.success();
    }

    /**
     * 为页面移除标签
     *
     * @param id     页面ID
     * @param tagId  标签ID
     * @return 操作结果
     */
    @DeleteMapping("/pages/{id}/tags/{tagId}")
    public Result<Void> removeTagFromPage(@PathVariable Long id, @PathVariable Long tagId) {
        tagService.removeTagFromPage(id, tagId);
        return Result.success();
    }

    /**
     * 获取页面的标签列表
     *
     * @param id 页面ID
     * @return 标签列表
     */
    @GetMapping("/pages/{id}/tags")
    public Result<List<TagVO>> getTagsByPageId(@PathVariable Long id) {
        List<TagVO> tags = tagService.getTagsByPageId(id);
        return Result.success(tags);
    }
}
