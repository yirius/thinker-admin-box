import{_ as e,L as t}from"./index.5105b9ee.js";import a from"./tip.ab4e6e09.js";import{C as o}from"./cropperjs.f8d72405.js";import{Q as s,R as i,S as r}from"./@element-plus.0803672b.js";import{d as l,r as d,w as p,n,a6 as m,o as c,W as u,X as j,a as f,R as b,u as v,$ as g,a2 as y,a0 as h}from"./@vue.d34508ef.js";import"./element-plus.0e8efd86.js";import"./lodash-es.032e1c7e.js";import"./@ctrl.82a509e0.js";import"./@kangc.ff3ddaf0.js";import"./vue.7b877b81.js";import"./async-validator.ed4c92a2.js";import"./memoize-one.4ee5c96d.js";import"./escape-html.e5dfadb9.js";import"./normalize-wheel-es.1c4ac15a.js";import"./@floating-ui.bb85fca7.js";import"./vxe-table.bc3fd82d.js";import"./xe-utils.a7d87cf6.js";import"./vxe-table-plugin-element.64c660a1.js";import"./dayjs.9968bd5d.js";import"./vxe-table-plugin-renderer.8d2437f3.js";import"./vxe-table-plugin-export-xlsx.c90d9631.js";import"./exceljs.61d36b19.js";import"./vue-i18n.d768295e.js";import"./@intlify.7df7362d.js";import"./vuex.a564e100.js";import"./axios.d2ab6b2c.js";import"./qs.b39c9507.js";import"./side-channel.7002e90d.js";import"./get-intrinsic.1675b33e.js";import"./has-symbols.caae0f97.js";import"./function-bind.cb3858f2.js";import"./has.c1051c46.js";import"./call-bind.0b5a4f26.js";import"./object-inspect.0eb10ca6.js";import"./dotenv.0dd75455.js";import"./vue-router.020d7cae.js";import"./nprogress.9b8ff459.js";import"./vue3-sfc-loader.41fe0098.js";import"./@vueuse.6434955c.js";import"./throttle-debounce.f3f727a9.js";import"./sortablejs.479dbdba.js";import"./echarts.50670f84.js";import"./zrender.8a794faa.js";import"./tslib.60310f1a.js";import"./vue-clipboard3.5d961544.js";import"./clipboard.ac68146e.js";import"./mitt.550594b0.js";/* empty css                      */const x={class:"container"},w={class:"box"},_=["src"],C={class:"box"},k={class:"handle-box"},D=h("上传图片"),R=h("下载截图至本地"),z=h("保存截图");var L=e(l({props:{layer:{type:Object,required:!0,default:()=>({show:!1})},modelValue:{type:String,default:""}},emits:["update:modelValue"],setup(e,{emit:l}){const h=e;let L=d(null);const V="image"+(new Date).getTime(),E="preview"+(new Date).getTime();let I=d(null);function U(){L.value&&L.value.destroy();const e=document.getElementById(V);L.value=new o(e,{preview:"."+E})}function W(e,t){!function(e){let t=new FileReader;t.readAsDataURL(e.raw),t.onload=function(e){document.getElementById(V).setAttribute("src",this.result),U()}}(e),I.value.clearFiles()}function q(){var e;const t=null==(e=L.value)?void 0:e.getCroppedCanvas({maxWidth:4096,maxHeight:4096}),a=(null==t?void 0:t.toDataURL())||"",o=document.createElement("a");o.href=a,o.download="download.png",o.click()}function A(){var e;const t=null==(e=L.value)?void 0:e.getCroppedCanvas({maxWidth:4096,maxHeight:4096}),a=null==t?void 0:t.toDataURL();l("update:modelValue",a),h.layer.show=!1}return p((()=>h.layer.show),(e=>{e&&n((()=>{U()}))})),(o,l)=>{const d=m("el-button"),p=m("el-upload");return c(),u(t,{layer:e.layer,width:"1000"},{default:j((()=>[f("div",x,[f("div",w,[f("img",{id:V,class:"default-img",src:e.modelValue},null,8,_)]),f("div",C,[f("div",{class:b([E,"preview"])})])]),v(L)?(c(),u(a,{key:0,cropper:v(L)},null,8,["cropper"])):g("",!0),f("div",k,[y(p,{class:"upload-demo",action:"","auto-upload":!1,accept:"image/*",ref_key:"uploadDom",ref:I,"on-change":W},{default:j((()=>[y(d,{type:"primary",icon:v(s),style:{"margin-right":"10px"}},{default:j((()=>[D])),_:1},8,["icon"])])),_:1},512),y(d,{type:"primary",icon:v(i),onClick:q},{default:j((()=>[R])),_:1},8,["icon"]),y(d,{type:"primary",icon:v(r),onClick:A},{default:j((()=>[z])),_:1},8,["icon"])])])),_:1},8,["layer"])}}}),[["__scopeId","data-v-0acb6983"]]);export{L as default};