package com.thinker.framework.renders.assemblys.slots;

import com.thinker.framework.renders.abstracts.RootRender;
import com.thinker.framework.renders.assemblys.page.ElIcon;
import com.thinker.framework.renders.entity.RenderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ElImageErrorSlot extends RootRender {
    @Override
    public void beforeRender() {
        attrs.set("style", "display: flex;\n" +
                "  justify-content: center;\n" +
                "  align-items: center;\n" +
                "  width: 100%;\n" +
                "  height: 100%;\n" +
                "  background: var(--el-fill-color-light);\n" +
                "  color: var(--el-text-color-secondary);\n" +
                "  font-size: 30px;");

        getChildren().add(new ElIcon().setIconName("Picture"));
    }

    @Override
    public RenderResult afterRender(RenderResult renderResult) {
        return renderResult;
    }
}
