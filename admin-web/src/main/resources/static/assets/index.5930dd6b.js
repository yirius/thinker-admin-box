import{_ as F,L as k}from"./index.a744804a.js";import E from"./tip.053cf807.js";import{C as V}from"./cropperjs.f8d72405.js";import{u as B,e as A,p as L}from"./@element-plus.220d6db7.js";import{d as R,r as h,w as T,n as I,a6 as g,o as y,W as v,X as p,a as r,R as N,u as s,$ as U,a2 as n,a0 as c}from"./@vue.1015dcbd.js";import"./element-plus.0c0d1b6e.js";import"./lodash-es.032e1c7e.js";import"./@ctrl.82a509e0.js";import"./dayjs.818e5577.js";import"./@kangc.f912404d.js";import"./vue.9b77f3f7.js";import"./async-validator.5d25c98b.js";import"./memoize-one.4ee5c96d.js";import"./escape-html.e5dfadb9.js";import"./normalize-wheel-es.1c4ac15a.js";import"./@floating-ui.1ef3caef.js";import"./vxe-table.af633bdf.js";import"./xe-utils.a7d87cf6.js";import"./vxe-table-plugin-element.e4063f60.js";import"./vxe-table-plugin-export-xlsx.25030aa5.js";import"./exceljs.3364a90c.js";import"./vue-i18n.e5b09f92.js";import"./@intlify.7df7362d.js";import"./vuex.fa98a0d6.js";import"./axios.d2ab6b2c.js";import"./qs.6ea39d31.js";import"./side-channel.f7ae5909.js";import"./get-intrinsic.1675b33e.js";import"./has-symbols.caae0f97.js";import"./function-bind.cb3858f2.js";import"./has.c1051c46.js";import"./call-bind.0b5a4f26.js";import"./object-inspect.a81284f5.js";import"./dotenv.04d4ebad.js";import"./vue-router.87d0219e.js";import"./nprogress.9823fd6c.js";import"./vue3-sfc-loader.41fe0098.js";import"./@vueuse.38f6f3c4.js";import"./throttle-debounce.f3f727a9.js";import"./sortablejs.939c3627.js";import"./echarts.a4c9e4fe.js";import"./zrender.d49f868d.js";import"./tslib.60310f1a.js";/* empty css                      */import"./vite-plugin-mock.03a04ca1.js";import"./mockjs.bc2d1bb5.js";import"./path-to-regexp.3eda823b.js";const W={class:"container"},H={class:"box"},j=["src"],q={class:"box"},z={class:"handle-box"},M=c("\u4E0A\u4F20\u56FE\u7247"),O=c("\u4E0B\u8F7D\u622A\u56FE\u81F3\u672C\u5730"),S=c("\u4FDD\u5B58\u622A\u56FE"),X=R({props:{layer:{type:Object,required:!0,default:()=>({show:!1})},modelValue:{type:String,default:""}},emits:["update:modelValue"],setup(m,{emit:w}){const u=m;let a=h(null);const l="image"+new Date().getTime(),d="preview"+new Date().getTime();let _=h(null);T(()=>u.layer.show,e=>{!e||I(()=>{f()})});function f(){a.value&&a.value.destroy();const e=document.getElementById(l);a.value=new V(e,{preview:"."+d})}function x(e,o){C(e),_.value.clearFiles()}function C(e){let o=new FileReader;o.readAsDataURL(e.raw),o.onload=function(t){document.getElementById(l).setAttribute("src",this.result),f()}}function b(){var i;const e=(i=a.value)==null?void 0:i.getCroppedCanvas({maxWidth:4096,maxHeight:4096}),o=(e==null?void 0:e.toDataURL())||"",t=document.createElement("a");t.href=o,t.download="download.png",t.click()}function D(){var t;const e=(t=a.value)==null?void 0:t.getCroppedCanvas({maxWidth:4096,maxHeight:4096}),o=e==null?void 0:e.toDataURL();w("update:modelValue",o),u.layer.show=!1}return(e,o)=>{const t=g("el-button"),i=g("el-upload");return y(),v(k,{layer:m.layer,width:"1000"},{default:p(()=>[r("div",W,[r("div",H,[r("img",{id:l,class:"default-img",src:m.modelValue},null,8,j)]),r("div",q,[r("div",{class:N([d,"preview"])})])]),s(a)?(y(),v(E,{key:0,cropper:s(a)},null,8,["cropper"])):U("",!0),r("div",z,[n(i,{class:"upload-demo",action:"","auto-upload":!1,accept:"image/*",ref_key:"uploadDom",ref:_,"on-change":x},{default:p(()=>[n(t,{type:"primary",icon:s(B),style:{"margin-right":"10px"}},{default:p(()=>[M]),_:1},8,["icon"])]),_:1},512),n(t,{type:"primary",icon:s(A),onClick:b},{default:p(()=>[O]),_:1},8,["icon"]),n(t,{type:"primary",icon:s(L),onClick:D},{default:p(()=>[S]),_:1},8,["icon"])])]),_:1},8,["layer"])}}});var je=F(X,[["__scopeId","data-v-0acb6983"]]);export{je as default};