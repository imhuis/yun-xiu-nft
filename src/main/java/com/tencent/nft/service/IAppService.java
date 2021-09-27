package com.tencent.nft.service;

import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.nft.vo.MyLibraryVO;

import java.util.List;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
public interface IAppService {

    List<MyLibraryVO> myLibrary();

    CollectionVO collectionDetails(String subId);

}
