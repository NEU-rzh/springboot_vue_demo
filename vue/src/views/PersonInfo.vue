<template>
  <el-card class="box-card" style="margin-top: 100px; ">
    <template #header>
      <div class="card-header">
        <span>个人信息</span>
      </div>
    </template>
    <el-form ref="form" :model="form" label-width="100px" style="width: 40%">
      <el-form-item label="账号">
        <el-input v-model="form.usercode" disabled></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.pword" ></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="form.uname"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.tel"></el-input>
      </el-form-item>
      <el-form-item label="角色">
        <el-input v-model="form.urole" disabled></el-input>
      </el-form-item>
      <div style="margin-left: 55px">
        <el-button type="primary" @click="update" style="width: 15%">更新</el-button>
      </div>
    </el-form>
  </el-card>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "PersonInfo",
  data() {
    return {
      form: {}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form = JSON.parse(str)
  },
  methods:{
    update() {
      request.put("/user", this.form).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>