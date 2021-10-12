<template>
  <div style="width: 100%; height: 100vh; background-color: beige; overflow: hidden">
    <div style="width: 400px; margin: 100px auto">
      <div style="text-align: center; margin-bottom: 50px; font-size: 40px">注册账号</div>
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="账号" prop="usercode">
          <el-input v-model="form.usercode"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pword">
          <el-input v-model="form.pword"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="uname">
          <el-input v-model="form.uname"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="urole">
          <el-radio-group v-model="form.urole">
            <el-radio label="fac_admin"></el-radio>
            <el-radio label="trader"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="工厂名称(管理员填)" v-show="form.urole==='fac_admin'">
          <el-input v-model="form.factoryName" > </el-input>
        </el-form-item>
        <el-form-item label="工厂简介(管理员填)" v-show="form.urole==='fac_admin'">
          <el-input type="textarea" v-model="form.factoryIntroduction"></el-input>
        </el-form-item>
      </el-form>
      <el-button style="width: 100%; margin-top: 5px;" size="20px" type="primary"  @click="register">注册</el-button>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "Register",
  data() {
    return {
      form: {},
      rules: {
        usercode: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        pword: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        uname: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        tel: [
          {required: true, message: '请输入电话', trigger: 'blur'}
        ],
        urole: [
          {required: true, message: '请选择角色', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/register",this.form).then(res =>{
            if (res.code === '0'){
              this.$message({
                type: "success",
                message: "注册成功"
              })
              if (this.form.urole==="fac_admin")
              {
                this.form.personInChargeId=res.data.userId
                this.form.loginState=0
                this.form.type="fac"
                request.post("/factory",this.form).then(res =>{
                  if (res.code === '0') {
                    this.$message({
                      type: "success",
                      message: "注册成功"
                    })
                  }else{
                    this.$message({
                      type: "error",
                      message: res.msg
                    })
                  }
                })
              }
              this.$router.push("/") //页面跳转
            }else{
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>