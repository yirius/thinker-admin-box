const y={mounted:(e,i)=>{c(e,i,!1);let _,t,o={down:{x:0,y:0},move:{x:0,y:0}},s=!1;e.__position__={x:0,y:0};let u="text";function m(n){n.button===0&&(o.down={x:n.clientX,y:n.clientY},s=!0,_=e.__parentDom__.getBoundingClientRect(),t=e.getBoundingClientRect(),u=document.querySelector("body").style.userSelect,document.querySelector("body").style.userSelect="none")}function d(n){!s||(o.move={x:n.clientX,y:n.clientY},a())}function p(n){s&&(s=!1,document.querySelector("body").style.userSelect=u)}function a(){const n=o.move.x+t.x-_.x-o.down.x,r=o.move.y+t.y-_.y-o.down.y;n<0?e.__position__.x=0:n>_.width-t.width?e.__position__.x=_.width-t.width:e.__position__.x=n,r<0?e.__position__.y=0:r>_.height-t.height?e.__position__.y=_.height-t.height:e.__position__.y=r,e.style.cssText+=`
        position: absolute;
        z-index: 100;
        left: ${e.__position__.x}px;
        top: ${e.__position__.y}px;
      `}e.__mouseDown__=m,e.__mouseMove__=d,e.__mouseUp__=p,e.addEventListener("mousedown",e.__mouseDown__),document.addEventListener("mousemove",e.__mouseMove__),document.addEventListener("mouseup",e.__mouseUp__)},updated(e,i){c(e,i,!0)},beforeUnmount(e){document.removeEventListener("mousedown",e.__mouseDown__),document.removeEventListener("mousemove",e.__mouseMove__),document.removeEventListener("mouseup",e.__mouseUp__)}};function c(e,i,_){const t=[{name:"father",dom:e.parentElement}];let o;if(i.value){const m=t.find(d=>d.name===i.value);m&&m.dom?o=m.dom:o=document.querySelector(i.value)||t[0].dom||t[1].dom}else o=t[0].dom||t[1].dom;const s=o.getBoundingClientRect(),u=e.getBoundingClientRect();e.__parentDom__&&(e.__parentDom__.style.position="static"),e.__parentDom__=o,e.__parentDom__.style.position="relative",_&&(e.__position__={x:u.x-s.x,y:u.y-s.y},e.style.cssText+=`
      position: absolute;
      z-index: 100;
      left: ${e.__position__.x}px;
      top: ${e.__position__.y}px;
    `)}export{y as default};
