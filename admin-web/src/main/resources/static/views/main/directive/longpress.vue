<template>
  <div class="layout-container">
    <div class="layout-container-table">
      <el-button v-longpress="setData">长按指令</el-button>
      <p>{{ data }}</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
export default defineComponent({
  setup() {
    let data = ref('')
    function setData() {
      data.value = '执行长按指令'
    }
    return {
      data,
      setData
    }
  }
})
</script>

<style scoped>
  
</style>