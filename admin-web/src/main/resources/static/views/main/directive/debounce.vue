<template>
  <div class="layout-container">
    <div class="layout-container-table">
      <div class="box">
        <el-button v-debounce="getData(123)" type="primary">防抖按钮</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'

export default defineComponent({
  setup() {
    const getData = (str)=> {
      return function() {
        console.log(str)
        ElMessage({
          type: 'success',
          message: '正在拉取数据'
        })
      }
    }
    onUnmounted(() => {
      ElMessage.closeAll()
    })
    return {
      getData
    }
  }
})
</script>

<style scoped>
  .box {
    display: flex;
    .el-input {
      margin-right: 10px;
    }
  }
</style>