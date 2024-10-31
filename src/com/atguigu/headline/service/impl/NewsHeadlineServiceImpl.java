package com.atguigu.headline.service.impl;

import com.atguigu.headline.dao.NewsHeadLineDao;
import com.atguigu.headline.dao.impl.NewsHeadlineDaoImpl;
import com.atguigu.headline.pojo.NewsHeadline;
import com.atguigu.headline.pojo.vo.HeadlineDetailVo;
import com.atguigu.headline.pojo.vo.HeadlinePageVo;
import com.atguigu.headline.pojo.vo.HeadlineQueryVo;
import com.atguigu.headline.service.NewsHeadlineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {
    private final NewsHeadLineDao headLineDao = new NewsHeadlineDaoImpl();

    @Override
    public Map<Object, Object> findPage(HeadlineQueryVo headlineQueryVo) {
        int pageNum = headlineQueryVo.getPageNum();
        int pageSize = headlineQueryVo.getPageSize();
        List<HeadlinePageVo> pageData = headLineDao.findPageList(headlineQueryVo);
        int totalSize = headLineDao.findPageCount(headlineQueryVo);
        int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
        Map<Object, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageNum", pageNum);
        pageInfo.put("pageSize", pageSize);
        pageInfo.put("pageData", pageData);
        pageInfo.put("totalSize", totalSize);
        pageInfo.put("totalPage", totalPage);
        return pageInfo;
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail(int hid) {
        // 修改头条的浏览量
        headLineDao.incrPageViews(hid);
        // 查询头条的详情
        return headLineDao.findHeadlineDetail(hid);
    }

    @Override
    public int addNewsHeadline(NewsHeadline newsHeadline) {
        return headLineDao.addNewsHeadline(newsHeadline);
    }

    @Override
    public NewsHeadline findByHid(int hid) {
        return headLineDao.findByHid(hid);
    }

    @Override
    public int update(NewsHeadline newsHeadline) {
        return headLineDao.update(newsHeadline);
    }
}
