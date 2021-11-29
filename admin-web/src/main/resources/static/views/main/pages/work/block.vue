<template>
  <div class="block">
    <div class="header">
      <span class="num">{{ data.children.length }}</span>
      <div>{{ data.name }}</div>
    </div>
    <div class="list" ref="dom">
      <Item v-for="item in data.children" :key="item.id" :data="item" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue'
import Item from './item.vue'
import Sortable from 'sortablejs'

export default defineComponent({
  components: {
    Item
  },
  props: {
    data: {
      type: Object,
      default: () => {
        return {
          name: '',
          children: []
        }
      }
    }
  },
  setup(props) {
    const dom = ref(null)
    onMounted(() => {
      dom.value.list = props.data
      new Sortable.default(dom.value, {
        group: 'shared',
        animation: 150,
        ghostClass: 'blue-background-class',
        onEnd: function(evt) {
          const pullMode = evt.pullMode
          const oldIndex = evt.oldIndex
          const newIndex = evt.newIndex
          
          let oldList = evt.target.list.children
          let newList = evt.to.list.children
          if (pullMode) { // 移动至toList并去除旧数据
            newList.splice(newIndex, 0, oldList[oldIndex])
            oldList.splice(oldIndex, 1)
          } else { // 同List位置修改
            const tem = oldList[oldIndex]
            oldList[oldIndex] = oldList[newIndex]
            oldList[newIndex] = tem
            console.log(oldList[0])
          }
        }
      })
    })
    return {
      dom
    }
  }
})
</script>

<style scoped>
.block {
  width: 300px;
  height: calc(100% - 10px);
  background: #efefef;
  border: 1px solid #e3e3e3;
  border-radius: 4px;
  display: inline-flex;
  flex-direction: column;
}
.block .header {
  display: flex;
  align-items: center;
  height: 50px;
  padding-left: 15px;
}
.block .header .num {
  display: inline-block;
  margin-right: 4px;
  color: #fff;
  font-size: 12px;
  min-width: 20px;
  line-height: 20px;
  font-weight: 400;
  text-align: center;
  background-color: #999;
  border-radius: 50%;
}
.block .header div {
  color: #000;
  margin-left: 5px;
}
.block .list {
  padding: 0 0 0 10px;
  overflow-y: auto;
  flex: 1;
  height: auto;
  width: calc(100% - 10px);
}
</style>