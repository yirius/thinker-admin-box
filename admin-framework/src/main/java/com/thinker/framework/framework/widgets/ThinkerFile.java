package com.thinker.framework.framework.widgets;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.TextValue;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title ThinkerFile
 * @description 便捷调用方法
 * @time 2020/2/22 12:32 下午
 * @return
 **/
@Slf4j
public class ThinkerFile {
    /**
     * @title 获取到上传图片地址
     * @description 
     * @author YangYuanCe
     * @return {@link null}
     **/
    public String getUploadDir() {
        return getDirPath(ThinkerAdmin.properties().getConfig().getUploadPath());
    }

    /**
     * 保存所有路径
     */
    private final Map<String, String> dirMap = new HashMap<>();

    public String getDirPath(String dirName) {
        if(!dirMap.containsKey(dirName) || Validator.isEmpty(dirMap.get(dirName))) {
            File file = new File(dirName);
            if(file.exists()) {
                dirMap.put(dirName, file.getAbsolutePath());
            } else {
                if(file.mkdirs()) {
                    dirMap.put(dirName, file.getAbsolutePath());
                } else {
                    dirMap.put(dirName, (new File("uploads" + File.separator)).getAbsolutePath());
                }
            }
        }

        return dirMap.get(dirName) + File.separator;
    }

    /**
     * @title getDirectory
     * @description 获取到当前指定路径的树结构
     * @author YangYuanCe
     * @param path
     * @return {@link TextValue}
     **/
    public TextValue getDirectory(String path) {
        return getDirectory(new File(path));
    }

    public TextValue getDirectory(File file) {
        TextValue textValue = TextValue.create(file.getName(), file.getAbsolutePath()).set("ext", file.isDirectory() ? 1 : 0);

        if(file.isDirectory()) {
            List<TextValue> mapList = new ArrayList<>();

            File[] files = file.listFiles((dir, name) -> !name.startsWith("."));
            if(files != null && files.length != 0) {
                for (File file1 : files) {
                    mapList.add(getDirectory(file1));
                }
                textValue.set("childs", mapList);
            }
            files = null;
        }

        file = null;
        return textValue;
    }

    public String getSuffix(File file) {
        return FileTypeUtil.getType(file);
    }

    public String getSuffix(String s) {
        return FileTypeUtil.getType(s);
    }

    public String getSuffix(InputStream s) {
        return FileTypeUtil.getType(s);
    }

    /**
     * 根据名称，获取到路径
     * @param filePath
     * @return
     */
    public File getFileByPath(String filePath) {
        if(filePath.contains(ThinkerAdmin.properties().getConfig().getUploadPath())) {
            return FileUtil.file(getUploadDir() + StrUtil.subAfter(filePath, ThinkerAdmin.properties().getConfig().getUploadPath(), false));
        } else {
            return FileUtil.file(filePath);
        }
    }
}
