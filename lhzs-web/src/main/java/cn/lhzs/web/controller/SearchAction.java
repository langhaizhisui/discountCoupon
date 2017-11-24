package cn.lhzs.web.controller;

import org.junit.Test;

import java.util.List;

/**
 * Created by IBA-EDV on 2017/11/23.
 */
public class SearchAction {

    private SearchService searchService = new SearchService();

    /**
     * 创建news索引
     */
    @Test
    public void buildSearchIndex() {
        searchService.builderSearchIndex();
    }

    /**
     * 搜索新闻
     */
    @Test
    public void searchNews() {
        String param = "个人";
        List<News> news = searchService.searchsNews(param);
        System.out.println("id   标题                                           内容");
        for (int i = 0; i < news.size(); i++) {
            News article = news.get(i);
            System.out.println(article.getId() + "   " + article.getTitle() + "   " + article.getContent());
        }
    }
}
