package com.salmon.happyrmmall.mall.service.impl;

import com.google.common.collect.Lists;
import com.salmon.happyrmmall.mall.service.IFileService;
import com.salmon.happyrmmall.mall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @date 2020-12-3 - 18:31
 * Created by Salmon
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 文件加载
     * @param file
     * @param path
     * @return
     */
    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();

        //获取扩展名(文件.后的字符串)
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        //加载后的文件名，随机乱码
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;

        StringBuilder info = new StringBuilder();
        info.append("开始上传文件，上传文件的文件名:{").append(fileName).append("},上传的路径:{").
                append(path).append("},新文件名:{").append(uploadFileName).append("}");
        logger.info(String.valueOf(info));
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile); //文件上传成功了
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));//将targetFile上传到自己的ftp服务器上
            logger.info("上传成功！！！");
            //上传完之后，删除业务服务器的文件
            targetFile.delete();
        }catch (IOException e){
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }


}
