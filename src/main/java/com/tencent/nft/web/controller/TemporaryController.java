package com.tencent.nft.web.controller;



import com.tencent.nft.entity.nft.vo.SysResult;
import com.tencent.nft.entity.security.Temporarysave;
import com.tencent.nft.service.TemporaryService;
import org.springframework.web.bind.annotation.*;

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

      @GetMapping("/selectlinshi")
    public SysResult selecttemporarysave(Temporarysave temporarysave){
          temporaryService.selectlinshi(temporarysave);
          return SysResult.success(temporaryService.selectlinshi(temporarysave));
      }
}
