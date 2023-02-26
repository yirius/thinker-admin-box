package com.thinker.adminweb.api.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.thinker.framework.framework.ThinkerAdmin;
import com.thinker.framework.framework.entity.vo.LabelValue;
import com.thinker.framework.framework.support.SpringContext;
import com.thinker.framework.framework.widgets.ThinkerResponse;
import com.thinker.framework.renders.DefineComponent;
import com.thinker.framework.renders.ThinkerForm;
import com.thinker.framework.renders.ThinkerPage;
import com.thinker.framework.renders.ThinkerTable;
import com.thinker.framework.renders.assemblys.form.plugins.ElCheckbox;
import com.thinker.framework.renders.assemblys.form.plugins.ElRadio;
import com.thinker.framework.renders.assemblys.form.plugins.ElUpload;
import com.thinker.framework.renders.entity.table.*;
import com.thinker.framework.renders.entity.enums.DateTypeEnum;
import com.thinker.minio.MinioUtil;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/test")
public class TestApi {

    @RequestMapping(value = "/testPage")
    public ThinkerResponse testPage() {
        DefineComponent.setModelValue("pTextA", "654321");

        return new ThinkerPage((thinkerPage) -> {
            thinkerPage.getRender().getAttrs().set("style", "padding: 15px");

            // table相关
            ThinkerTable thinkerTable = new ThinkerTable();
            thinkerTable.setId("table_name");

            thinkerTable.checkbox().setWidth("60px");
            thinkerTable.column("id", "ID").setSortable(true);
            thinkerTable.column("name", "名册").setSortable(true).setEditRender(new EditRender().setName("ElInput"));
            thinkerTable.column("userType", "名称").setMinWidth("120px").setEditRender(
                    new EditRender().setName("ElCascader").setAttrs(Dict.create().set("options", new ArrayList<LabelValue>() {
                        {
                            add(LabelValue.create("测试", 1).setChildren(new ArrayList<LabelValue>() {
                                {
                                    add(LabelValue.create("测试3", 3));
                                    add(LabelValue.create("测试4", 4));
                                }
                            }));
                            add(LabelValue.create("测试2", 2));
                        }
                    }))
            );
            thinkerTable.column("content", "测试").setMinWidth("120px").setFilters(new ArrayList<LabelValue>(){
                {
                    add(LabelValue.create("测试", 1));
                    add(LabelValue.create("测试2", 2));
                }
            }).setEditRender(new EditRender().setName("ElSelect").setOptions(new ArrayList<LabelValue>() {
                {
                    add(LabelValue.create("测试", 1));
                    add(LabelValue.create("测试2", 2));
                }
            }));

            thinkerTable.importData("/thinker/admin/upload");
            Map<String, List<EditRuleItem>> ruleItemMap = new HashMap<>();
            ruleItemMap.put("name", new ArrayList<EditRuleItem>() {
                {
                    add(new EditRuleItem().setMessage("测试长度提示").setValidator("(e) => { if(e.cellValue.length > 5) { return new Error('长度错误') } }"));
                }
            });
            thinkerTable.setEditRules(ruleItemMap);

//            thinkerTable.getToolbarConfig().setButtons(new ArrayList<ToolbarConfigButton>() {
//                {
//                    add(new ToolbarConfigButton().setName("测试").setCode("add"));
//                }
//            });
            thinkerTable.getEvents().put("toolbarButtonClick", "(e) => { console.log(e); }");

            thinkerTable.setFormConfig(new FormConfig().setTitleWidth("120px").setItems(new ArrayList<FormConfigItems>() {
                {
                    add(new FormConfigItems().setField("name").setTitle("名称").setItemRender(
                            new EditRender().setName("$input").setProps(Dict.create().set("placeholder", "测试pla"))
                    ));
                    add(new FormConfigItems().setField("id").setTitle("ID").setItemRender(
                            new EditRender().setName("ElSelect").setOptions(new ArrayList<LabelValue>(){
                                {
                                    add(LabelValue.create("测试", 1));
                                    add(LabelValue.create("测试2", 2));
                                }
                            })
                    ).setFolding(true));
                    add(new FormConfigItems().setSpan(24).setAlign("center").setCollapseNode(true).setItemRender(
                            new EditRender().setName("$buttons").setChildren(new ArrayList<Dict>() {
                                {
                                    add(Dict.create().set("props", Dict.create().set("type", "submit").set("content", "提交").set("status", "primary")));
                                    add(Dict.create().set("props", Dict.create().set("type", "reset").set("content", "重置")));
                                }
                            })
                    ));
                }
            }));

            thinkerPage.getChildren().add(thinkerTable);

            // 渲染Form
            ThinkerForm thinkerForm = new ThinkerForm();
            thinkerForm.input("pText", "测试Input").setOnInput("(value) => { console.log(findComponentData(_strConcatIndex)) }");
            thinkerForm.input("inputPrefix", "测试Input前缀")
                    .setPrepend("() => { return \"https://\"; }")
                    .setOnInput("(value) => { console.log(findComponentData(_strConcatIndex)) }");

            thinkerForm.select("pTextData", "测试Select").addOptions(new ArrayList<LabelValue>() {
                {
                    add(LabelValue.create("测试1", 1));
                    add(LabelValue.create("测试2", 2));
                    add(LabelValue.create("测试3", 3).set("disabled", true));
                    add(LabelValue.create("测试4", 4));
                }
            }).setPlaceholder("请选择测试数据").setOnChange("(value) => { if(value==2) { findComponentData('0-0-3-0').children = [{component:'ElOption', attrs: {label: '1231', value: 'a'}}]; } }");

            thinkerForm.select("pTextDataGroup", "测试SelectGroup").addGroupOptions("测试", new ArrayList<LabelValue>() {
                {
                    add(LabelValue.create("测试1", 1));
                    add(LabelValue.create("测试2", 2));
                }
            }).addGroupOptions("测试2", new ArrayList<LabelValue>() {
                {
                    add(LabelValue.create("测试3", 3).set("disabled", true));
                    add(LabelValue.create("测试4", 4));
                }
            }).setPlaceholder("请选择测试数据").search().setRemote(true).setRemoteMethod("(value) => { console.log(findComponentData(_strConcatIndex)) }");

            thinkerForm.cascader("cascaderData", "测试Cascader").setOptions(new ArrayList<LabelValue>() {
                {
                    add(LabelValue.create("测试1", 1).setChildren(new ArrayList<LabelValue>() {
                        {
                            add(LabelValue.create("测试1", 5));
                            add(LabelValue.create("测试2", 6));
                            add(LabelValue.create("测试3", 7).set("disabled", true));
                            add(LabelValue.create("测试4", 8).setChildren(new ArrayList<LabelValue>() {
                                {
                                    add(LabelValue.create("测试1", 9));
                                    add(LabelValue.create("测试2", 10));
                                    add(LabelValue.create("测试3", 11).set("disabled", true));
                                    add(LabelValue.create("测试4", 12));
                                }
                            }));
                        }
                    }));
                    add(LabelValue.create("测试2", 2));
                    add(LabelValue.create("测试3", 3).set("disabled", true));
                    add(LabelValue.create("测试4", 4));
                }
            }).setIsPanel(true);

            thinkerForm.checkbox("checkboxData", "测试Checkbox").setLabel("同意协议");
            thinkerForm.checkboxGroup("checkboxArr", "测试CheckboxGroup").runClosure(rootRender -> {
                rootRender.addOptions(new ArrayList<LabelValue>() {
                    {
                        add(LabelValue.create("测试1", 1));
                        add(LabelValue.create("测试2", 2));
                        add(LabelValue.create("测试3", 3).set("disabled", true));
                        add(LabelValue.create("测试4", 4));
                    }
                });

                rootRender.getChildren().forEach(rootRender1 -> {
                    ((ElCheckbox) rootRender1).setIsButton(true);
                });
            });

            thinkerForm.colorPicker("colorPickerData", "测试ColorPicker").setOnActiveChange("(value) => { console.log(value) }");
            thinkerForm.datePicker("datePickerData", "测试DatePicker")
                    .setType(DateTypeEnum.DATERANGE)
                    .setOnChange("(value) => { console.log(value) }")
                    .setShortcuts("[{text: 'Today', value: new Date()}]");

            thinkerForm.inputNumber("inputNumber", "测试inputNumber").setOnChange("(value) => { console.log(value) }");

            thinkerForm.radio("radioData", "测试Radio").setLabel("同意协议");
            thinkerForm.radioGroup("radioArr", "测试radioGroup").runClosure(rootRender -> {
                rootRender.addOptions(new ArrayList<LabelValue>() {
                    {
                        add(LabelValue.create("测试1", 1));
                        add(LabelValue.create("测试2", 2));
                        add(LabelValue.create("测试3", 3).set("disabled", true));
                        add(LabelValue.create("测试4", 4));
                    }
                });

                rootRender.getChildren().forEach(rootRender1 -> {
                    ((ElRadio) rootRender1).setIsButton(true);
                });
            });

            thinkerForm.rate("rateData", "测试Rate");

            thinkerForm.slider("sliderData", "测试Slider");

            thinkerForm.switchs("switchData", "测试Switch");

            thinkerForm.timePicker("timePickerData", "测试timePicker").setDisabledHours("() => { return [1,2,3] }");

            thinkerForm.timeSelect("timeSelectData", "测试timeSelect").setStep("00:20");

            thinkerForm.transfer("transferData", "测试transfer").setData(new ArrayList<LabelValue>() {
                {
                    add(LabelValue.create("测试1", 1));
                    add(LabelValue.create("测试2", 2));
                    add(LabelValue.create("测试3", 3).set("disabled", true));
                    add(LabelValue.create("测试4", 4));
                }
            }).setTitles(Arrays.asList("待选择", "已选择"));

            thinkerForm.upload("uploadData", "测试upload").setListType(ElUpload.ListType.PICTURECARD).setFileList(new ArrayList<Dict>() {
                {
                    add(Dict.create().set("name", "food.jpeg").
                            set("url", "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100")
                    );
                }
            });

            thinkerForm.upload("uploadFile", "测试uploadFile").setFileList(new ArrayList<Dict>() {
                {
                    add(Dict.create().set("name", "food.jpeg").
                            set("url", "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100")
                    );
                }
            });

            thinkerForm.upload("uploadFile", "测试uploadFile").setListType(ElUpload.ListType.PICTURE).setFileList(new ArrayList<Dict>() {
                {
                    add(Dict.create().set("name", "food.jpeg").
                            set("url", "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100")
                    );
                }
            });

            thinkerForm.submit("");

            thinkerPage.getChildren().add(thinkerForm);
        }).render();
    }

    @RequestMapping(value = "/testProperties")
    public ThinkerResponse testProperties() {
        return new ThinkerResponse().data(ThinkerAdmin.properties().getToken()).success();
    }
}
