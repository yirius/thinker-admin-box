import{_ as a}from"./vue-clipboard3.47a611fa.js";import{i as l}from"./index.4634ff3e.js";import{E as r}from"./element-plus.c13ad818.js";const{t}=l.global,{toClipboard:p}=a();function y(o){return new Promise((e,s)=>{try{p(o),r.success(t("sys.layout.copyTextSuccess")),e(o)}catch(c){r.error(t("sys.layout.copyTextError")),s(c)}})}var m=Object.freeze({__proto__:null,[Symbol.toStringTag]:"Module",copyText:y});export{m as c};
