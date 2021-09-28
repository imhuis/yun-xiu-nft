package com.tencent.nft.service;

import com.tencent.nft.common.base.FeedBack;

import java.text.ParseException;
import java.util.List;


/**
 * @author: yunj
 * @date: 2021/9/26
 * @description:
 */
public interface IFeedService {

    int insert(FeedBack feedBack);

    List<FeedBack> getByDate(String date);

    boolean deleteById(Integer id);

    List<FeedBack> getAll(String date) throws ParseException;

    Object getById(Integer id);
}
