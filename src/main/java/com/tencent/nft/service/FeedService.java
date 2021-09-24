package com.tencent.nft.service;

import com.tencent.nft.common.base.FeedBack;
import java.text.ParseException;
import java.util.List;

public interface FeedService {
    int insert(FeedBack feedBack);

    List<FeedBack> getByDate(String date);

    boolean deleteById(Integer id);

    List<FeedBack> getAll(String date) throws ParseException;

    Object getById(Integer id);
}
