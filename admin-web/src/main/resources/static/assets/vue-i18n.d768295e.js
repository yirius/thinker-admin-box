import{m as e,a as t,i as a,b as n,c as l,d as o,e as s,f as r,g as i,h as c,r as u,j as _,s as m,k as f,l as p,n as g,o as b,p as d,q as v,u as h,V as y,t as k,v as L,w as I,x as F,y as E,z as O,A as N,N as T,B as D,C as M,M as S,D as P,E as V,F as W,G as w,H as $}from"./@intlify.7df7362d.js";import{O as U,I as R,g as j,K as H,k as C,e as A,i as Y,r as G,f as x,w as z,bA as B,a2 as q,a8 as J}from"./@vue.d34508ef.js";
/*!
  * vue-i18n v9.1.9
  * (c) 2021 kazuya kawaguchi
  * Released under the MIT License.
  */function K(e,...t){return r(e,null,void 0)}const Z=e("__transrateVNode"),Q=e("__datetimeParts"),X=e("__numberParts"),ee=e("__enableEmitter"),te=e("__disableEmitter"),ae=e("__setPluralRules");e("__intlifyMeta");const ne=e("__injectWithOption");let le=0;function oe(e){return(t,a,n,l)=>e(a,n,j()||void 0,l)}function se(e,t){const{messages:a,__i18n:n}=t,l=f(a)?a:o(n)?{}:{[e]:{}};if(o(n)&&n.forEach((({locale:e,resource:t})=>{e?(l[e]=l[e]||{},ie(t,l[e])):ie(t,l)})),t.flatJson)for(const o in l)p(l,o)&&g(l[o]);return l}const re=e=>!l(e)||o(e);function ie(e,t){if(re(e)||re(t))throw K(20);for(const a in e)p(e,a)&&(re(e[a])||re(t[a])?t[a]=e[a]:ie(e[a],t[a]))}function ce(e={}){const{__root:r}=e,i=void 0===r;let c=!s(e.inheritLocale)||e.inheritLocale;const u=G(r&&c?r.locale.value:n(e.locale)?e.locale:"en-US"),_=G(r&&c?r.fallbackLocale.value:n(e.fallbackLocale)||o(e.fallbackLocale)||f(e.fallbackLocale)||!1===e.fallbackLocale?e.fallbackLocale:u.value),m=G(se(u.value,e)),p=G(f(e.datetimeFormats)?e.datetimeFormats:{[u.value]:{}}),g=G(f(e.numberFormats)?e.numberFormats:{[u.value]:{}});let y=r?r.missingWarn:!s(e.missingWarn)&&!b(e.missingWarn)||e.missingWarn,k=r?r.fallbackWarn:!s(e.fallbackWarn)&&!b(e.fallbackWarn)||e.fallbackWarn,L=r?r.fallbackRoot:!s(e.fallbackRoot)||e.fallbackRoot,I=!!e.fallbackFormat,U=d(e.missing)?e.missing:null,R=d(e.missing)?oe(e.missing):null,H=d(e.postTranslation)?e.postTranslation:null,C=!s(e.warnHtmlMessage)||e.warnHtmlMessage,A=!!e.escapeParameter;const Y=r?r.modifiers:f(e.modifiers)?e.modifiers:{};let B,ee=e.pluralRules||r&&r.pluralRules;B=v({version:"9.1.9",locale:u.value,fallbackLocale:_.value,messages:m.value,datetimeFormats:p.value,numberFormats:g.value,modifiers:Y,pluralRules:ee,missing:null===R?void 0:R,missingWarn:y,fallbackWarn:k,fallbackFormat:I,unresolving:!0,postTranslation:null===H?void 0:H,warnHtmlMessage:C,escapeParameter:A,__datetimeFormatters:f(B)?B.__datetimeFormatters:void 0,__numberFormatters:f(B)?B.__numberFormatters:void 0,__v_emitter:f(B)?B.__v_emitter:void 0,__meta:{framework:"vue"}}),h(B,u.value,_.value);const te=x({get:()=>u.value,set:e=>{u.value=e,B.locale=u.value}}),re=x({get:()=>_.value,set:e=>{_.value=e,B.fallbackLocale=_.value,h(B,u.value,e)}}),ce=x((()=>m.value)),ue=x((()=>p.value)),_e=x((()=>g.value));function me(e,t,n,l,o,s){let i;if(u.value,_.value,m.value,p.value,g.value,__INTLIFY_PROD_DEVTOOLS__)try{N((()=>{const e=j();return e&&e.type.__INTLIFY_META__?{__INTLIFY_META__:e.type.__INTLIFY_META__}:null})()),i=e(B)}finally{N(null)}else i=e(B);if(a(i)&&i===T){const[e,a]=t();return r&&L?l(r):o(e)}if(s(i))return i;throw K(14)}function fe(...e){return me((t=>M(t,...e)),(()=>D(...e)),0,(t=>t.t(...e)),(e=>e),(e=>n(e)))}const pe={normalize:function(e){return e.map((e=>n(e)?q(J,null,e,0):e))},interpolate:e=>e,type:"vnode"};function ge(e){return m.value[e]||{}}le++,r&&(z(r.locale,(e=>{c&&(u.value=e,B.locale=e,h(B,u.value,_.value))})),z(r.fallbackLocale,(e=>{c&&(_.value=e,B.fallbackLocale=e,h(B,u.value,_.value))})));return{id:le,locale:te,fallbackLocale:re,get inheritLocale(){return c},set inheritLocale(e){c=e,e&&r&&(u.value=r.locale.value,_.value=r.fallbackLocale.value,h(B,u.value,_.value))},get availableLocales(){return Object.keys(m.value).sort()},messages:ce,datetimeFormats:ue,numberFormats:_e,get modifiers(){return Y},get pluralRules(){return ee||{}},get isGlobal(){return i},get missingWarn(){return y},set missingWarn(e){y=e,B.missingWarn=y},get fallbackWarn(){return k},set fallbackWarn(e){k=e,B.fallbackWarn=k},get fallbackRoot(){return L},set fallbackRoot(e){L=e},get fallbackFormat(){return I},set fallbackFormat(e){I=e,B.fallbackFormat=I},get warnHtmlMessage(){return C},set warnHtmlMessage(e){C=e,B.warnHtmlMessage=e},get escapeParameter(){return A},set escapeParameter(e){A=e,B.escapeParameter=e},t:fe,rt:function(...e){const[a,n,o]=e;if(o&&!l(o))throw K(15);return fe(a,n,t({resolvedMessage:!0},o||{}))},d:function(...e){return me((t=>V(t,...e)),(()=>P(...e)),0,(t=>t.d(...e)),(()=>S),(e=>n(e)))},n:function(...e){return me((t=>w(t,...e)),(()=>W(...e)),0,(t=>t.n(...e)),(()=>S),(e=>n(e)))},te:function(e,t){const a=ge(n(t)?t:u.value);return null!==F(a,e)},tm:function(e){const t=function(e){let t=null;const a=$(B,_.value,u.value);for(let n=0;n<a.length;n++){const l=m.value[a[n]]||{},o=F(l,e);if(null!=o){t=o;break}}return t}(e);return null!=t?t:r&&r.tm(e)||{}},getLocaleMessage:ge,setLocaleMessage:function(e,t){m.value[e]=t,B.messages=m.value},mergeLocaleMessage:function(e,t){m.value[e]=m.value[e]||{},ie(t,m.value[e]),B.messages=m.value},getDateTimeFormat:function(e){return p.value[e]||{}},setDateTimeFormat:function(e,t){p.value[e]=t,B.datetimeFormats=p.value,E(B,e,t)},mergeDateTimeFormat:function(e,a){p.value[e]=t(p.value[e]||{},a),B.datetimeFormats=p.value,E(B,e,a)},getNumberFormat:function(e){return g.value[e]||{}},setNumberFormat:function(e,t){g.value[e]=t,B.numberFormats=g.value,O(B,e,t)},mergeNumberFormat:function(e,a){g.value[e]=t(g.value[e]||{},a),B.numberFormats=g.value,O(B,e,a)},getPostTranslationHandler:function(){return d(H)?H:null},setPostTranslationHandler:function(e){H=e,B.postTranslation=e},getMissingHandler:function(){return U},setMissingHandler:function(e){null!==e&&(R=oe(e)),U=e,B.missing=R},[Z]:function(...e){return me((t=>{let a;const n=t;try{n.processor=pe,a=M(n,...e)}finally{n.processor=null}return a}),(()=>D(...e)),0,(t=>t[Z](...e)),(e=>[q(J,null,e,0)]),(e=>o(e)))},[X]:function(...e){return me((t=>w(t,...e)),(()=>W(...e)),0,(t=>t[X](...e)),(()=>[]),(e=>n(e)||o(e)))},[Q]:function(...e){return me((t=>V(t,...e)),(()=>P(...e)),0,(t=>t[Q](...e)),(()=>[]),(e=>n(e)||o(e)))},[ae]:function(e){ee=e,B.pluralRules=ee},[ne]:e.__injectWithOption}}function ue(e={}){const l=ce(function(e){const a=n(e.locale)?e.locale:"en-US",l=n(e.fallbackLocale)||o(e.fallbackLocale)||f(e.fallbackLocale)||!1===e.fallbackLocale?e.fallbackLocale:a,r=d(e.missing)?e.missing:void 0,i=!s(e.silentTranslationWarn)&&!b(e.silentTranslationWarn)||!e.silentTranslationWarn,c=!s(e.silentFallbackWarn)&&!b(e.silentFallbackWarn)||!e.silentFallbackWarn,u=!s(e.fallbackRoot)||e.fallbackRoot,_=!!e.formatFallbackMessages,m=f(e.modifiers)?e.modifiers:{},p=e.pluralizationRules,g=d(e.postTranslation)?e.postTranslation:void 0,v=!n(e.warnHtmlInMessage)||"off"!==e.warnHtmlInMessage,h=!!e.escapeParameterHtml,y=!s(e.sync)||e.sync;let k=e.messages;if(f(e.sharedMessages)){const a=e.sharedMessages;k=Object.keys(a).reduce(((e,n)=>{const l=e[n]||(e[n]={});return t(l,a[n]),e}),k||{})}const{__i18n:L,__root:I,__injectWithOption:F}=e,E=e.datetimeFormats,O=e.numberFormats;return{locale:a,fallbackLocale:l,messages:k,flatJson:e.flatJson,datetimeFormats:E,numberFormats:O,missing:r,missingWarn:i,fallbackWarn:c,fallbackRoot:u,fallbackFormat:_,modifiers:m,pluralRules:p,postTranslation:g,warnHtmlMessage:v,escapeParameter:h,inheritLocale:y,__i18n:L,__root:I,__injectWithOption:F}}(e)),r={id:l.id,get locale(){return l.locale.value},set locale(e){l.locale.value=e},get fallbackLocale(){return l.fallbackLocale.value},set fallbackLocale(e){l.fallbackLocale.value=e},get messages(){return l.messages.value},get datetimeFormats(){return l.datetimeFormats.value},get numberFormats(){return l.numberFormats.value},get availableLocales(){return l.availableLocales},get formatter(){return{interpolate:()=>[]}},set formatter(e){},get missing(){return l.getMissingHandler()},set missing(e){l.setMissingHandler(e)},get silentTranslationWarn(){return s(l.missingWarn)?!l.missingWarn:l.missingWarn},set silentTranslationWarn(e){l.missingWarn=s(e)?!e:e},get silentFallbackWarn(){return s(l.fallbackWarn)?!l.fallbackWarn:l.fallbackWarn},set silentFallbackWarn(e){l.fallbackWarn=s(e)?!e:e},get modifiers(){return l.modifiers},get formatFallbackMessages(){return l.fallbackFormat},set formatFallbackMessages(e){l.fallbackFormat=e},get postTranslation(){return l.getPostTranslationHandler()},set postTranslation(e){l.setPostTranslationHandler(e)},get sync(){return l.inheritLocale},set sync(e){l.inheritLocale=e},get warnHtmlInMessage(){return l.warnHtmlMessage?"warn":"off"},set warnHtmlInMessage(e){l.warnHtmlMessage="off"!==e},get escapeParameterHtml(){return l.escapeParameter},set escapeParameterHtml(e){l.escapeParameter=e},get preserveDirectiveContent(){return!0},set preserveDirectiveContent(e){},get pluralizationRules(){return l.pluralRules||{}},__composer:l,t(...e){const[t,a,s]=e,r={};let i=null,c=null;if(!n(t))throw K(15);const u=t;return n(a)?r.locale=a:o(a)?i=a:f(a)&&(c=a),o(s)?i=s:f(s)&&(c=s),l.t(u,i||c||{},r)},rt:(...e)=>l.rt(...e),tc(...e){const[t,s,r]=e,i={plural:1};let c=null,u=null;if(!n(t))throw K(15);const _=t;return n(s)?i.locale=s:a(s)?i.plural=s:o(s)?c=s:f(s)&&(u=s),n(r)?i.locale=r:o(r)?c=r:f(r)&&(u=r),l.t(_,c||u||{},i)},te:(e,t)=>l.te(e,t),tm:e=>l.tm(e),getLocaleMessage:e=>l.getLocaleMessage(e),setLocaleMessage(e,t){l.setLocaleMessage(e,t)},mergeLocaleMessage(e,t){l.mergeLocaleMessage(e,t)},d:(...e)=>l.d(...e),getDateTimeFormat:e=>l.getDateTimeFormat(e),setDateTimeFormat(e,t){l.setDateTimeFormat(e,t)},mergeDateTimeFormat(e,t){l.mergeDateTimeFormat(e,t)},n:(...e)=>l.n(...e),getNumberFormat:e=>l.getNumberFormat(e),setNumberFormat(e,t){l.setNumberFormat(e,t)},mergeNumberFormat(e,t){l.mergeNumberFormat(e,t)},getChoiceIndex:(e,t)=>-1,__onComponentInstanceCreated(t){const{componentInstanceCreatedListener:a}=e;a&&a(t,r)}};return r}const _e={tag:{type:[String,Object]},locale:{type:String},scope:{type:String,validator:e=>"parent"===e||"global"===e,default:"parent"},i18n:{type:Object}},me={name:"i18n-t",props:t({keypath:{type:String,required:!0},plural:{type:[Number,String],validator:e=>a(e)||!isNaN(e)}},_e),setup(e,a){const{slots:o,attrs:s}=a,r=e.i18n||Me({useScope:e.scope,__useComponent:!0}),i=Object.keys(o).filter((e=>"_"!==e));return()=>{const o={};e.locale&&(o.locale=e.locale),void 0!==e.plural&&(o.plural=n(e.plural)?+e.plural:e.plural);const c=function({slots:e},t){return 1===t.length&&"default"===t[0]?e.default?e.default():[]:t.reduce(((t,a)=>{const n=e[a];return n&&(t[a]=n()),t}),{})}(a,i),u=r[Z](e.keypath,c,o),_=t({},s);return n(e.tag)||l(e.tag)?U(e.tag,_,u):U(R,_,u)}}};function fe(e,a,s,r){const{slots:i,attrs:c}=a;return()=>{const a={part:!0};let u={};e.locale&&(a.locale=e.locale),n(e.format)?a.key=e.format:l(e.format)&&(n(e.format.key)&&(a.key=e.format.key),u=Object.keys(e.format).reduce(((a,n)=>s.includes(n)?t({},a,{[n]:e.format[n]}):a),{}));const _=r(e.value,a,u);let m=[a.key];o(_)?m=_.map(((e,t)=>{const a=i[e.type];return a?a({[e.type]:e.value,index:t,parts:_}):[e.value]})):n(_)&&(m=[_]);const f=t({},c);return n(e.tag)||l(e.tag)?U(e.tag,f,m):U(R,f,m)}}const pe=["localeMatcher","style","unit","unitDisplay","currency","currencyDisplay","useGrouping","numberingSystem","minimumIntegerDigits","minimumFractionDigits","maximumFractionDigits","minimumSignificantDigits","maximumSignificantDigits","notation","formatMatcher"],ge={name:"i18n-n",props:t({value:{type:Number,required:!0},format:{type:[String,Object]}},_e),setup(e,t){const a=e.i18n||Me({useScope:"parent",__useComponent:!0});return fe(e,t,pe,((...e)=>a[X](...e)))}},be=["dateStyle","timeStyle","fractionalSecondDigits","calendar","dayPeriod","numberingSystem","localeMatcher","timeZone","hour12","hourCycle","formatMatcher","weekday","era","year","month","day","hour","minute","second","timeZoneName"],de={name:"i18n-d",props:t({value:{type:[Number,Date],required:!0},format:{type:[String,Object]}},_e),setup(e,t){const a=e.i18n||Me({useScope:"parent",__useComponent:!0});return fe(e,t,be,((...e)=>a[Q](...e)))}};function ve(e){const t=(t,{instance:l,value:o,modifiers:s})=>{if(!l||!l.$)throw K(22);const r=function(e,t){const a=e;if("composition"===e.mode)return a.__getInstance(t)||e.global;{const n=a.__getInstance(t);return null!=n?n.__composer:e.global.__composer}}(e,l.$),i=function(e){if(n(e))return{path:e};if(f(e)){if(!("path"in e))throw K(19);return e}throw K(20)}(o);t.textContent=r.t(...function(e){const{path:t,locale:l,args:o,choice:s,plural:r}=e,i={},c=o||{};n(l)&&(i.locale=l);a(s)&&(i.plural=s);a(r)&&(i.plural=r);return[t,c,i]}(i))};return{beforeMount:t,beforeUpdate:t}}let he;async function ye(e,t){return new Promise(((a,r)=>{try{B({id:"vue-devtools-plugin-vue-i18n",label:y["vue-devtools-plugin-vue-i18n"],packageName:"vue-i18n",homepage:"https://vue-i18n.intlify.dev",logo:"https://vue-i18n.intlify.dev/vue-i18n-devtools-logo.png",componentStateTypes:["vue-i18n: composer properties"],app:e},(r=>{he=r,r.on.visitComponentTree((({componentInstance:e,treeNode:a})=>{!function(e,t,a){const n="composition"===a.mode?a.global:a.global.__composer;if(e&&e.vnode.el.__VUE_I18N__&&e.vnode.el.__VUE_I18N__!==n){const a={label:`i18n (${e.type.name||e.type.displayName||e.type.__file} Scope)`,textColor:0,backgroundColor:16764185};t.tags.push(a)}}(e,a,t)})),r.on.inspectComponent((({componentInstance:e,instanceData:a})=>{e.vnode.el.__VUE_I18N__&&a&&("legacy"===t.mode?e.vnode.el.__VUE_I18N__!==t.global.__composer&&ke(a,e.vnode.el.__VUE_I18N__):ke(a,e.vnode.el.__VUE_I18N__))})),r.addInspector({id:"vue-i18n-resource-inspector",label:y["vue-i18n-resource-inspector"],icon:"language",treeFilterPlaceholder:k["vue-i18n-resource-inspector"]}),r.on.getInspectorTree((a=>{a.app===e&&"vue-i18n-resource-inspector"===a.inspectorId&&function(e,t){e.rootNodes.push({id:"global",label:"Global Scope"});const a="composition"===t.mode?t.global:t.global.__composer;for(const[n,l]of t.__instances){const o="composition"===t.mode?l:l.__composer;if(a===o)continue;const s=n.type.name||n.type.displayName||n.type.__file;e.rootNodes.push({id:o.id.toString(),label:`${s} Scope`})}}(a,t)})),r.on.getInspectorState((a=>{a.app===e&&"vue-i18n-resource-inspector"===a.inspectorId&&function(e,t){const a=Oe(e.nodeId,t);a&&(e.state=function(e){const t={},a="Locale related info",n=[{type:a,key:"locale",editable:!0,value:e.locale.value},{type:a,key:"fallbackLocale",editable:!0,value:e.fallbackLocale.value},{type:a,key:"availableLocales",editable:!1,value:e.availableLocales},{type:a,key:"inheritLocale",editable:!0,value:e.inheritLocale}];t[a]=n;const l="Locale messages info",o=[{type:l,key:"messages",editable:!1,value:Le(e.messages.value)}];t[l]=o;const s="Datetime formats info",r=[{type:s,key:"datetimeFormats",editable:!1,value:e.datetimeFormats.value}];t[s]=r;const i="Datetime formats info",c=[{type:i,key:"numberFormats",editable:!1,value:e.numberFormats.value}];return t[i]=c,t}(a))}(a,t)})),r.on.editInspectorState((a=>{a.app===e&&"vue-i18n-resource-inspector"===a.inspectorId&&function(e,t){const a=Oe(e.nodeId,t);if(a){const[t]=e.path;"locale"===t&&n(e.state.value)?a.locale.value=e.state.value:"fallbackLocale"===t&&(n(e.state.value)||o(e.state.value)||l(e.state.value))?a.fallbackLocale.value=e.state.value:"inheritLocale"===t&&s(e.state.value)&&(a.inheritLocale=e.state.value)}}(a,t)})),r.addTimelineLayer({id:"vue-i18n-timeline",label:y["vue-i18n-timeline"],color:L["vue-i18n-timeline"]}),a(!0)}))}catch(i){r(!1)}}))}function ke(e,t){const a="vue-i18n: composer properties";e.state.push({type:a,key:"locale",editable:!0,value:t.locale.value}),e.state.push({type:a,key:"availableLocales",editable:!1,value:t.availableLocales}),e.state.push({type:a,key:"fallbackLocale",editable:!0,value:t.fallbackLocale.value}),e.state.push({type:a,key:"inheritLocale",editable:!0,value:t.inheritLocale}),e.state.push({type:a,key:"messages",editable:!1,value:Le(t.messages.value)}),e.state.push({type:a,key:"datetimeFormats",editable:!1,value:t.datetimeFormats.value}),e.state.push({type:a,key:"numberFormats",editable:!1,value:t.numberFormats.value})}function Le(e){const t={};return Object.keys(e).forEach((a=>{const n=e[a];var o;d(n)&&"source"in n?t[a]={_custom:{type:"function",display:"<span>ƒ</span> "+((o=n).source?`("${Fe(o.source)}")`:"(?)")}}:l(n)?t[a]=Le(n):t[a]=n})),t}const Ie={"<":"&lt;",">":"&gt;",'"':"&quot;","&":"&amp;"};function Fe(e){return e.replace(/[<>"&]/g,Ee)}function Ee(e){return Ie[e]||e}function Oe(e,t){if("global"===e)return"composition"===t.mode?t.global:t.global.__composer;{const a=Array.from(t.__instances.values()).find((t=>t.id.toString()===e));return a?"composition"===t.mode?a:a.__composer:null}}function Ne(e,t){if(he){let a;t&&"groupId"in t&&(a=t.groupId,delete t.groupId),he.addTimelineEvent({layerId:"vue-i18n-timeline",event:{title:e,groupId:a,time:Date.now(),meta:{},data:t||{},logType:"compile-error"===e?"error":"fallback"===e||"missing"===e?"warning":"default"}})}}function Te(e,t){e.locale=t.locale||e.locale,e.fallbackLocale=t.fallbackLocale||e.fallbackLocale,e.missing=t.missing||e.missing,e.silentTranslationWarn=t.silentTranslationWarn||e.silentFallbackWarn,e.silentFallbackWarn=t.silentFallbackWarn||e.silentFallbackWarn,e.formatFallbackMessages=t.formatFallbackMessages||e.formatFallbackMessages,e.postTranslation=t.postTranslation||e.postTranslation,e.warnHtmlInMessage=t.warnHtmlInMessage||e.warnHtmlInMessage,e.escapeParameterHtml=t.escapeParameterHtml||e.escapeParameterHtml,e.sync=t.sync||e.sync,e.__composer[ae](t.pluralizationRules||e.pluralizationRules);const a=se(e.locale,{messages:t.messages,__i18n:t.__i18n});return Object.keys(a).forEach((t=>e.mergeLocaleMessage(t,a[t]))),t.datetimeFormats&&Object.keys(t.datetimeFormats).forEach((a=>e.mergeDateTimeFormat(a,t.datetimeFormats[a]))),t.numberFormats&&Object.keys(t.numberFormats).forEach((a=>e.mergeNumberFormat(a,t.numberFormats[a]))),e}function De(t={}){const a=__VUE_I18N_LEGACY_API__&&s(t.legacy)?t.legacy:__VUE_I18N_LEGACY_API__,n=!!t.globalInjection,l=new Map,o=__VUE_I18N_LEGACY_API__&&a?ue(t):ce(t),r=e(""),c={get mode(){return __VUE_I18N_LEGACY_API__&&a?"legacy":"composition"},async install(e,...t){if(__VUE_I18N_PROD_DEVTOOLS__&&(e.__VUE_I18N__=c),e.__VUE_I18N_SYMBOL__=r,e.provide(e.__VUE_I18N_SYMBOL__,c),!a&&n&&function(e,t){const a=Object.create(null);Se.forEach((e=>{const n=Object.getOwnPropertyDescriptor(t,e);if(!n)throw K(22);const l=Y(n.value)?{get:()=>n.value.value,set(e){n.value.value=e}}:{get:()=>n.get&&n.get()};Object.defineProperty(a,e,l)})),e.config.globalProperties.$i18n=a,Pe.forEach((a=>{const n=Object.getOwnPropertyDescriptor(t,a);if(!n||!n.value)throw K(22);Object.defineProperty(e.config.globalProperties,`$${a}`,n)}))}(e,c.global),__VUE_I18N_FULL_INSTALL__&&function(e,t,...a){const n=f(a[0])?a[0]:{},l=!!n.useI18nComponentName;(!s(n.globalInstall)||n.globalInstall)&&(e.component(l?"i18n":me.name,me),e.component(ge.name,ge),e.component(de.name,de)),e.directive("t",ve(t))}(e,c,...t),__VUE_I18N_LEGACY_API__&&a&&e.mixin(function(e,t,a){return{beforeCreate(){const n=j();if(!n)throw K(22);const l=this.$options;if(l.i18n){const a=l.i18n;l.__i18n&&(a.__i18n=l.__i18n),a.__root=t,this===this.$root?this.$i18n=Te(e,a):(a.__injectWithOption=!0,this.$i18n=ue(a))}else l.__i18n?this===this.$root?this.$i18n=Te(e,l):this.$i18n=ue({__i18n:l.__i18n,__injectWithOption:!0,__root:t}):this.$i18n=e;e.__onComponentInstanceCreated(this.$i18n),a.__setInstance(n,this.$i18n),this.$t=(...e)=>this.$i18n.t(...e),this.$rt=(...e)=>this.$i18n.rt(...e),this.$tc=(...e)=>this.$i18n.tc(...e),this.$te=(e,t)=>this.$i18n.te(e,t),this.$d=(...e)=>this.$i18n.d(...e),this.$n=(...e)=>this.$i18n.n(...e),this.$tm=e=>this.$i18n.tm(e)},mounted(){if(__VUE_I18N_PROD_DEVTOOLS__){this.$el.__VUE_I18N__=this.$i18n.__composer;const e=this.__v_emitter=i(),t=this.$i18n;t.__enableEmitter&&t.__enableEmitter(e),e.on("*",Ne)}},beforeUnmount(){const e=j();if(!e)throw K(22);if(__VUE_I18N_PROD_DEVTOOLS__){this.__v_emitter&&(this.__v_emitter.off("*",Ne),delete this.__v_emitter);const e=this.$i18n;e.__disableEmitter&&e.__disableEmitter(),delete this.$el.__VUE_I18N__}delete this.$t,delete this.$rt,delete this.$tc,delete this.$te,delete this.$d,delete this.$n,delete this.$tm,a.__deleteInstance(e),delete this.$i18n}}}(o,o.__composer,c)),__VUE_I18N_PROD_DEVTOOLS__){if(!(await ye(e,c)))throw K(21);const t=i();if(a){const e=o;e.__enableEmitter&&e.__enableEmitter(t)}else{const e=o;e[ee]&&e[ee](t)}t.on("*",Ne)}},get global(){return o},__instances:l,__getInstance:e=>l.get(e)||null,__setInstance(e,t){l.set(e,t)},__deleteInstance(e){l.delete(e)}};return c}function Me(e={}){const a=j();if(null==a)throw K(16);if(!a.appContext.app.__VUE_I18N_SYMBOL__)throw K(17);const n=H(a.appContext.app.__VUE_I18N_SYMBOL__);if(!n)throw K(22);const o="composition"===n.mode?n.global:n.global.__composer,s=c(e)?"__i18n"in a.type?"local":"global":e.useScope?e.useScope:"local";if("global"===s){let t=l(e.messages)?e.messages:{};"__i18nGlobal"in a.type&&(t=se(o.locale.value,{messages:t,__i18n:a.type.__i18nGlobal}));const n=Object.keys(t);if(n.length&&n.forEach((e=>{o.mergeLocaleMessage(e,t[e])})),l(e.datetimeFormats)){const t=Object.keys(e.datetimeFormats);t.length&&t.forEach((t=>{o.mergeDateTimeFormat(t,e.datetimeFormats[t])}))}if(l(e.numberFormats)){const t=Object.keys(e.numberFormats);t.length&&t.forEach((t=>{o.mergeNumberFormat(t,e.numberFormats[t])}))}return o}if("parent"===s){let t=function(e,t,a=!1){let n=null;const l=t.root;let o=t.parent;for(;null!=o;){const t=e;if("composition"===e.mode)n=t.__getInstance(o);else{const e=t.__getInstance(o);null!=e&&(n=e.__composer),a&&n&&!n[ne]&&(n=null)}if(null!=n)break;if(l===o)break;o=o.parent}return n}(n,a,e.__useComponent);return null==t&&(t=o),t}if("legacy"===n.mode)throw K(18);const r=n;let u=r.__getInstance(a);if(null==u){const n=a.type,l=t({},e);n.__i18n&&(l.__i18n=n.__i18n),o&&(l.__root=o),u=ce(l),function(e,t,a){let n=null;C((()=>{if(__VUE_I18N_PROD_DEVTOOLS__&&t.vnode.el){t.vnode.el.__VUE_I18N__=a,n=i();const e=a;e[ee]&&e[ee](n),n.on("*",Ne)}}),t),A((()=>{if(__VUE_I18N_PROD_DEVTOOLS__&&t.vnode.el&&t.vnode.el.__VUE_I18N__){n&&n.off("*",Ne);const e=a;e[te]&&e[te](),delete t.vnode.el.__VUE_I18N__}e.__deleteInstance(t)}),t)}(r,a,u),r.__setInstance(a,u)}return u}const Se=["locale","fallbackLocale","availableLocales"],Pe=["t","rt","d","n","tm"];if(u(I),"boolean"!=typeof __VUE_I18N_FULL_INSTALL__&&(_().__VUE_I18N_FULL_INSTALL__=!0),"boolean"!=typeof __VUE_I18N_LEGACY_API__&&(_().__VUE_I18N_LEGACY_API__=!0),"boolean"!=typeof __VUE_I18N_PROD_DEVTOOLS__&&(_().__VUE_I18N_PROD_DEVTOOLS__=!1),"boolean"!=typeof __INTLIFY_PROD_DEVTOOLS__&&(_().__INTLIFY_PROD_DEVTOOLS__=!1),__INTLIFY_PROD_DEVTOOLS__){const e=_();e.__INTLIFY__=!0,m(e.__INTLIFY_DEVTOOLS_GLOBAL_HOOK__)}var Ve=Object.freeze(Object.defineProperty({__proto__:null,DatetimeFormat:de,NumberFormat:ge,Translation:me,VERSION:"9.1.9",createI18n:De,useI18n:Me,vTDirective:ve},Symbol.toStringTag,{value:"Module"}));export{De as c,Me as u,Ve as v};