import s from"./plugins.2e86651e.js";import m from"./toolbar.8bf54a85.js";import l from"./dynamicLoadScript.35d7f4bd.js";import{_ as p}from"./index.4634ff3e.js";import{o as d,c,a as u,X as _,Y as h}from"./@vue.2fc568c1.js";import"./vue-i18n.36c67e35.js";import"./@intlify.7df7362d.js";import"./axios.d2ab6b2c.js";import"./qs.361ab08d.js";import"./side-channel.a4b5bd0c.js";import"./get-intrinsic.1675b33e.js";import"./has-symbols.caae0f97.js";import"./function-bind.cb3858f2.js";import"./has.c1051c46.js";import"./call-bind.0b5a4f26.js";import"./object-inspect.62630b2b.js";import"./dotenv.7e6d7ced.js";import"./@kangc.ec979c3e.js";import"./vue.300b83f3.js";import"./element-plus.c13ad818.js";import"./lodash.01006d1a.js";import"./@element-plus.1487391f.js";import"./@popperjs.d772622c.js";import"./dayjs.7e511c20.js";import"./async-validator.5d25c98b.js";import"./memoize-one.4ee5c96d.js";import"./normalize-wheel-es.9a219a59.js";import"./vuex.a5034c37.js";import"./vue-router.73d0d986.js";import"./nprogress.45e58d6b.js";import"./vue3-sfc-loader.41fe0098.js";import"./@vueuse.e05d5cea.js";import"./throttle-debounce.9c252086.js";import"./sortablejs.3cc0441d.js";import"./echarts.ca7a4e02.js";import"./zrender.9caa3fba.js";import"./tslib.60310f1a.js";/* empty css                      */import"./vite-plugin-mock.33c3a5ac.js";import"./mockjs.9ddc5731.js";import"./path-to-regexp.3eda823b.js";const y="https://cdn.jsdelivr.net/npm/tinymce-all-in-one@4.9.3/tinymce.min.js",g={name:"Tinymce",props:{id:{type:String,default:function(){return"vue-tinymce-"+ +new Date+((Math.random()*1e3).toFixed(0)+"")}},modelValue:{type:String,default:""},toolbar:{type:Array,required:!1,default(){return[]}},menubar:{type:String,default:"edit view format table"},height:{type:[Number,String],required:!1,default:400},width:{type:[Number,String],required:!1,default:"auto"}},data(){return{hasInit:!1,tinymceId:null,fullscreen:!1,languageTypeList:{en:"en",zh:"zh_CN",es:"es_MX",ja:"ja"}}},computed:{containerWidth(){const t=this.width;return/^[\d]+(\.[\d]+)?$/.test(t)?`${t}px`:t}},watch:{modelValue(t){this.hasInit&&this.getContent()!==t&&this.$nextTick(()=>window.tinymce.get(this.tinymceId).setContent(t||""))}},created(){this.tinymceId=this.id},mounted(){this.init()},activated(){window.tinymce&&this.initTinymce()},deactivated(){this.destroyTinymce()},destroyed(){this.destroyTinymce()},methods:{init(){l(y,t=>{if(t){console.log(t.message);return}this.initTinymce()})},initTinymce(){const t=this;window.tinymce.init({selector:`#${this.tinymceId}`,language:this.languageTypeList.zh,height:this.height,body_class:"panel-body ",object_resizing:!0,toolbar:this.toolbar.length>0?this.toolbar:m,menubar:this.menubar,plugins:s,end_container_on_empty_block:!0,powerpaste_word_import:"propmt",powerpaste_html_import:"propmt",powerpaste_allow_local_images:!0,paste_data_images:!0,code_dialog_height:450,code_dialog_width:1e3,advlist_bullet_styles:"square",advlist_number_styles:"default",imagetools_cors_hosts:["www.tinymce.com","codepen.io"],default_link_target:"_blank",link_title:!1,fontsize_formats:"11pt 12pt 12pt 14pt 18pt 24pt 36pt 72pt",nonbreaking_force_tab:!0,init_instance_callback:e=>{t.modelValue&&e.setContent(t.modelValue),t.hasInit=!0,e.on("NodeChange Change KeyUp SetContent",()=>{this.$emit("update:modelValue",e.getContent())})},paste_preprocess:(e,o)=>{let r=[];o.content.replace(/<img [^>]*src=['"]([^'"]+)[^>]*>/gi,(i,n)=>{n.indexOf("//")===0?r.push("http:"+n):r.push(n)}),r.length>0&&this.uploadRemoteFile(r,0)},images_upload_handler:function(e,o,r){let i=e.blob();i.name||(i=new window.File([i],"image.png"));let n=new FormData;n.append("file",i),uploadImage(n).then(a=>{o({}.VUE_APP_Image+"/"+a.data)})},setup(e){e.on("FullscreenStateChanged",o=>{t.fullscreen=o.state})},convert_urls:!1})},destroyTinymce(){const t=window.tinymce.get(this.tinymceId);this.fullscreen&&t.execCommand("mceFullScreen"),t&&t.destroy()},setContent(t){window.tinymce.get(this.tinymceId).setContent(t)},getContent(){return window.tinymce.get(this.tinymceId).getContent()},imageSuccessCBK(t){window.tinymce.get(this.tinymceId).insertContent(`<img src="${{}.VUE_APP_Image}/${t}" >`)},uploadRemoteFile(t,e){}}},f=["id"];function w(t,e,o,r,i,n){return d(),c("div",{class:_([{fullscreen:i.fullscreen},"tinymce-container"]),style:h({width:n.containerWidth})},[u("textarea",{id:i.tinymceId,class:"tinymce-textarea"},null,8,f)],6)}var st=p(g,[["render",w],["__scopeId","data-v-01f2a62e"]]);export{st as default};