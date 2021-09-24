package com.tencent.nft.web.controller;



import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.entity.security.Temporarysave;
import com.tencent.nft.service.TemporaryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class TemporaryController {
      @Resource
      private TemporaryService temporaryService;

      @PostMapping("/linshiinsert")
      public SysResult linshi(@RequestBody  Temporarysave temporarysave){
          temporaryService.linshi(temporarysave);
          return SysResult.success();
      }
}
