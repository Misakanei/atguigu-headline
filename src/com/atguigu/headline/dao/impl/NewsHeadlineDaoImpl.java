package com.atguigu.headline.dao.impl;

import com.atguigu.headline.dao.BaseDao;
import com.atguigu.headline.dao.NewsHeadLineDao;
import com.atguigu.headline.pojo.NewsHeadline;
import com.atguigu.headline.pojo.vo.HeadlineDetailVo;
import com.atguigu.headline.pojo.vo.HeadlinePageVo;
import com.atguigu.headline.pojo.vo.HeadlineQueryVo;

import java.util.ArrayList;
import java.util.List;

public class NewsHeadlineDaoImpl extends BaseDao implements NewsHeadLineDao {
    @Override
    public List<HeadlinePageVo> findPageList(HeadlineQueryVo headlineQueryVo) {
        List params = new ArrayList();
        String sql = """
                select
                    hid,
                    title,
                    type,
                    page_views pageViews,
                    TIMESTAMPDIFF(HOUR,create_time,now()) pastHours,
                    publisher
                from
                    news_headline
                where
                    is_deleted = 0
                """;
        if (headlineQueryVo.getType() != 0) {
            sql = sql.concat(" and type = ? ");
            params.add(headlineQueryVo.getType());
        }
        if (headlineQueryVo.getKeyWords() != null && !headlineQueryVo.getKeyWords().equals("")) {
            sql = sql.concat(" and title like ? ");
            params.add("%" + headlineQueryVo.getKeyWords() + "%");
        }
        sql = sql.concat(" order by pastHours ASC , page_views DESC ");
        sql = sql.concat(" limit ? , ?");
        params.add((headlineQueryVo.getPageNum() - 1) * headlineQueryVo.getPageSize());
        params.add(headlineQueryVo.getPageSize());
        return baseQuery(HeadlinePageVo.class, sql, params.toArray());
    }

    @Override
    public int findPageCount(HeadlineQueryVo headlineQueryVo) {
        List params = new ArrayList();
        String sql = """
                select
                    count(1)
                from
                    news_headline
                where
                    is_deleted = 0
                """;
        if (headlineQueryVo.getType() != 0) {
            sql = sql.concat(" and type = ? ");
            params.add(headlineQueryVo.getType());
        }
        if (headlineQueryVo.getKeyWords() != null && !headlineQueryVo.getKeyWords().equals("")) {
            sql = sql.concat(" and title like ? ");
            params.add("%" + headlineQueryVo.getKeyWords() + "%");
        }
        Long count = baseQueryObject(Long.class, sql, params.toArray());
        return count.intValue();
    }

    @Override
    public int incrPageViews(int hid) {
        String sql = "update news_headline set page_views = page_views + 1 where hid = ?";
        return baseUpdate(sql, hid);
    }

    @Override
    public HeadlineDetailVo findHeadlineDetail(int hid) {
        String sql = """
                select
                    h.hid hid,
                    h.title title,
                    h.article article,
                    h.type type,
                    t.tname typeName,
                    h.page_views pageViews,
                    TIMESTAMPDIFF(HOUR,h.create_time,now()) pastHours,
                    h.publisher publisher,
                    u.nick_name author
                from
                    news_headline h
                left join
                    news_type t
                on
                    h.type = t.tid
                left join
                    news_user u
                on
                    h.publisher = u.uid
                where
                    h.hid = ?
                """;
        List<HeadlineDetailVo> voList = baseQuery(HeadlineDetailVo.class, sql, hid);
        return null != voList && voList.size() > 0 ? voList.get(0) : null;
    }

    @Override
    public int addNewsHeadline(NewsHeadline newsHeadline) {
        String sql = """
                insert into news_headline
                values (default,?,?,?,?,0,now(),now(),0)
                """;
        return baseUpdate(sql, newsHeadline.getTitle(), newsHeadline.getArticle(), newsHeadline.getType(), newsHeadline.getPublisher());
    }
}