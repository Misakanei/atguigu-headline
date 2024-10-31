package com.atguigu.headline.dao;

import com.atguigu.headline.pojo.NewsHeadline;
import com.atguigu.headline.pojo.vo.HeadlineDetailVo;
import com.atguigu.headline.pojo.vo.HeadlinePageVo;
import com.atguigu.headline.pojo.vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadLineDao {
    List<HeadlinePageVo> findPageList(HeadlineQueryVo headlineQueryVo);

    int findPageCount(HeadlineQueryVo headlineQueryVo);

    int incrPageViews(int hid);

    HeadlineDetailVo findHeadlineDetail(int hid);

    int addNewsHeadline(NewsHeadline newsHeadline);

    NewsHeadline findByHid(int hid);

    int update(NewsHeadline newsHeadline);
}


