import r from"./plugins.63b05a92.js";import o from"./toolbar.992921f9.js";import l from"./dynamicLoadScript.35d7f4bd.js";import{uploadHttpRequestApi as m}from"./index.e9719bc9.js";import{_ as c}from"./index.a744804a.js";import{o as p,c as d,a as u,R as h,S as f}from"./@vue.1015dcbd.js";import"./@element-plus.220d6db7.js";import"./element-plus.0c0d1b6e.js";import"./lodash-es.032e1c7e.js";import"./@ctrl.82a509e0.js";import"./dayjs.818e5577.js";import"./@kangc.f912404d.js";import"./vue.9b77f3f7.js";import"./async-validator.5d25c98b.js";import"./memoize-one.4ee5c96d.js";import"./escape-html.e5dfadb9.js";import"./normalize-wheel-es.1c4ac15a.js";import"./@floating-ui.1ef3caef.js";import"./vxe-table.af633bdf.js";import"./xe-utils.a7d87cf6.js";import"./vxe-table-plugin-element.e4063f60.js";import"./vxe-table-plugin-export-xlsx.25030aa5.js";import"./exceljs.3364a90c.js";import"./vue-i18n.e5b09f92.js";import"./@intlify.7df7362d.js";import"./vuex.fa98a0d6.js";import"./axios.d2ab6b2c.js";import"./qs.6ea39d31.js";import"./side-channel.f7ae5909.js";import"./get-intrinsic.1675b33e.js";import"./has-symbols.caae0f97.js";import"./function-bind.cb3858f2.js";import"./has.c1051c46.js";import"./call-bind.0b5a4f26.js";import"./object-inspect.a81284f5.js";import"./dotenv.04d4ebad.js";import"./vue-router.87d0219e.js";import"./nprogress.9823fd6c.js";import"./vue3-sfc-loader.41fe0098.js";import"./@vueuse.38f6f3c4.js";import"./throttle-debounce.f3f727a9.js";import"./sortablejs.939c3627.js";import"./echarts.a4c9e4fe.js";import"./zrender.d49f868d.js";import"./tslib.60310f1a.js";/* empty css                      */import"./vite-plugin-mock.03a04ca1.js";import"./mockjs.bc2d1bb5.js";import"./path-to-regexp.3eda823b.js";const g="/tinymce/tinymce.min.js",y={name:"Tinymce",props:{id:{type:String,default:function(){return"vue-tinymce-"+ +new Date+((Math.random()*1e3).toFixed(0)+"")}},modelValue:{type:String,default:""},toolbar:{type:Array,required:!1,default(){return[]}},plugins:{type:Array,required:!1,default(){return[]}},menubar:{type:String,default:"edit view format table"},height:{type:[Number,String],required:!1,default:400},width:{type:[Number,String],required:!1,default:"auto"},uploadUrl:{type:String,required:!1,default:"/thinker/admin/upload"},fontsize:{type:String,required:!1,default:"12px 14px 16px 18px 24px 36px 48px 56px 72px"},linkList:{type:Array,required:!1,default:[]},imageList:{type:Array,required:!1,default:[]},templateList:{type:Array,required:!1,default:[]},tplReplaceValues:{type:Object,required:!1,default:{}}},data(){return{hasInit:!1,tinymceId:null,fullscreen:!1,languageTypeList:{en:"en",zh:"zh_CN",es:"es_MX",ja:"ja"}}},computed:{containerWidth(){const e=this.width;return/^[\d]+(\.[\d]+)?$/.test(e)?`${e}px`:e}},watch:{modelValue(e){this.hasInit&&this.getContent()!==e&&this.$nextTick(()=>window.tinymce.get(this.tinymceId).setContent(e||""))}},created(){this.tinymceId=this.id},mounted(){this.init()},activated(){window.tinymce&&this.initTinymce()},deactivated(){this.destroyTinymce()},destroyed(){this.destroyTinymce()},methods:{init(){l(g,e=>{if(e){console.log(e.message);return}this.initTinymce()})},initTinymce(){const e=this;window.tinymce.init({selector:`#${this.tinymceId}`,content_style:"img {max-width:100%;}",language:this.languageTypeList.zh,body_class:"panel-body ",object_resizing:!0,plugins:this.plugins.length>0?this.plugins:r,toolbar:this.toolbar.length>0?this.toolbar:o,fontsize_formats:this.fontsize,font_formats:"\u5FAE\u8F6F\u96C5\u9ED1=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;\u82F9\u679C\u82F9\u65B9=PingFang SC,Microsoft YaHei,sans-serif;\u5B8B\u4F53=simsun,serif;\u4EFF\u5B8B\u4F53=FangSong,serif;\u9ED1\u4F53=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sans-serif;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Symbol=symbol;Tahoma=tahoma,arial,helvetica,sans-serif;Terminal=terminal,monaco;Times New Roman=times new roman,times;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings,zapf dingbats;\u77E5\u4E4E\u914D\u7F6E=BlinkMacSystemFont, Helvetica Neue, PingFang SC, Microsoft YaHei, Source Han Sans SC, Noto Sans CJK SC, WenQuanYi Micro Hei, sans-serif;\u5C0F\u7C73\u914D\u7F6E=Helvetica Neue,Helvetica,Arial,Microsoft Yahei,Hiragino Sans GB,Heiti SC,WenQuanYi Micro Hei,sans-serif",height:this.height,min_height:400,link_list:this.linkList&&this.linkList.length>0?this.linkList:null,image_list:this.imageList&&this.imageList.length>0?this.imageList:null,templates:this.templateList&&this.templateList.length>0?this.templateList:null,template_cdate_format:"%Y-%m-%d %H:%M:%S",template_mdate_format:"%Y-%m-%d",template_replace_values:this.tplReplaceValues,template_selected_content_classes:"selcontent",extended_valid_elements:"script[src]",autosave_ask_before_unload:!1,toolbar_mode:"wrap",automatic_uploads:!0,images_upload_handler:(t,a,n)=>{m({action:this.uploadUrl,filename:"file",file:t.blob(),data:{},method:"POST",headers:{}}).then(i=>{a(i.data.file)}).catch(i=>{n(i.msg||"\u672A\u77E5\u9519\u8BEF")})},init_instance_callback:t=>{e.modelValue&&t.setContent(e.modelValue),e.hasInit=!0,t.on("NodeChange Change KeyUp SetContent",()=>{this.$emit("update:modelValue",t.getContent())})},setup(t){t.on("FullscreenStateChanged",a=>{e.fullscreen=a.state})},convert_urls:!1})},destroyTinymce(){const e=window.tinymce.get(this.tinymceId);this.fullscreen&&e.execCommand("mceFullScreen"),e&&e.destroy()},setContent(e){window.tinymce.get(this.tinymceId).setContent(e)},getContent(){return window.tinymce.get(this.tinymceId).getContent()},imageSuccessCBK(e){window.tinymce.get(this.tinymceId).insertContent(`<img src="${{}.VUE_APP_Image}/${e}" >`)},uploadRemoteFile(e,t){}}},_=["id"];function S(e,t,a,n,i,s){return p(),d("div",{class:h([{fullscreen:i.fullscreen},"tinymce-container"]),style:f({width:s.containerWidth})},[u("textarea",{id:i.tinymceId,class:"tinymce-textarea"},null,8,_)],6)}var fe=c(y,[["render",S],["__scopeId","data-v-4beda8da"]]);export{fe as default};