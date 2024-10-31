package com.atguigu.headline.service;

import com.atguigu.headline.pojo.NewsHeadline;
import com.atguigu.headline.pojo.vo.HeadlineDetailVo;
import com.atguigu.headline.pojo.vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    Map<Object, Object> findPage(HeadlineQueryVo headlineQueryVo);

    HeadlineDetailVo findHeadlineDetail(int hid);

    int addNewsHeadline(NewsHeadline newsHeadline);

    NewsHeadline findByHid(int hid);

    int update(NewsHeadline newsHeadline);

    void removeByHid(int hid);
}
