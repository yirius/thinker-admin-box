<template>
  <div class="layout-container">
    <div class="layout-container-table">
      点击状态：{{ sideStr }}
      <div class="box" v-clickoutside="setData">
        <span>InSide</span>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref } from 'vue'
export default defineComponent({
  setup() {
    const sideStr = ref('')
    const setData = (type)=> {
     if (type) {
       sideStr.value = 'outSide'
     } else {
       sideStr.value = 'inSide'
     }
    }
    return {
      sideStr,
      setData
    }
  }
})
</script>

<style scoped>
  .box {
    width: 200px;
    height: 200px;
    background: red;
    margin-top: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
    font-size: 40px;
  }
</style>