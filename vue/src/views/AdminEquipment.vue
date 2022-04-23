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
          prop="equipmentId"
          label="设备ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="equipmentName"
          label="设备名称">
      </el-table-column>
      <el-table-column
          prop="equipmentType"
          label="设备类别">
      </el-table-column>
      <el-table-column
          prop="equipmentSpecification"
          label="设备规格">
      </el-table-column>
      <el-table-column
          prop="equipmentState"
          label="设备状态">
      </el-table-column>
      <el-table-column
          prop="rentState"
          label="租用状态">
      </el-table-column>
      <el-table-column
          prop="factoryId"
          label="所属工厂ID">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleOpenClose(scope.row)" size="small" type="primary">远程开关机</el-button>
          <el-button @click="handleEdit(scope.row)" size="small">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？" @confirm="handleDelete(scope.row.equipmentId)" v-if="JSON.parse(JSON.stringify(scope.row)).rentState=='未被租用'"
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
        <el-form-item label="设备名称">
          <el-input v-model="form.equipmentName"></el-input>
        </el-form-item>
<!--        <el-form-item label="设备类别">-->
<!--          <el-input v-model="form.equipmentType"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="设备类别">
          <el-select v-model="form.equipmentType" placeholder="请选择类型">
            <el-option v-for="(item ,index) in allEmType" :key="index" :label="item.equipmentType" :value="item.equipmentType"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备规格">
          <el-input v-model="form.equipmentSpecification"></el-input>
        </el-form-item>
        <el-form-item label="设备状态">
          <el-radio-group v-model="form.equipmentState">
            <el-radio label="生产中"></el-radio>
            <el-radio label="闲置中"></el-radio>
            <el-radio label="已关闭"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="租用状态">
          <el-radio-group v-model="form.rentState">
            <el-radio label="工厂设备"></el-radio>
            <el-radio label="已被租用"></el-radio>
            <el-radio label="未被租用"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="所属工厂ID">
          <el-input v-model="form.factoryId"></el-input>
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
  name: "AdminEquipment",
  components: {

  },
  data(){
    return{
      form: {},
      allEmType: {},
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
    this.findType()
  },
  methods:{
    add(){
      this.dialogVisible = true;
      // 清空表单域
      this.form={};
    },
    load(){
      request.get("/equipment",{
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
      if (this.form.equipmentId){
        request.put("/equipment", this.form).then(res => {
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
        request.post("/equipment", this.form).then(res => {
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
    findType(){
      request.get("/typeofequipment",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: ""
        }
      }).then(res=>{
        this.allEmType = res.data.records
        this.total = res.data.total
      })
    },
    handleOpenClose(row){
      this.form = JSON.parse(JSON.stringify(row))
      request.put("/equipment/equipmentopenclose",this.form).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "操作成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load() // 刷新表格的数据
      })
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id)
    {
      request.delete("/equipment/"+id).then(res=>{
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