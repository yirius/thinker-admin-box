import r from"./plugins.2e86651e.js";import m from"./toolbar.8bf54a85.js";import l from"./dynamicLoadScript.35d7f4bd.js";import{_ as d}from"./index.dd1da4b6.js";import{ax as c,V as p,W as u,A as f,C as h}from"./@vue.559b001e.js";import"./vue-i18n.b013bd4b.js";import"./@intlify.7df7362d.js";import"./axios.d2ab6b2c.js";import"./qs.67967ad9.js";import"./side-channel.9caedf91.js";import"./get-intrinsic.1675b33e.js";import"./has-symbols.caae0f97.js";import"./function-bind.cb3858f2.js";import"./has.c1051c46.js";import"./call-bind.0b5a4f26.js";import"./object-inspect.a91d3e60.js";import"./xlsx.5af0aaf1.js";import"./mockjs.fbe85ee3.js";import"./element-plus.4e647bc4.js";import"./lodash.79e1ad93.js";import"./@element-plus.f7fcbb73.js";import"./@popperjs.a8a4a6a0.js";import"./dayjs.f5211fdf.js";import"./async-validator.5d25c98b.js";import"./memoize-one.4ee5c96d.js";import"./normalize-wheel-es.9a219a59.js";import"./vuex.7f9e2f80.js";import"./vue-router.7e2488e1.js";import"./nprogress.1eb72115.js";import"./vue3-sfc-loader.41fe0098.js";import"./vue.51eda945.js";import"./@vueuse.ccac29d9.js";import"./throttle-debounce.9c252086.js";import"./sortablejs.3cc0441d.js";import"./echarts.b37547bc.js";import"./zrender.9e5c8901.js";import"./tslib.34a40861.js";/* empty css                      */import"./vite-plugin-mock.027c602a.js";import"./path-to-regexp.3eda823b.js";const _="https://cdn.jsdelivr.net/npm/tinymce-all-in-one@4.9.3/tinymce.min.js",y={name:"Tinymce",props:{id:{type:String,default:function(){return"vue-tinymce-"+ +new Date+((Math.random()*1e3).toFixed(0)+"")}},modelValue:{type:String,default:""},toolbar:{type:Array,required:!1,default(){return[]}},menubar:{type:String,default:"edit view format table"},height:{type:[Number,String],required:!1,default:400},width:{type:[Number,String],required:!1,default:"auto"}},data(){return{hasInit:!1,tinymceId:null,fullscreen:!1,languageTypeList:{en:"en",zh:"zh_CN",es:"es_MX",ja:"ja"}}},computed:{containerWidth(){const e=this.width;return/^[\d]+(\.[\d]+)?$/.test(e)?`${e}px`:e}},watch:{modelValue(e){this.hasInit&&this.getContent()!==e&&this.$nextTick(()=>window.tinymce.get(this.tinymceId).setContent(e||""))}},created(){this.tinymceId=this.id},mounted(){this.init()},activated(){window.tinymce&&this.initTinymce()},deactivated(){this.destroyTinymce()},destroyed(){this.destroyTinymce()},methods:{init(){l(_,e=>{if(e){console.log(e.message);return}this.initTinymce()})},initTinymce(){const e=this;window.tinymce.init({selector:`#${this.tinymceId}`,language:this.languageTypeList.zh,height:this.height,body_class:"panel-body ",object_resizing:!0,toolbar:this.toolbar.length>0?this.toolbar:m,menubar:this.menubar,plugins:r,end_container_on_empty_block:!0,powerpaste_word_import:"propmt",powerpaste_html_import:"propmt",powerpaste_allow_local_images:!0,paste_data_images:!0,code_dialog_height:450,code_dialog_width:1e3,advlist_bullet_styles:"square",advlist_number_styles:"default",imagetools_cors_hosts:["www.tinymce.com","codepen.io"],default_link_target:"_blank",link_title:!1,fontsize_formats:"11pt 12pt 12pt 14pt 18pt 24pt 36pt 72pt",nonbreaking_force_tab:!0,init_instance_callback:t=>{e.modelValue&&t.setContent(e.modelValue),e.hasInit=!0,t.on("NodeChange Change KeyUp SetContent",()=>{this.$emit("update:modelValue",t.getContent())})},paste_preprocess:(t,s)=>{let a=[];s.content.replace(/<img [^>]*src=['"]([^'"]+)[^>]*>/gi,(i,n)=>{n.indexOf("//")===0?a.push("http:"+n):a.push(n)}),a.length>0&&this.uploadRemoteFile(a,0)},images_upload_handler:function(t,s,a){let i=t.blob();i.name||(i=new window.File([i],"image.png"));let n=new FormData;n.append("file",i),uploadImage(n).then(o=>{s({}.VUE_APP_Image+"/"+o.data)})},setup(t){t.on("FullscreenStateChanged",s=>{e.fullscreen=s.state})},convert_urls:!1})},destroyTinymce(){const e=window.tinymce.get(this.tinymceId);this.fullscreen&&e.execCommand("mceFullScreen"),e&&e.destroy()},setContent(e){window.tinymce.get(this.tinymceId).setContent(e)},getContent(){return window.tinymce.get(this.tinymceId).getContent()},imageSuccessCBK(e){window.tinymce.get(this.tinymceId).insertContent(`<img src="${{}.VUE_APP_Image}/${e}" >`)},uploadRemoteFile(e,t){}}},g=["id"];function b(e,t,s,a,i,n){return c(),p("div",{class:f([{fullscreen:i.fullscreen},"tinymce-container"]),style:h({width:n.containerWidth})},[u("textarea",{id:i.tinymceId,class:"tinymce-textarea"},null,8,g)],6)}var oe=d(y,[["render",b],["__scopeId","data-v-01f2a62e"]]);export{oe as default};