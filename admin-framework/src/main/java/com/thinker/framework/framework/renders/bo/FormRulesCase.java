package com.thinker.framework.framework.renders.bo;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class FormRulesCase {

    //使用闭包的形式
    public interface Closure {
        void run(FormRulesCase formRulesCase);
    }

    public FormRulesCase(Closure closure) {
        if(closure != null) closure.run(this);
    }

    public enum RulesType {
        STRING("string"),NUMBER("number"),BOOLEAN("boolean"),
        METHOD("method"),REGEXP("regexp"),INTEGER("integer"),
        FLOAT("float"),ARRAY("array"),OBJECT("object"),
        ENUM("enum"),DATE("date"),URL("url"),HEX("hex"),
        EMAIL("email"),PATTERN("pattern"),ANY("any");

        private final String type;
        RulesType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    private RulesType type;

    private Boolean required;

    private String pattern;

    private Long min;

    private Long max;

    private Long len;

    private List<Object> enums;

    private Boolean whitespace;

    // 数组或object的内部数据校验
    private Map<String, List<FormRulesCase>> fields;

    private Object options;

    private FormRulesCase defaultField;

    private String transform;

    private String message;

    private String asyncValidator;

    private String validator;

    private String trigger = "blur";

    @Override
    public String toString() {
        StrBuilder fieldsStr = new StrBuilder().append("{\n");
        if(fields != null) {
            fields.forEach((s, formRules) -> fieldsStr.append(s).append(":").append("[").append(StrUtil.join(",", formRules)).append("],\n"));
        }

        return "{" +
                (type == null ? "" : "type:\"" + type + "\",") +
                (required == null ? "" : "required:" + required + ",") +
                (pattern == null ? "" : "pattern:" + pattern + ",") +
                (min == null ? "" : "min:" + min + ",") +
                (max == null ? "" : "max:" + max + ",") +
                (len == null ? "" : "min:" + len + ",") +
                (enums == null ? "" : "enum:" + JSON.toJSONString(enums) + ",") +
                (whitespace == null ? "" : "whitespace:" + whitespace + ",") +
                (fields == null ? "" : "fields:" + fieldsStr.toStringAndReset() + "},") +
                (defaultField == null ? "" : "defaultField:" + defaultField + ",") +
                (transform == null ? "" : "transform: (value) => {\n" + transform + "\n},") +
                (message == null ? "" : "message: () => {\n" + (message.contains("return") ? message : "return \"" + message + "\"") + "\n},") +
                (asyncValidator == null ? "" : "asyncValidator: (rule, value, callback) => {\n" + asyncValidator + "\n},") +
                (validator == null ? "" : "validator: (rule, value, callback) => {\n" + (validator.contains("return") ? validator : "return " + validator) + "\n},") +
                (trigger == null ? "" : "trigger:\"" + trigger + "\",") +
                '}';
    }
}
