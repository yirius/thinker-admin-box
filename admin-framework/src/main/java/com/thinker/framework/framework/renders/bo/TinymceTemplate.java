package com.thinker.framework.framework.renders.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TinymceTemplate {
    private String title;
    private String description;
    private String content;
    private String url;
}
