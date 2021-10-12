<template>
  <div style="width: 100%; height: 100vh; background-color: beige; overflow: hidden">
    <div style="width: 400px; margin: 200px auto">
      <div style="text-align: center; margin-bottom: 50px; font-size: 40px">东软智能制造平台</div>
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="账号" prop="usercode" size="normal" >
          <el-input type="text" v-model="form.usercode" placeholder="Usercode"/>
        </el-form-item>
        <el-form-item label="密码" prop="pword" size="normal">
          <el-input type="password" v-model="form.pword" placeholder="Password" />
        </el-form-item>
      </el-form>
      <el-button style="width: 100%; margin-top: 5px;" size="20px" type="primary"  @click="login">登 录</el-button>
      <div>
        <el-link :underline="false" href="/register">没有账号？点击注册</el-link>
      </div>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "Login",
  data() {
    return {
      form: {},
      rules: {
        usercode: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        pword: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    create(){
      sessionStorage.removeItem("user")
    },
    login(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登陆成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))// 缓存用户信息
              let obj = JSON.parse(sessionStorage.getItem("user"))
              if(obj.urole==="sup_admin") this.$router.push("/adminlayout")//页面跳转
              else if(obj.urole==="fac_admin") this.$router.push("/fadminlayout")//页面跳转
              else if(obj.urole==="trader") this.$router.push("/traderlayout")
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
    })
  }
}
}
</script>

<style scoped>

</style>