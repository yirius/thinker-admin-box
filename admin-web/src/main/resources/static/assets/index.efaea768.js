import{_ as e}from"./index.5105b9ee.js";import{d as a,r as t,a$ as s,a6 as o,o as i,c as r,a2 as n,X as l,W as p,$ as d,a0 as m,a1 as c,Q as u,U as g}from"./@vue.d34508ef.js";import"./@element-plus.0803672b.js";import"./element-plus.0e8efd86.js";import"./lodash-es.032e1c7e.js";import"./@ctrl.82a509e0.js";import"./@kangc.ff3ddaf0.js";import"./vue.7b877b81.js";import"./async-validator.ed4c92a2.js";import"./memoize-one.4ee5c96d.js";import"./escape-html.e5dfadb9.js";import"./normalize-wheel-es.1c4ac15a.js";import"./@floating-ui.bb85fca7.js";import"./vxe-table.bc3fd82d.js";import"./xe-utils.a7d87cf6.js";import"./vxe-table-plugin-element.64c660a1.js";import"./dayjs.9968bd5d.js";import"./vxe-table-plugin-renderer.8d2437f3.js";import"./vxe-table-plugin-export-xlsx.c90d9631.js";import"./exceljs.61d36b19.js";import"./vue-i18n.d768295e.js";import"./@intlify.7df7362d.js";import"./vuex.a564e100.js";import"./axios.d2ab6b2c.js";import"./qs.b39c9507.js";import"./side-channel.7002e90d.js";import"./get-intrinsic.1675b33e.js";import"./has-symbols.caae0f97.js";import"./function-bind.cb3858f2.js";import"./has.c1051c46.js";import"./call-bind.0b5a4f26.js";import"./object-inspect.0eb10ca6.js";import"./dotenv.0dd75455.js";import"./vue-router.020d7cae.js";import"./nprogress.9b8ff459.js";import"./vue3-sfc-loader.41fe0098.js";import"./@vueuse.6434955c.js";import"./throttle-debounce.f3f727a9.js";import"./sortablejs.479dbdba.js";import"./echarts.50670f84.js";import"./zrender.8a794faa.js";import"./tslib.60310f1a.js";import"./vue-clipboard3.5d961544.js";import"./clipboard.ac68146e.js";import"./mitt.550594b0.js";/* empty css                      */const j=a({props:{data:{type:Array,default:()=>[]},select:{type:Array,default:()=>[]},showIndex:{type:Boolean,default:!1},showSelection:{type:Boolean,default:!1},showPage:{type:Boolean,default:!0},page:{type:Object,default:()=>({index:1,size:20,total:0})},pageLayout:{type:String,default:"total, sizes, prev, pager, next, jumper"},pageSizes:{type:Array,default:[10,20,50,100]}},setup(e,a){const o=t(null);let i=null;return s((()=>{o.value.doLayout()})),{table:o,handleCurrentChange:t=>{i?e.page.index=1:(e.page.index=t,a.emit("getTableData"))},handleSizeChange:t=>{i="work",setTimeout((()=>{i=null}),100),e.page.size=t,a.emit("getTableData",!0)},handleSelectionChange:e=>{a.emit("selection-change",e)}}}}),b={class:"system-table-box"};var f=e(j,[["render",function(e,a,t,s,j,f){const h=o("el-table-column"),y=o("el-table"),x=o("el-pagination");return i(),r("div",b,[n(y,g(e.$attrs,{ref:"table",class:"system-table",border:"",height:"100%",data:e.data,onSelectionChange:e.handleSelectionChange}),{default:l((()=>[e.showSelection?(i(),p(h,{key:0,type:"selection",align:"center",width:"50"})):d("",!0),e.showIndex?(i(),p(h,{key:1,label:"序号",width:"60",align:"center"},{default:l((a=>[m(c((e.page.index-1)*e.page.size+a.$index+1),1)])),_:1})):d("",!0),u(e.$slots,"default",{},void 0,!0)])),_:3},16,["data","onSelectionChange"]),e.showPage?(i(),p(x,{key:0,"current-page":e.page.index,"onUpdate:current-page":a[0]||(a[0]=a=>e.page.index=a),class:"system-page",background:"",layout:e.pageLayout,total:e.page.total,"page-size":e.page.size,"page-sizes":e.pageSizes,onCurrentChange:e.handleCurrentChange,onSizeChange:e.handleSizeChange},null,8,["current-page","layout","total","page-size","page-sizes","onCurrentChange","onSizeChange"])):d("",!0)])}],["__scopeId","data-v-92b11d46"]]);export{f as default};
