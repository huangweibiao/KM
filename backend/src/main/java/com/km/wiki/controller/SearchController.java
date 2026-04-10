package com.km.wiki.controller;

import com.km.wiki.common.Result;
import com.km.wiki.service.SearchService;
import com.km.wiki.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 搜索控制器
 * 处理知识库搜索相关接口
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    /**
     * 关键词搜索
     *
     * @param keyword 搜索关键词
     * @param wikiId  知识库ID（可选）
     * @param pageable 分页参数
     * @return 搜索结果
     */
    @GetMapping("/search")
    public Result<Page<PageVO>> search(
            @RequestParam String keyword,
            @RequestParam(required = false) Long wikiId,
            Pageable pageable) {
        logger.info("搜索关键词: {}, 知识库: {}", keyword, wikiId);
        Page<PageVO> result = searchService.search(keyword, wikiId, pageable);
        return Result.success(result);
    }

    /**
     * 搜索建议
     *
     * @param keyword 搜索关键词
     * @return 建议列表
     */
    @GetMapping("/search/suggest")
    public Result<List<String>> suggest(@RequestParam String keyword) {
        List<String> suggestions = searchService.suggest(keyword);
        return Result.success(suggestions);
    }
}
