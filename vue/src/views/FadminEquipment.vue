<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="success" @click="rent">租用设备</el-button>
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
          <el-button @click="handleOpenClose(scope.row)" size="small" type="primary" plain>远程开关机</el-button>
          <el-button @click="handleEdit(scope.row)" size="small" type="success" plain v-if="JSON.parse(JSON.stringify(scope.row)).rentState=='工厂设备'">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？" @confirm="handleDelete(scope.row.equipmentId)" v-if="JSON.parse(JSON.stringify(scope.row)).rentState=='工厂设备'"
          >
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
          <el-button @click="handleAlloc(scope.row)" size="small" type="info" plain>配置产品</el-button>
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
        title="新增"
        v-model="dialogVisible"
        width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="设备名称">
          <el-input v-model="form.equipmentName"></el-input>
        </el-form-item>
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
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>

<!--    租用设备-->
    <el-dialog
      title="租用设备"
      v-model="rentDialog"
      width="80%">
      <div>
        <el-input v-model="searchRent" placeholder="请输入关键字" clearable style="width: 20%"></el-input>
        <el-button type="primary" style="margin-left: 5px" @click="rent">查询</el-button>
      </div>
      <el-table
          :data="rentData"
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
            <el-button @click="rentEm(scope.row)" size="small" type="primary" plain>租用</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 10px 0">
        <el-pagination
            @size-change="handleSizeChangeRent"
            @current-change="handleCurrentChangeRent"
            :current-page="currentPageRent"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSizeRent"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalRent">
        </el-pagination>
      </div>
    </el-dialog>

<!--    配置产能-->
    <el-dialog title="添加产品产能信息" v-model="capDialog" width="60%">
      <el-form :model="form" >
        <div style="display: flex">
          <el-form-item label="设备编号">
            <el-input v-model="info.equipmentId" style="width: 250px" :disabled="true"></el-input>
          </el-form-item>
          <div style="flex: 1"></div>
          <el-form-item label="设备名称">
            <el-input v-model="info.equipmentName" style="width: 250px" :disabled="true"></el-input>
          </el-form-item>
        </div>
        <el-button type="primary" @click="addCapacity(info.equipmentId)">添加产品</el-button>
        <el-table
            :data="capData"
            stripe
            style="width: 100%;">
          <el-table-column min-width="6">
            <template #default="scope1" >
              产品名称：<el-input v-model="scope1.row.productName" style="width: 40%" :disabled="true"></el-input>
              产能：<el-input v-model="scope1.row.capNum" style="width: 40%" ></el-input>
            </template>
          </el-table-column>
          <el-table-column style="width: 10px" min-width="1">
            <template #default="scope2">
              <el-popconfirm
                  title="确定删除吗？"
                  @confirm="deletepower(scope2.row)"
              >
                <template #reference>
                  <el-button
                      type="danger"
                  >删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="capDialog=false ;capData=[]">取 消</el-button>
            <el-button type="primary" @click="scheduling">确 定</el-button>
          </span>
      </template>
    </el-dialog>


    <el-dialog title="添加产能" v-model="newCap" width="40%">
      <el-form :model="form" label-width="120px">
        <div style="display: flex">
          <el-form-item label="产品id">
            <el-input v-model="capa.pdId" style="width: 80%" ></el-input>
          </el-form-item>
          <div style="flex: 1"></div>
          <el-form-item label="产能">
            <el-input v-model="capa.capNum" style="width: 80%" ></el-input>
          </el-form-item>
        </div>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="newCap=false ">取 消</el-button>
            <el-button type="primary" @click="addCap">确 定</el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "FadminEquipment",
  components: {

  },
  data(){
    return{
      form: {},
      allEmType:{},
      search: '',
      searchRent: '',
      currentPage: 1,
      currentPageRent:1,
      pageSize: 10,
      pageSizeRent: 10,
      dialogVisible: false,
      rentDialog: false,
      capDialog: false,
      newCap: false,
      tableData:[],
      rentData:[],
      capData:[],
      total: 0,
      totalRent: 0,
      info: {},
      capa:{},
      uId: JSON.parse(sessionStorage.getItem("user")).userId,
      facId: 0
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
    // 查找所有可租用设备
    rent(){
      this.rentDialog = true;
      request.get("/equipment/rent",{
        params: {
          pageNumRent: this.currentPageRent,
          pageSizeRent: this.pageSizeRent,
          searchRent: this.searchRent
        }
      }).then(res=>{
        console.log(res)
        this.rentData = res.data.records
        this.totalRent = res.data.totalRent
      })
    },
    rentEm(row){
      this.form = JSON.parse(JSON.stringify(row))
      request.put("/equipment/rentem",this.form,{
        params: {
          facId: this.facId
        }
      }).then(res=> {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "租用成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.rent()// 刷新表格的数据
        this.rentDialog = false
        this.load()
      }
      )},
    findType(){
      request.get("/typeofequipment",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: ""
        }
      }).then(res=>{
        this.allEmType = res.data.records
        this.totol = res.data.total
      })
    },
    load(){
      request.get("/equipment/fequipment",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          uId: this.uId
        }
      }).then(res=>{
        console.log(res)
        console.log(sessionStorage.getItem("user"))
        this.facId = res.data.records[0].factoryId
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    loadCap(){
      request.get("/cap",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          eId: this.info.equipmentId
        }
      }).then(res=>{
        console.log(res)
        this.capData=res.data.records
          }
      )
    },
    addCap(){
      request.post("/cap", this.capa).then(res=>{
        if (res.code === '0') {
          this.loadCap()
          this.newCap = false
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
      })
    },
    addCapacity(id){
      this.capa.emId =id
      console.log(this.capa)
      this.newCap = true
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
        this.form.rentState="工厂设备"
        this.form.factoryId=this.facId
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
    handleAlloc(row){
      this.info = JSON.parse(JSON.stringify(row))
      this.loadCap()
      this.capDialog = true
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
    },
    handleSizeChangeRent(pageSizeRent){
      this.pageSizeRent = pageSizeRent
      this.rent()
    },
    handleCurrentChangeRent(pageNumRent){
      this.currentPageRent = pageNumRent
      this.rent()
    }

  }
}
</script>

<style scoped>

</style>