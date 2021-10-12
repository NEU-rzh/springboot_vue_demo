<template>
  <div style="padding: 10px">
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <el-table
        :data="tableData"
        stripe
        style="width: 100%">
      <el-table-column
          prop="capId"
          label="产能ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="productId"
          label="产品Id">
      </el-table-column>
      <el-table-column
          prop="productName"
          label="产品名称">
      </el-table-column>
      <el-table-column
          prop="equipmentId"
          label="设备Id">
      </el-table-column>
      <el-table-column
          prop="equipmentName"
          label="设备名称">
      </el-table-column>
      <el-table-column
          prop="capacity"
          label="产能">
      </el-table-column>
      <el-table-column
          prop="factoryId"
          label="工厂Id">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" size="small" type="primary" plain>修改</el-button>
          <el-popconfirm
          title="确定删除吗？" @confirm="handleDelete(scope.row.capId)"
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
        width="60%">
      <el-form :model="form" label-width="120px">
        <div style="display: flex">
          <el-form-item label="产品ID">
            <el-input v-model="form.productId"></el-input>
          </el-form-item>
          <el-form-item label="设备ID">
            <el-input v-model="form.equipmentId"></el-input>
          </el-form-item>
          <el-form-item label="产能">
            <el-input v-model="form.capacity"></el-input>
          </el-form-item>
        </div>
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
  name: "FadminCapacity",
  components: {},
  data() {
    return {
      form: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      tableData: [],
      uId: JSON.parse(sessionStorage.getItem("user")).userId,
      facId: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    add(){
      this.dialogVisible = true;
      // 清空表单域
      this.form={};
    },
    load() {
      request.get("/cap", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          uId: this.uId
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save(){
      if (this.form.capId){
        request.put("/cap", this.form).then(res => {
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
        request.post("/cap", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
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
      }
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id){
      request.delete("/cap/"+id).then(res=>{
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
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>

</style>