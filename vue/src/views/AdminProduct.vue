<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
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
          prop="productId"
          label="产品ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="productName"
          label="产品名称">
      </el-table-column>
      <el-table-column
          prop="productType"
          label="产品类型">
      </el-table-column>
      <el-table-column
          prop="productSpecification"
          label="产品规格">
      </el-table-column>
      <el-table-column
          prop="productIntroduction"
          label="产品简介">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" size="small">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？" @confirm="handleDelete(scope.row.productId)"
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
        <el-form-item label="产品名称">
          <el-input v-model="form.productName"></el-input>
        </el-form-item>
<!--        <el-form-item label="产品类别">-->
<!--          <el-input v-model="form.productType"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="产品类别">
          <el-select v-model="form.productType" placeholder="请选择类型">
            <el-option v-for="(item ,index) in allPdType" :key="index" :label="item.productType" :value="item.productType"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="产品规格">
          <el-input v-model="form.productSpecification"></el-input>
        </el-form-item>
        <el-form-item label="产品简介">
          <el-input v-model="form.productIntroduction"></el-input>
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
  name: "AdminProduct",
  components: {

  },
  data(){
    return{
      form: {},
      allPdType: {},
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
    this.findAllPdType()
  },
  methods:{
    add(){
      this.dialogVisible = true;
      // 清空表单域
      this.form={};
    },
    load(){
      request.get("/product",{
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
    findAllPdType(){
      request.get("/typeofproduct",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: ""
        }
      }).then(res=>
      {
        this.allPdType = res.data.records
        this.total = res.data.total
      })
    },
    save(){
      if (this.form.productId){
        request.put("/product", this.form).then(res => {
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
        request.post("/product", this.form).then(res => {
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
    handleDelete(id)
    {
      request.delete("/product/"+id).then(res=>{
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

<style scoped>

</style>