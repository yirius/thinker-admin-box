let o=[];function u(){return window.tinymce}const r=(e,i)=>{const d=document.getElementById(e),t=i||function(){};if(!d){const n=document.createElement("script");n.src=e,n.id=e,document.body.appendChild(n),o.push(t),("onload"in n?c:a)(n)}d&&t&&(u()?t(null,d):o.push(t));function c(n){n.onload=function(){this.onerror=this.onload=null;for(const l of o)l(null,n);o=null},n.onerror=function(){this.onerror=this.onload=null,t(new Error("Failed to load "+e),n)}}function a(n){n.onreadystatechange=function(){if(!(this.readyState!=="complete"&&this.readyState!=="loaded")){this.onreadystatechange=null;for(const l of o)l(null,n);o=null}}}};export{r as default};
