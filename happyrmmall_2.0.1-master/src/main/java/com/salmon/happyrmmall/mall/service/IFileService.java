package com.salmon.happyrmmall.mall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @date 2020-12-3 - 18:30
 * Created by Salmon
 */

public interface IFileService {

    public String upload(MultipartFile file, String path);
}
