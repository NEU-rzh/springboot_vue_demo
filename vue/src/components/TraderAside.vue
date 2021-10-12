<template>
  <el-menu
      style="width: 200px"
      class="el-menu-vertical-demo"
      router>
    <el-submenu index="1">
        <template #title>订单管理</template>
        <el-menu-item index="traderorder">我的订单</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "TraderAside"
}
</script>

<style scoped>

</style>