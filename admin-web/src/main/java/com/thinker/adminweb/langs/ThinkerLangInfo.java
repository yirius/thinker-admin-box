package com.thinker.adminweb.langs;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.framework.langs.ILangInfo;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThinkerLangInfo extends ILangInfo {

    @Override
    public Dict getZhCn() {
        return Dict.create().set(
                "system",
                Dict.create()
                        .set("title", "网站title")
                        .set("subTitle", "网站子标题")
        );
    }

    @Override
    public Dict getEn() {
        return Dict.create().set(
                "system",
                Dict.create()
                        .set("title", "Title")
                        .set("subTitle", "Subtitle")
        );
    }


}
