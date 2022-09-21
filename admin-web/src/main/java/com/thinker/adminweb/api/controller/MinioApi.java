package com.thinker.adminweb.api.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.crypto.SecureUtil;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.minio.MinioUtil;
import io.minio.ComposeSource;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/minio")
public class MinioApi {

    @RequestMapping(value = "/checkMd5")
    public ThinkerResponse checkMd5(String md5) {
        return new ThinkerResponse().fail();
    }

    @RequestMapping(value = "/uploadChunk")
    public ThinkerResponse uploadChunk(MultipartFile data, String md5, String name, long totalSize, long total, long index) {
        try{
            SpringContext.getBean(MinioUtil.class).createBucket("chunk");

            if(!SpringContext.getBean(MinioUtil.class).isObjectExist("chunk", md5+"_"+index+".chunk")) {
                ObjectWriteResponse objectWriteResponse = SpringContext.getBean(MinioUtil.class)
                        .uploadFile("chunk", md5+"_"+index+".chunk", data.getInputStream());
                log.info(objectWriteResponse.etag());

                if(index < total) {
                    return new ThinkerResponse().code(222);
                }
                return new ThinkerResponse().success();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return new ThinkerResponse().fail();
    }

    @RequestMapping(value = "/merge")
    public ThinkerResponse merge(String fileName, long shardCount, String md5, long fileSize, String fileType) {
        try{
            SpringContext.getBean(MinioUtil.class).createBucket("chunk");

            if(SpringContext.getBean(MinioUtil.class).isObjectExist("chunk", md5+"_"+1+".chunk")) {
                List<ComposeSource> composeSources = new ArrayList<>();
                List<String> delSources = new ArrayList<>();
                for (int i = 1; i <= shardCount; i++) {
                    composeSources.add(ComposeSource.builder().bucket("chunk").object(md5+"_"+i+".chunk").build());
                    delSources.add(md5+"_"+i+".chunk");
                }
                ObjectWriteResponse objectWriteResponse = SpringContext.getBean(MinioUtil.class)
                        .composeObject("chunk", md5+"."+fileType, composeSources);
                log.info(objectWriteResponse.etag());
                SpringContext.getBean(MinioUtil.class).removeFiles("chunk", delSources);
                return new ThinkerResponse().success();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return new ThinkerResponse().fail();
    }

    @RequestMapping(value = "/uploadMinio")
    public ThinkerResponse uploadMinio(MultipartFile file) throws Exception {
        try{
            String fileMd5 = SecureUtil.md5(file.getInputStream());

            String fileSuffix = FileTypeUtil.getType(file.getInputStream(), file.getOriginalFilename());

            SpringContext.getBean(MinioUtil.class).createBucket("datashare");

            if(!SpringContext.getBean(MinioUtil.class).isObjectExist("datashare", fileMd5+"."+fileSuffix)) {
                ObjectWriteResponse objectWriteResponse = SpringContext.getBean(MinioUtil.class).uploadFile("datashare", file, fileMd5+"."+fileSuffix, file.getContentType());
                log.info(objectWriteResponse.etag());
                return new ThinkerResponse().success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/getPresignedObjectUrl")
    public ThinkerResponse getPresignedObjectUrl(MultipartFile file) throws Exception {
        log.info(SpringContext.getBean(MinioUtil.class).getPresignedObjectUrl("datashare", "f83e91c4794e482727da3fecad3bbdbb.pptx"));
        return null;
    }
}
