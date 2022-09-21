package com.thinker.minio;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    /******************************  Operate Bucket Start  ******************************/

    /**
     * 启动SpringBoot容器的时候初始化Bucket
     * 如果没有Bucket则创建
     * @throws Exception
     */
    public boolean createBucket(String bucketName) {
        try{
            if (!bucketExists(bucketName)) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            return true;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    /**
     *  判断Bucket是否存在，true：存在，false：不存在
     * @return
     * @throws Exception
     */
    public boolean bucketExists(String bucketName) {
        try{
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    /**
     * 获得Bucket的策略
     * @param bucketName
     * @return
     * @throws Exception
     */
    public String getBucketPolicy(String bucketName) throws Exception {
        return minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
    }


    /**
     * 获得所有Bucket列表
     * @return
     * @throws Exception
     */
    public List<Bucket> getAllBuckets() {
        try{
            return minioClient.listBuckets();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据bucketName获取其相关信息
     * @param bucketName
     * @return
     * @throws Exception
     */
    public Optional<Bucket> getBucket(String bucketName) {
        try{
            return getAllBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 根据bucketName删除Bucket，true：删除成功； false：删除失败，文件或已不存在
     * @param bucketName
     * @throws Exception
     */
    public boolean removeBucket(String bucketName) {
        try{
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            return true;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    /******************************  Operate Bucket End  ******************************/


    /******************************  Operate Files Start  ******************************/

    /**
     * 判断文件是否存在
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @return
     */
    public boolean isObjectExist(String bucketName, String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        } catch (Exception ignored) {

        }
        return false;
    }

    /**
     * 判断文件夹是否存在
     * @param bucketName 存储桶
     * @param objectName 文件夹名称
     * @return
     */
    public boolean isFolderExist(String bucketName, String objectName) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    return true;
                }
            }
        } catch (Exception ignored) {

        }
        return false;
    }

    /**
     * 根据文件前置查询文件
     * @param bucketName 存储桶
     * @param prefix 前缀
     * @param recursive 是否使用递归查询
     * @return MinioItem 列表
     * @throws Exception
     */
    public List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        try {
            List<Item> list = new ArrayList<>();
            Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
            if (objectsIterator != null) {
                for (Result<Item> o : objectsIterator) {
                    Item item = o.get();
                    list.add(item);
                }
            }
            return list;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 获取文件流
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @return 二进制流
     */
    public InputStream getObject(String bucketName, String objectName) {
        try{
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 断点下载
     * @param bucketName 存储桶
     * @param objectName 文件名称
     * @param offset 起始字节的位置
     * @param length 要读取的长度
     * @return 二进制流
     */
    public InputStream getObject(String bucketName, String objectName, long offset, long length) {
        try{
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).offset(offset).length(length).build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 获取路径下文件列表
     * @param bucketName 存储桶
     * @param prefix 文件名称
     * @param recursive 是否递归查找，false：模拟文件夹结构查找
     * @return 二进制流
     */
    public Iterable<Result<Item>> listObjects(String bucketName, String prefix, boolean recursive) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(recursive).build());
    }

    /**
     * 使用MultipartFile进行文件上传
     * @param bucketName 存储桶
     * @param file 文件名
     * @param objectName 对象名
     * @param contentType 类型
     * @return
     * @throws Exception
     */
    public ObjectWriteResponse uploadFile(String bucketName, MultipartFile file, String objectName, String contentType) {
        try{
            InputStream inputStream = file.getInputStream();
            return minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).contentType(contentType)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 上传本地文件
     * @param bucketName 存储桶
     * @param objectName 对象名称
     * @param fileName 本地文件路径
     */
    public ObjectWriteResponse uploadFile(String bucketName, String objectName, String fileName) {
        try{
            return minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(objectName).filename(fileName).build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 通过流上传文件
     *
     * @param bucketName 存储桶
     * @param objectName 文件对象
     * @param inputStream 文件流
     */
    public ObjectWriteResponse uploadFile(String bucketName, String objectName, InputStream inputStream) {
        try{
            return minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, inputStream.available(), -1).build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 进行分片文件合并
     * @param bucketName
     * @param objectName
     * @param sourceList
     * @return
     */
    public ObjectWriteResponse composeObject(String bucketName, String objectName, List<ComposeSource> sourceList) {
        try{
            ObjectWriteResponse response = minioClient.composeObject(
                    ComposeObjectArgs.builder().bucket(bucketName).object(objectName).sources(sourceList).build()
            );
            removeFiles(bucketName, sourceList.stream().map(ObjectArgs::object).collect(Collectors.toList()));
            return response;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 创建文件夹或目录
     * @param bucketName 存储桶
     * @param objectName 目录路径
     */
    public ObjectWriteResponse createDir(String bucketName, String objectName) {
        try{
            return minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件信息, 如果抛出异常则说明文件不存在
     *
     * @param bucketName 存储桶
     * @param objectName 文件名称
     */
    public String getFileStatusInfo(String bucketName, String objectName) {
        try {
            return minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()).toString();
        } catch (Exception ignored) {

        }
        return null;
    }

    /**
     * 拷贝文件
     *
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @param srcBucketName 目标存储桶
     * @param srcObjectName 目标文件名
     */
    public ObjectWriteResponse copyFile(String bucketName, String objectName, String srcBucketName, String srcObjectName) {
        try{
            return minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .source(CopySource.builder().bucket(bucketName).object(objectName).build())
                            .bucket(srcBucketName)
                            .object(srcObjectName)
                            .build());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param bucketName 存储桶
     * @param objectName 文件名称
     */
    public boolean removeFile(String bucketName, String objectName) {
        try{
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            return true;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }

    /**
     * 批量删除文件
     * @param bucketName 存储桶
     * @param keys 需要删除的文件列表
     * @return
     */
    public void removeFiles(String bucketName, List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
            try {
                removeFile(bucketName, s);
            } catch (Exception e) {
                log.error("[Minio工具类]>>>> 批量删除文件，异常：", e);
            }
        });
    }

    /**
     * 获取文件外链
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @param expires 过期时间 <=7 秒 （外链有效时间（单位：秒））
     * @return url
     * @throws Exception
     */
    public String getPresignedObjectUrl(String bucketName, String objectName, Integer expires) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().expiry(expires).bucket(bucketName).object(objectName).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 获得文件外链
     * @param bucketName
     * @param objectName
     * @return url
     * @throws Exception
     */
    public String getPresignedObjectUrl(String bucketName, String objectName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    /**
     * 将URLDecoder编码转成UTF8
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }
}
