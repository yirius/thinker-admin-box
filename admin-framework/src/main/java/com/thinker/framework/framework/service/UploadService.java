package com.thinker.framework.framework.service;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Validator;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.support.exceptions.ThinkerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @title UploadService
 * @description 便捷调用方法
 * @time 2020/2/11 6:42 下午
 * @return
 **/
@Slf4j
@Service
public class UploadService {

    public Map<String, Object> upload(HttpServletRequest request) {
        //判断是否上传了文件
        MultipartHttpServletRequest multipartRequest = null;
        if (request instanceof MultipartHttpServletRequest) {
            multipartRequest = (MultipartHttpServletRequest)(request);
        }

        return upload(multipartRequest, true);
    }

    public Map<String, Object> upload(HttpServletRequest request, boolean isImage) {
        //判断是否上传了文件
        MultipartHttpServletRequest multipartRequest = null;
        if (request instanceof MultipartHttpServletRequest) {
            multipartRequest = (MultipartHttpServletRequest)(request);
        }

        return upload(multipartRequest, isImage);
    }

    /**
     * @title upload
     * @description 上传的方法
     * @author YangYuanCe
     * @param multipartRequest
     * @return {@link Map< String, Object>}
     **/
    public Map<String, Object> upload(MultipartHttpServletRequest multipartRequest, boolean isImage) {
        //最终返回的数据
        Map<String, Object> map = new HashMap<>();
        //存在上传文件
        if(multipartRequest != null) {
            //找到上传地址
            String uploadDir = ThinkerAdmin.file().getUploadDir();
            //循环上传的文件，进行上传
            multipartRequest.getFileMap().forEach((s, multipartFile) -> {
                if(multipartFile != null && !multipartFile.isEmpty()) {
                    String filePath = getUploadFilePath(multipartFile, isImage);

                    File file = new File(uploadDir + filePath);

                    if(!file.exists()) {
                        try {
                            if(file.mkdirs()) {
                                multipartFile.transferTo(file);
                            } else {
                                throw new ThinkerException("message.thinker.uploads.mkdirError|{\"file\":\""+multipartFile.getOriginalFilename()+"\"}", 360);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();

                            throw new ThinkerException("message.thinker.uploads.uploadError|{\"file\":\""+multipartFile.getOriginalFilename()+"\",\"reason\":\""+e.getMessage(), 361);
                        }
                    }

                    map.put(s, "/uploads/" + filePath.replace("\\", "/"));
                }
            });
        }

        return map;
    }

    protected List<String> imageSuffixList;
    protected List<String> fileSuffixList;

    /**
     * @title getUploadFilePath
     * @description 获取到上传的照片
     * @author YangYuanCe
     * @param multipartFile
     * @param isImage
     * @return {@link String}
     **/
    public String getUploadFilePath(MultipartFile multipartFile, boolean isImage) {
        String fileMd5;
        String fileSuffix;
        try{
            fileMd5 = ThinkerAdmin.encrypt().md5(Arrays.toString(multipartFile.getBytes()));

            fileSuffix = FileTypeUtil.getType(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            String originalExt = FileUtil.extName(multipartFile.getOriginalFilename());
            String nameExt = FileUtil.extName(multipartFile.getName());
            if(fileSuffix.equals("jar") && Validator.isEmpty(nameExt) && Validator.isNotEmpty(originalExt)) {
                fileSuffix = originalExt;
            }
        }catch (Exception e) {
            e.printStackTrace();
            fileMd5 = "";
            fileSuffix = "jpg";
        }

        if(Validator.isEmpty(fileSuffix)) {
            String[] strings = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.");
            fileSuffix = strings[strings.length-1];
        }

        String filePrevName = fileMd5.substring(0, 2);
        String fileSuffixName = fileMd5.substring(2);

        if(isImage) {
            if(multipartFile.getSize() > ThinkerAdmin.properties().getConfig().getUploadImageSize()) {
                throw new ThinkerException("message.thinker.uploads.sizeMaxError|{\"file\":\""+multipartFile.getOriginalFilename()
                        +"\",\"size\":\""+(ThinkerAdmin.properties().getConfig().getUploadImageSize()/1000000)+"\"}", 362);
            }

            if(imageSuffixList == null) {
                imageSuffixList = ThinkerAdmin.properties().getConfig().getUploadImageSuffix();
            }

            if(!imageSuffixList.contains(fileSuffix)) {
                throw new ThinkerException("message.thinker.uploads.suffixError|{\"suffix\":\""+fileSuffix+"\"}", 363);
            }
        } else {
            if(multipartFile.getSize() > ThinkerAdmin.properties().getConfig().getUploadFileSize()) {
                throw new ThinkerException("message.thinker.uploads.sizeMaxError|{\"file\":\""+multipartFile.getOriginalFilename()
                        +"\",\"size\":\""+(ThinkerAdmin.properties().getConfig().getUploadFileSize()/1000000)+"\"}", 362);
            }

            if(fileSuffixList == null) {
                fileSuffixList = ThinkerAdmin.properties().getConfig().getUploadFileSuffix();
            }

            if(!fileSuffixList.contains(fileSuffix)) {
                throw new ThinkerException("message.thinker.uploads.suffixError|{\"suffix\":\""+fileSuffix+"\"}", 363);
            }
        }

        return filePrevName + File.separator + fileSuffixName + "." + fileSuffix;
    }
}
