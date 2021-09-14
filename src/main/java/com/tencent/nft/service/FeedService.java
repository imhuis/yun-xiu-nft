package com.tencent.nft.service;



import com.tencent.nft.common.base.FeedBack;

import java.util.List;

public interface FeedService {
    int insert(FeedBack feedBack);

    List<FeedBack> getByDate(String date);

    boolean deleteById(Integer id);

    List<FeedBack> getAllFeedBack();

    Object getById(Integer id);
}
