import{_ as f}from"./index.dd1da4b6.js";import{a3 as j,k as b,al as h,aF as l,ax as n,V as y,a1 as v,b0 as c,Q as p,U as d,a0 as C,D as z,aE as S,aj as _}from"./@vue.559b001e.js";import"./vue-i18n.b013bd4b.js";import"./@intlify.7df7362d.js";import"./axios.d2ab6b2c.js";import"./qs.67967ad9.js";import"./side-channel.9caedf91.js";import"./get-intrinsic.1675b33e.js";import"./has-symbols.caae0f97.js";import"./function-bind.cb3858f2.js";import"./has.c1051c46.js";import"./call-bind.0b5a4f26.js";import"./object-inspect.a91d3e60.js";import"./xlsx.5af0aaf1.js";import"./mockjs.fbe85ee3.js";import"./element-plus.4e647bc4.js";import"./lodash.79e1ad93.js";import"./@element-plus.f7fcbb73.js";import"./@popperjs.a8a4a6a0.js";import"./dayjs.f5211fdf.js";import"./async-validator.5d25c98b.js";import"./memoize-one.4ee5c96d.js";import"./normalize-wheel-es.9a219a59.js";import"./vuex.7f9e2f80.js";import"./vue-router.7e2488e1.js";import"./nprogress.1eb72115.js";import"./vue3-sfc-loader.41fe0098.js";import"./vue.51eda945.js";import"./@vueuse.ccac29d9.js";import"./throttle-debounce.9c252086.js";import"./sortablejs.3cc0441d.js";import"./echarts.b37547bc.js";import"./zrender.9e5c8901.js";import"./tslib.34a40861.js";/* empty css                      */import"./vite-plugin-mock.027c602a.js";import"./path-to-regexp.3eda823b.js";const k=j({props:{data:{type:Array,default:()=>[]},select:{type:Array,default:()=>[]},showIndex:{type:Boolean,default:!1},showSelection:{type:Boolean,default:!1},showPage:{type:Boolean,default:!0},page:{type:Object,default:()=>({index:1,size:20,total:0})},pageLayout:{type:String,default:"total, sizes, prev, pager, next, jumper"},pageSizes:{type:Array,default:[10,20,50,100]}},setup(e,t){const r=b(null);let o=null;const m=a=>{o?e.page.index=1:(e.page.index=a,t.emit("getTableData"))},u=a=>{o="work",setTimeout(()=>{o=null},100),e.page.size=a,t.emit("getTableData",!0)},s=a=>{t.emit("selection-change",a)};return h(()=>{r.value.doLayout()}),{table:r,handleCurrentChange:m,handleSizeChange:u,handleSelectionChange:s}}}),w={class:"system-table-box"};function x(e,t,r,o,m,u){const s=l("el-table-column"),a=l("el-table"),g=l("el-pagination");return n(),y("div",w,[v(a,_(e.$attrs,{ref:"table",class:"system-table",border:"",height:"100%",data:e.data,onSelectionChange:e.handleSelectionChange}),{default:c(()=>[e.showSelection?(n(),p(s,{key:0,type:"selection",align:"center",width:"50"})):d("",!0),e.showIndex?(n(),p(s,{key:1,label:"\u5E8F\u53F7",width:"60",align:"center"},{default:c(i=>[C(z((e.page.index-1)*e.page.size+i.$index+1),1)]),_:1})):d("",!0),S(e.$slots,"default",{},void 0,!0)]),_:3},16,["data","onSelectionChange"]),e.showPage?(n(),p(g,{key:0,"current-page":e.page.index,"onUpdate:current-page":t[0]||(t[0]=i=>e.page.index=i),class:"system-page",background:"",layout:e.pageLayout,total:e.page.total,"page-size":e.page.size,"page-sizes":e.pageSizes,onCurrentChange:e.handleCurrentChange,onSizeChange:e.handleSizeChange},null,8,["current-page","layout","total","page-size","page-sizes","onCurrentChange","onSizeChange"])):d("",!0)])}var me=f(k,[["render",x],["__scopeId","data-v-1a94e188"]]);export{me as default};
