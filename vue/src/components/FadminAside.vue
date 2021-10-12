<template>
  <el-menu
      style="width: 200px"
      class="el-menu-vertical-demo"
      router>
    <el-submenu index="1">
      <template #title>我的工厂</template>
      <el-menu-item index="fadminequipment">我的设备</el-menu-item>
      <el-menu-item index="fadmincapacity">配置产能</el-menu-item>
    </el-submenu>
    <el-submenu index="2">
      <template #title>订单管理</template>
      <el-menu-item index="fadminorder">订单接单</el-menu-item>
      <el-menu-item index="fadminscheduling">订单排产</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "FadminAside"
}
</script>

<style scoped>

</style>