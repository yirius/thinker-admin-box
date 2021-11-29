package com.thinker.framework.framework.renders.bo;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Accessors(chain = true)
public class PageImportCase {
    private String from;
    private List<String> moduleNames = new ArrayList<>();
    private String defaultName = null;
    private boolean isStar = false;

    /**
     * 批量添加modules
     * @param appendModuleNames
     * @return
     */
    public PageImportCase addModuleNames(List<String> appendModuleNames) {
        if(appendModuleNames != null && appendModuleNames.size() > 0) {
            appendModuleNames.forEach(s -> {
                if(!moduleNames.contains(s)) moduleNames.add(s);
            });
        }
        return this;
    }

    @Override
    public String toString() {
        if(isStar) {
            return "import * as " + defaultName + " from \"" + from + "\"";
        } else {
            if(moduleNames.size() > 0) {
                return "import " + (defaultName == null ? "" : defaultName + ",") + "{ " + StrUtil.join(",", moduleNames) + " }" + " from \"" + from + "\"";
            } else {
                return "import " + (defaultName == null ? "" : defaultName) + " from \"" + from + "\"";
            }
        }
    }
}
