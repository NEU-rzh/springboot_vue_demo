<template>
  <div style="padding: 10px">
<!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">注册</el-button>
    </div>
<!--    搜索区域-->
    <div>
      <el-input v-model="search" placeholder="请输入关键字" clearable style="width: 20%"></el-input>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <el-table
        :data="tableData"
        stripe
        style="width: 100%">
      <el-table-column
          prop="userId"
          label="ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="usercode"
          label="账号">
      </el-table-column>
      <el-table-column
          prop="pword"
          label="密码">
      </el-table-column>
      <el-table-column
          prop="uname"
          label="姓名">
      </el-table-column>
      <el-table-column
          prop="tel"
          label="电话">
      </el-table-column>
      <el-table-column
          prop="urole"
          label="角色">
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="注册时间">
      </el-table-column>
      <el-table-column
          prop="updateTime"
          label="更新时间">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" size="small">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？" @confirm="handleDelete(scope.row.userId)" v-if="scope.row.urole!='sup_admin'"
          >
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

<!--    输入信息对话框-->
    <el-dialog
      title="提示"
      v-model="dialogVisible"
      width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="账号">
          <el-input v-model="form.usercode"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.pword"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.uname"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.tel"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-radio-group v-model="form.urole">
            <el-radio label="fac_admin"></el-radio>
            <el-radio label="trader"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="工厂名称(管理员填)" v-show="form.urole==='fac_admin'">
          <el-input v-model="form.factoryName"></el-input>
        </el-form-item>
        <el-form-item label="工厂简介(管理员填)" v-show="form.urole==='fac_admin'">
          <el-input type="textarea" v-model="form.factoryIntroduction"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>


import request from "../../utils/request";

export default {
  name: 'AdminUser',
  components: {

  },
  data(){
    return{
      form: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      dialogVisible: false,
      tableData:[],
      total: 0
    }
  },
  created(){
    this.load()
  },
  methods:{
    add(){
      this.dialogVisible = true;
      // 清空表单域
      this.form={};
    },
    load(){
      request.get("/user",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save(){
      if (this.form.userId){
        request.put("/user", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }else {
        request.post("/user", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
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
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() // 刷新表格的数据
          this.dialogVisible = false  // 关闭弹窗
        })
      }
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id)
    {
      request.delete("/user/"+id).then(res=>{
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      this.load()
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    }


  }
}
</script>
