import{x as h}from"./xlsx.c338de94.js";import{x as T}from"./xlsx.c338de94.js";import"./@kangc.ec979c3e.js";import"./vue.300b83f3.js";import"./@vue.2fc568c1.js";import"./dotenv.7e6d7ced.js";const{utils:a,writeFile:n}=h;function m({data:x,header:t,filename:o,json2sheetOpts:s={},write2excelOpts:e={bookType:"xlsx"}}){const r=[...x];t&&(r.unshift(t),s.skipHeader=!0);const i=a.json_to_sheet(r,s),c={SheetNames:[o],Sheets:{[o]:i}};n(c,o,e)}function w({data:x,header:t,filename:o,write2excelOpts:s={bookType:"xlsx"}}){const e=[...x];t&&e.unshift(t);const r=a.aoa_to_sheet(e),i={SheetNames:[o],Sheets:{[o]:r}};n(i,o,s)}export{w as aoaToSheetXlsx,T as default,m as jsonToSheetXlsx};