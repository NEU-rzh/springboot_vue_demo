<template>
  <el-menu style="width: 200px" class="el-menu-vertical-demo" router>
    <el-submenu index="1" >
      <template #title>系统设置</template>
      <el-menu-item index="adminuser">用户管理</el-menu-item>
    </el-submenu>
    <el-submenu index="2">
      <template #title>云工厂</template>
      <el-menu-item index="adminfactory">云工厂信息</el-menu-item>
    </el-submenu>
    <el-submenu index="3">
      <template #title>产品管理</template>
      <el-menu-item index="adminproducttype">产品类别管理</el-menu-item>
      <el-menu-item index="adminproduct">产品信息管理</el-menu-item>
    </el-submenu>
    <el-submenu index="4">
      <template #title>产能中心</template>
      <el-menu-item index="adminequipmenttype">设备类别管理</el-menu-item>
      <el-menu-item index="adminequipment">设备管理</el-menu-item>
    </el-submenu>
    <el-submenu index="5">
      <template #title>订单管理</template>
      <el-menu-item index="adminorder">订单基本信息</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "AdminAside"
}
</script>

<style scoped>

</style>