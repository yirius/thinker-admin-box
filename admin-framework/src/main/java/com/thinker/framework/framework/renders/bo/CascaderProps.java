package com.thinker.framework.framework.renders.bo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CascaderProps {

    /**
     * 次级菜单的展开方式
     */
    private String expandTrigger = "click";

    /**
     * 是否多选
     */
    private boolean multiple = false;

    /**
     * 是否严格的遵守父子节点不互相关联
     */
    private boolean checkStrictly = false;

    /**
     * 在选中节点改变时，是否返回由该节点所在的各级菜单的值所组成的数组，若设置 false，则只返回该节点的值
     */
    private boolean emitPath = true;

    /**
     * 是否动态加载子节点，需与 lazyLoad 方法结合使用
     */
    private boolean lazy = false;

    /**
     * 加载动态数据的方法，仅在 lazy 为 true 时有效	function(node, resolve)，node为当前点击的节点，resolve为数据加载完成的回调(必须调用)
     */
    private String lazyLoad;

    /**
     * 	指定选项的值为选项对象的某个属性值
     */
    private String value = "value";

    private String label = "label";

    private String children = "children";

    private String disabled = "disabled";

    private String leaf = "leaf";
}
