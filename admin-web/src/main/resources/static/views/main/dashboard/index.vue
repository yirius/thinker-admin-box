<template>
  <div class="box">
    <ViewBasePage :render-value="renderValue" :model-value="modelValue"></ViewBasePage>
  </div>
</template>

<script>
import { defineComponent, reactive, ref, unref } from 'vue'
import ViewBasePage from '@/views/base/page.vue'

export default defineComponent({
  components: {
    ViewBasePage
  },
  setup(props, vm) {
    // 保存对应的ref响应变量
    let modelRefsValue = {};
    // 其他输出值
    const modelValue = reactive({});

    const renderValue = reactive({
      render: {
        component: "div",
        children: []
      }
    });

    const response = async () => {
      return await new Promise(resolve => {
        setTimeout(() => {
          var responseData = {
            testName: "aaa",
            rightName: "456",
            selectValue: 1,
            selectValueOther: ""
          }
          for (var i in responseData) {
            modelRefsValue[i] = ref(responseData[i]);
            modelValue[i] = modelRefsValue[i];
          }

          renderValue.render = {
            component: "ElRow",
            children: [{
              component: "ElCol",
              attrs: {
                span: 12
              },
              children: [{
                component: "ElForm",
                attrs: {
                  // modelValue: modelValue.value.formValue
                },
                children: [{
                  component: "ElFormItem",
                  attrs: {
                    label: "测试输入"
                  },
                  children: [{
                    component: "ElInput",
                    attrs: {
                      modelValue: modelRefsValue['testName'],
                      onInput(value) {
                        if (modelRefsValue.rightName.value === "456") {
                          modelRefsValue.rightName.value = "123";
                        } else {
                          modelRefsValue.rightName.value = "456";
                        }
                      }
                    },
                  }]
                }, {
                  component: "ElFormItem",
                  attrs: {
                    label: "测试下拉"
                  },
                  children: [{
                    component: "ElSelect",
                    attrs: {
                      modelValue: modelRefsValue['selectValue'],
                      'onUpdate:modelValue': value => {
                        vm.emit('update:modelValue', value);
                        modelRefsValue['selectValue'].value = value;
                      },
                      onChange: (value, vm) => {
                        modelRefsValue['selectValueOther'].value = "";
                        if(value == 2) {
                          renderValue.render.children[0].children[0].children[2].children[0].children = [{
                            component: "ElOption",
                            attrs: {
                              label: "A测试",
                              value: "a"
                            }
                          }, {
                            component: "ElOption",
                            attrs: {
                              label: "A测试2",
                              value: "b"
                            }
                          }];
                        } else {
                          renderValue.render.children[0].children[0].children[2].children[0].children = [];
                        }
                      }
                    },
                    children: [{
                      component: "ElOption",
                      attrs: {
                        label: "测试",
                        value: 1
                      }
                    }, {
                      component: "ElOption",
                      attrs: {
                        label: "测试2",
                        value: 2
                      }
                    }]
                  }]
                }, {
                  component: "ElFormItem",
                  attrs: {
                    label: "测试下拉2"
                  },
                  children: [{
                    component: "ElSelect",
                    attrs: {
                      modelValue: modelRefsValue['selectValueOther'],
                      'onUpdate:modelValue': value => {
                        vm.emit('update:modelValue', value);
                        modelRefsValue['selectValueOther'].value = value;
                      }
                    }
                  }]
                }]
              }]
            }, {
              component: "ElCol",
              attrs: {
                span: 12,
                ":class": "return props.modelValue.testName == \"bbb\"?\"bg-red\":\"\";",
                onClick: () => {
                  console.log(JSON.stringify(modelValue));
                }
              },
              children: ["`${rightName}`"]
            }]
          };
          resolve({code: 1, data: {}, msg: "success"})
        }, 300);
      })
    };

    console.log(response());

    return {
      modelValue,
      renderValue
    }
  },
  data() {
    return {

    }
  },
  methods: {

  },
})
</script>

<style scoped>
  .box {
    padding: 15px;
  }
</style>