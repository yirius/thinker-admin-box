import{E as o}from"./element-plus.4e647bc4.js";function i(r){let u="";if(!/^\#?[0-9A-Fa-f]{6}$/.test(r))return o({type:"warning",message:"\u8F93\u5165\u9519\u8BEF\u7684hex"});r=r.replace("#",""),u=r.match(/../g);for(let e=0;e<3;e++)u[e]=parseInt(u[e],16);return u}function l(r,u,g){let e=/^\d{1,3}$/;if(!e.test(r)||!e.test(u)||!e.test(g))return o({type:"warning",message:"\u8F93\u5165\u9519\u8BEF\u7684rgb\u989C\u8272\u503C"});let t=[r.toString(16),u.toString(16),g.toString(16)];for(let n=0;n<3;n++)t[n].length==1&&(t[n]=`0${t[n]}`);return`#${t.join("")}`}function a(r,u){if(!/^\#?[0-9A-Fa-f]{6}$/.test(r))return o({type:"warning",message:"\u8F93\u5165\u9519\u8BEF\u7684hex\u989C\u8272\u503C"});let e=i(r);for(let t=0;t<3;t++)e[t]=Math.floor(e[t]*(1-u));return l(e[0],e[1],e[2])}function f(r,u){if(!/^\#?[0-9A-Fa-f]{6}$/.test(r))return o({type:"warning",message:"\u8F93\u5165\u9519\u8BEF\u7684hex\u989C\u8272\u503C"});let e=i(r);for(let t=0;t<3;t++)e[t]=Math.floor((255-e[t])*u+e[t]);return l(e[0],e[1],e[2])}var h=Object.freeze({__proto__:null,[Symbol.toStringTag]:"Module",hexToRgb:i,rgbToHex:l,getDarkColor:a,getLightColor:f});export{h as t};