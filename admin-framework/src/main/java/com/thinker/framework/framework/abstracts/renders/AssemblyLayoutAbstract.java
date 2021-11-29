package com.thinker.framework.framework.abstracts.renders;

import cn.hutool.core.text.StrBuilder;
import com.thinker.framework.framework.abstracts.LayoutAbstract;
import com.thinker.framework.framework.renders.form.assemblys.*;
import com.thinker.framework.framework.renders.form.assemblys.Date;
import com.thinker.framework.framework.renders.page.plugins.ThinkerRow;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 组件外部包裹内容
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public abstract class AssemblyLayoutAbstract extends LayoutAbstract {

    /**
     * 保存所有的组件组件, 不能直接设置，但保留在下级Layout里可改
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    protected List<AssemblyAbstract> assemblys = new ArrayList<>();

    /**
     * 添加一个组件
     * @param assembly
     * @param <T>
     * @return
     */
    private <T extends AssemblyAbstract> T addAssembly(T assembly) {
        assemblys.add(initLayoutAbstract(assembly));
        return assembly;
    }

    /**
     * 渲染所有的assembly
     * @return
     */
    public String getAssemblysRender() {
        StrBuilder formItemStr = new StrBuilder();
        assemblys.forEach(layoutAbstract -> formItemStr.append(layoutAbstract.render()));
        return formItemStr.toStringAndReset();
    }

    /**
     * 开启网格排列，24列，可在组件中开启openRowCol()并设置长度
     */
    @Setter(AccessLevel.NONE)
    private ThinkerRow row = null;

    /**
     * 开启网格排列，24列，可在组件中开启openRowCol()并设置长度
     */
    public ThinkerRow openRow() {
        if(row == null) {
            row = initLayoutAbstract(new ThinkerRow());
        }
        return row;
    }

    /**
     * 判断是否开启了网格排布，如果没开启返回默认内容，开启则包裹一层
     * @param content
     * @return
     */
    public String renderRow(String content) {
        if(row != null) {
            return row.setContent(content).render().toString();
        }
        return content;
    }

    public Cascader cascader(String prop, String label) {
        return addAssembly(new Cascader(prop, label));
    }

    public Checkbox checkbox(String prop, String label) {
        return addAssembly(new Checkbox(prop, label));
    }

    public ColorPicker colorPicker(String prop, String label) {
        return addAssembly(new ColorPicker(prop, label));
    }

    public Date date(String prop, String label) {
        return addAssembly(new Date(prop, label));
    }

    public Input input(String prop, String label) {
        return addAssembly(new Input(prop, label));
    }

    public InputAutocomplete inputAutocomplete(String prop, String label) {
        return addAssembly(new InputAutocomplete(prop, label));
    }

    public InputNumber inputNumber(String prop, String label) {
        return addAssembly(new InputNumber(prop, label));
    }

    public Radio radio(String prop, String label) {
        return addAssembly(new Radio(prop, label));
    }

    public Rate rate(String prop, String label) {
        return addAssembly(new Rate(prop, label));
    }

    public Select select(String prop, String label) {
        return addAssembly(new Select(prop, label));
    }

    public SelectVirtualized selectVirtualized(String prop, String label) {
        return addAssembly(new SelectVirtualized(prop, label));
    }

    public Slider slider(String prop, String label) {
        return addAssembly(new Slider(prop, label));
    }

    public Switchs switchs(String prop, String label) {
        return addAssembly(new Switchs(prop, label));
    }

    public Time time(String prop, String label) {
        return addAssembly(new Time(prop, label));
    }

    public TimeSelect timeSelect(String prop, String label) {
        return addAssembly(new TimeSelect(prop, label));
    }

    public Transfer transfer(String prop, String label) {
        return addAssembly(new Transfer(prop, label));
    }

    public Tree tree(String prop, String label) {
        return addAssembly(new Tree(prop, label));
    }

    public Upload upload(String prop, String label) {
        return addAssembly(new Upload(prop, label));
    }
}
