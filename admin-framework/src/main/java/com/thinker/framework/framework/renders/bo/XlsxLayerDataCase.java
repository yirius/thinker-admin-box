package com.thinker.framework.framework.renders.bo;

import com.thinker.framework.framework.entity.vo.LabelProp;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class XlsxLayerDataCase {
    private boolean show = false;
    private String title = "EXCEL导入";
    private boolean showButton = false;
    private String postUrl = null;
    // 是否服务器段轮询
    private boolean isServer = false;
    // 是否上传
    private boolean isUpload = false;
    // 上传地址
    private String uploadUrl = null;
    // 数据列
    private List<LabelProp> columns = new ArrayList<>();
    // 异常提示列
    private boolean isShowErr = false;
}
