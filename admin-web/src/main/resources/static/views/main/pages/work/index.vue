<template>
  <div class="layout-container">
    <el-alert type="success">请尝试左右拖动模块，数据从接口而来，任意时刻均可以提交至接口中去</el-alert>
    <div class="layout-container-table">
      <div class="list">
        <Block v-for="(block, key) in list" :key="key" :data="block" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import Block from './block.vue'
import { getData } from '@/api/work'
export default defineComponent({
  components: {
    Block
  },
  setup() {
    let list = ref([])
    const getList = () => {
      let params = {}
      getData(params)
      .then(res => {
        list.value = res.data.list
      })
    }
    getList()
    return {
      list
    }
  }
})
</script>

<style scoped>

</style>