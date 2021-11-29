package com.thinker.framework.token.extend;

import com.thinker.framework.framework.entity.vo.LabelValue;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThinkerController {

    public List<LabelValue> quickLabelValues(Object[]... list) {
        List<LabelValue> textValues = new ArrayList<>();
        for (Object[] strings : list) {
            textValues.add(LabelValue.create(String.valueOf(strings[0]), strings[1]));
        }
        return textValues;
    }
}
