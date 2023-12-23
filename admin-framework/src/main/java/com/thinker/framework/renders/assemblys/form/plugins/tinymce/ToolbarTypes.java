package com.thinker.framework.renders.assemblys.form.plugins.tinymce;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class ToolbarTypes {
    public static List<String> COMMON = Collections.singletonList("code undo redo restoredraft");
}
