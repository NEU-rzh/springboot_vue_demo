<template>
  <div style="height: 70px; border-bottom: 1px solid #ccc; display: flex; align-items: center" >
    <div style="width: 250px; padding-left: 30px; font-weight: bold; color: cornflowerblue;font-size: 25px">东软智能制造平台</div>
    <div style="flex: 1"></div>
    <el-dropdown style="padding-right: 20px">
      <span class="el-dropdown-link" style="font-size: 15px">
        {{ user.uname }}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="$router.push('/personinfo')">个人信息</el-dropdown-item>
          <el-dropdown-item @click="$router.push('/login')">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return {
      user:{}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  }
}
</script>

<style scoped>

</style>