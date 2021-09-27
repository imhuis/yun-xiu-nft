package com.tencent.nft.service.impl;

import com.google.common.collect.Lists;
import com.tencent.nft.common.util.UUIDUtil;
import com.tencent.nft.core.security.SecurityUtils;
import com.tencent.nft.entity.app.vo.CollectionVO;
import com.tencent.nft.entity.nft.NFTInfo;
import com.tencent.nft.entity.nft.NFTProduct;
import com.tencent.nft.entity.nft.SubNFT;
import com.tencent.nft.entity.nft.SuperNFT;
import com.tencent.nft.entity.nft.vo.MyLibraryVO;
import com.tencent.nft.mapper.NftMapper;
import com.tencent.nft.mapper.NftProductMapper;
import com.tencent.nft.mapper.UserLibraryMapper;
import com.tencent.nft.mapper.WxUserMapper;
import com.tencent.nft.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author: imhuis
 * @date: 2021/9/23
 * @description:
 */
@Service
public class AppServiceImpl implements IAppService {

    @Resource
    private NftMapper nftMapper;

    @Resource
    private UserLibraryMapper userLibraryMapper;

    @Resource
    private NftProductMapper productMapper;

    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public List<MyLibraryVO> myLibrary() {
        String p = SecurityUtils.getCurrentUsername().get();
        List<MyLibraryVO> collectionVOList = Lists.newLinkedList();
        List<Long> subNftList = userLibraryMapper.selectNftIdByPhone(p);
        subNftList.stream().forEach(s -> {
            SubNFT subNFT = nftMapper.selectSubNftById(s);
            NFTInfo nftInfo = nftMapper.selectNftInfoByNftId(subNFT.getSuperNFTId()).get();
            MyLibraryVO libraryVO = new MyLibraryVO();

            libraryVO.setNftId(subNFT.getNftId());
            libraryVO.setNftName(nftInfo.getNftName());
            libraryVO.setCoverPicture(nftInfo.getCoverPicture());

            libraryVO.setOwner(Optional.ofNullable(wxUserMapper.selectByPhone(p)).get().get().getNickname());
            collectionVOList.add(libraryVO);
        });
        return collectionVOList;
    }

    @Override
    public CollectionVO collectionDetails(String subId) {
        String p = SecurityUtils.getCurrentUsername().get();
        SubNFT zi = nftMapper.selectSubNftByNftId(subId);
        SuperNFT superNFT = nftMapper.selectSuperNFTByNftId(zi.getSuperNFTId()).get();
        NFTProduct nftProduct = productMapper.selectByNftId(zi.getSuperNFTId()).get();
        NFTInfo nftInfo = nftMapper.selectNftInfoByNftId(zi.getSuperNFTId()).get();
        CollectionVO collectionVO = new CollectionVO();

        collectionVO.setNftName(superNFT.getNftName());
        collectionVO.setNftFile(superNFT.getNftFile());
        collectionVO.setFileType(superNFT.getNftType().getCode());
        collectionVO.setIssuer(superNFT.getIssuer());
        collectionVO.setBrandOwner(superNFT.getBrandOwner());
        collectionVO.setCoverPic(nftInfo.getCoverPicture());
        collectionVO.setPrice(nftProduct.getUnitPrice().doubleValue());
        collectionVO.setBlockChainAddress(UUIDUtil.generateUUID());
        collectionVO.setOwner(Optional.ofNullable(wxUserMapper.selectByPhone(p)).get().get().getNickname());
        return collectionVO;
    }


}
