package com.tencent.nft.service.impl;

import com.feedget.vo.ImageVO;
import com.tencent.nft.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServicelmpl implements FileService {

    private String localDir = "/usr/local/src/images";  //设定Linux目录
    private String preURLPath = "http://image.jt.com";  //网络访问域名

    @Override
    public ImageVO upload(MultipartFile file) {
        //1.图片类型的校验  正则表达式  aaa.jpg
        String fileName = file.getOriginalFilename();
        //字符大小写 干扰正则的判断  将所有的文件转化为小写字母
        fileName = fileName.toLowerCase();
        //程序不满足正则, 则用户上传的图片有问题
        if(!fileName.matches("^.+\\.(jpg|png|gif)$")){

            return null;
        }

        //2. 校验文件是否为恶意程序  判断依据 属性宽度和高度  aa.exe.jpg
        try {
            //该对象是用来专门操作图片的API
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int height = bufferedImage.getHeight();
            int width  = bufferedImage.getWidth();
            //如果有一项为0 则表示一定不是正经的图片
            if(height == 0 || width == 0){
                return null;
            }

            //3.分目录存储文件  /yyyy/MM/dd
            //3.1 准备文件根目录
            String dateDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
            //拼接文件目录  F:/images/2021/MM/dd/
            String dirPath = localDir + dateDir;
            File dirFile = new File(dirPath);
            //3.2 判断是否需要创建目录
            if(!dirFile.exists()){ //不存在目录时,应该创建目录
                dirFile.mkdirs();
            }

            //4.防止文件重名 UUID.后缀
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //获取.的下标位置
            int index = fileName.lastIndexOf(".");
            //截取文件类型
            String fileType =  fileName.substring(index);
            //拼接新文件路径
            String realFileName = uuid + fileType;

            //5.实现文件上传操作
            //5.1 准备文件的全路径  文件目录/文件名称
            String realFilePath = dirPath + realFileName;
            //5.2 实现文件上传
            file.transferTo(new File(realFilePath));

            //6.封装返回值结果
            //封装虚拟路径    /2021/11/11/uuid.jpg
            String virtualPath = dateDir + realFileName;
            //封装URL地址   协议名称://域名:端口号/图片虚拟地址
            String urlPath = preURLPath + virtualPath;
            System.out.println("图片网络地址:"+urlPath);
            //封装VO对象
            ImageVO imageVO = new ImageVO();
            imageVO.setVirtualPath(virtualPath);
            imageVO.setUrlPath(urlPath);
            imageVO.setFileName(realFileName);
            return imageVO;

        } catch (IOException e) {
            e.printStackTrace();
            return null;    //如果程序执行报错,则返回null
        }
    }

}
