package com.thinker.framework.framework.abstracts;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.renders.tags.ComponentAttr;
import com.thinker.framework.framework.utils.ToolsUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有render内部类的基类
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public abstract class LayoutAbstract {

    public LayoutAbstract() {
        this.setLayoutId(this.getParentCall());
    }

    /**
     * 每一个组件需要存在一个id, 只能是英文, 且不能存在特殊字符
     */
    protected String layoutId;

    public String getParentCall() {
        String parentCallName = ThinkerAdmin.thread().getString("CURRENT_CLASS_METHOD", null);
        if(parentCallName == null) {
            int count = 0;
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            //循环找到名称
            for (StackTraceElement stackTraceElement: stackTraceElements) {
                if(stackTraceElement.getClassName().contains("sun.reflect.") || stackTraceElement.getClassName().contains("BySpringCGLIB")){//分割分类名
                    break;
                }
                //最多找10次，就不找了
                count++;
                if(count > 9){
                    break;
                }
            }
            --count;
            //如果存在该参数
            if(count <= 9 && stackTraceElements.length >= count) {
                String className = stackTraceElements[count].getClassName().toLowerCase();
                String methodName = stackTraceElements[count].getMethodName().toLowerCase();

                String[] classNames = StrUtil.split(className, ".");
                parentCallName = (classNames[classNames.length-1] + "_" + methodName).replace("controller_", "_");
            }

            //清空队列
            stackTraceElements = null;

            if(parentCallName == null){
                parentCallName = ToolsUtil.rand(12, ToolsUtil.RandFormat.CHAR).toLowerCase();
            }

            ThinkerAdmin.thread().setObject("CURRENT_CLASS_METHOD", parentCallName.replace("$", "").replace("`", ""));
        }

        return parentCallName;
    }

    /**
     * ATTR参数的渲染
     */
    private Map<String, Object> attrs = new HashMap<>();

    public LayoutAbstract addAttr(String attrName, Object attrValue) {
        attrs.put(attrName, attrValue);
        return this;
    }

    public LayoutAbstract removeAttr(String attrName) {
        attrs.remove(attrName);
        return this;
    }

    public String getAttrsStr() {
        StrBuilder strBuilder = new StrBuilder();
        attrs.forEach((s, o) -> strBuilder.append(s).append("=").append(String.valueOf(o)));
        return strBuilder.toStringAndReset();
    }

    /**
     * 渲染template模板
     * @param slotName <template #slotName></>
     * @param slotArgs <template #slotName="slotArgs"></>
     * @param slotContent <template #slotName="slotArgs">slotContent</>
     * @return
     */
    public String slotStr(String slotName, String slotArgs, String slotContent) {
        return Validator.isEmpty(slotContent) ? "" : "<template #"+slotName+(Validator.isNotEmpty(slotArgs)?"=\""+slotArgs+"\"":"")+">\n"+slotContent+"\n</template>\n";
    }

    /**
     * 初始化指定参数
     * @param layoutAbstract
     * @param <T>
     * @return
     */
    public <T extends LayoutAbstract> T initLayoutAbstract(T layoutAbstract) {
        layoutAbstract.setLayoutId(getLayoutId());
        return layoutAbstract;
    }

    /**
     * 是否显示该部分
     */
    @ComponentAttr(prevStr = "v-", isEvent = true, eventStateArgs = "()")
    private String show;

    /**
     * 各组件需要继承的接口
     * @return
     */
    public abstract Object render();

    @Override
    public String toString() {
        return String.valueOf(render());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
