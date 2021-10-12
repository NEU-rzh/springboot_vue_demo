<template>
  <div style="padding: 10px">
    <el-table
        :data="tableData"
        stripe
        style="width: 100%">
      <el-table-column
          prop="orderId"
          label="订单ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="productName"
          label="产品名称">
      </el-table-column>
      <el-table-column
          prop="quantity"
          label="订购数量">
      </el-table-column>
      <el-table-column
          prop="deliveryTime"
          label="交付时间">
      </el-table-column>
      <el-table-column
          prop="bidDeadline"
          label="投标截止日期">
      </el-table-column>
      <el-table-column
          prop="receiver"
          label="收货人">
      </el-table-column>
      <el-table-column
          prop="receiverTel"
          label="收货人联系方式">
      </el-table-column>
      <el-table-column
          prop="receiverAddress"
          label="收货人地址">
      </el-table-column>
      <el-table-column
          prop="orderState"
          label="订单状态">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleSchedule(scope.row)" size="small" type="primary" plain v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='投标结束'">排产</el-button>
          <el-button @click="handleSchedule1(scope.row)" size="small" type="success" plain v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='已排产'">完工</el-button>
          <el-button @click="handleSchedule2(scope.row)" size="small" type="danger" plain v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='完工'">发货</el-button>
          <el-button @click="handleSchedule3(scope.row)" size="small" type="info" plain v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='已收货'">完成</el-button>
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

    <!--排产弹窗-->
    <el-dialog title="排产" v-model="dialogVisible" width="70%">
      <el-form :model="form" label-width="60px">

        <el-button type="primary" @click="addScheduling(scheduleInfo.odId)">添加生产设备</el-button>

        <el-table
            :data="tableData2"
            stripe
            style="width: 100%;">


          <el-table-column min-width="10">

            <template #default="scope4" >
              设备id：<el-input v-model="scope4.row.emId" style="width: 20%" :disabled="true"></el-input>
              开始时间：<el-input v-model="scope4.row.stime" style="width: 20%" :disabled="true"></el-input>
              结束时间：<el-input v-model="scope4.row.ftime" style="width: 20%" :disabled="true"></el-input>
            </template>

          </el-table-column>

          <el-table-column style="width: 10px" min-width="1">
            <template #default="scope3">
              <!--          删除-->
              <el-popconfirm
                  title="确定删除吗？"
                  @confirm="deleteSchedule(scope3.row)"
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
            <el-button @click="dialogVisible=false ;tableData2=[]">取 消</el-button>
            <el-button type="primary" @click="scheduleAll">确 定</el-button>
          </span>
      </template>
    </el-dialog>


    <!--      添加排产弹窗-->
    <el-dialog title="设备排产" v-model="dialogVisible2" width="60%">
      <el-form :model="form" label-width="120px">

        <div style="display: flex">
          <el-form-item label="设备名称">
          </el-form-item>

          <!--            遍历设备-->
          <el-select v-model="addInfo.emId" placeholder="请选择设备">
            <el-option v-for="(item ,index) in allEm" :key="index" :label="item.equipmentName" :value="item.equipmentId"></el-option>
          </el-select>

<!--          <el-form-item label="开始时间">-->
<!--            <el-input v-model="addInfo.stime" style="width: 80%" ></el-input>-->
<!--          </el-form-item>-->
          开始时间：<el-date-picker  v-model="addInfo.stime" type="datetime" placeholder="选择时间" ></el-date-picker>
<!--          <el-form-item label="结束时间">-->
<!--            <el-input v-model="addInfo.ftime" style="width: 80%" ></el-input>-->
<!--          </el-form-item>-->
          结束时间：<el-date-picker  v-model="addInfo.ftime" type="datetime" placeholder="选择时间" ></el-date-picker>
        </div>

      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2=false">取 消</el-button>
            <el-button type="primary" @click="addSd">确 定</el-button>
          </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "FadminScheduling",
  components: {},
  data() {
    return {
      form: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogVisible2: false,
      //排产信息
      scheduleInfo:{},
      //添加信息
      addInfo:{},
      //所有设备
      allEm:{},
      tableData: [],
      tableData2:[],
      uId: JSON.parse(sessionStorage.getItem("user")).userId
    }
  },
  created() {
    this.load()
    this.findAllEm()
  },
  methods: {
    load() {
      request.get("/order/schedule", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          uId: this.uId
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    loadSd(){
      request.get("/scheduling",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }
      }).then(res=>{
        console.log(res)
        this.tableData2=res.data.records;
      })
    },
    deleteSchedule(row){
      request.delete("/scheduling/"+this.scheduleInfo.odId+"/"+row.emId).then(res=>{
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
      this.loadSd()
    },
    findAllEm(){
      request.get("/equipment/free",{
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          uId: this.uId
        }
      }).then(res=>{
        console.log(res)
        this.allEm=res.data.records;
        this.total=res.data.total;
      })
    },
    scheduleAll(){
      request.put("/scheduling/call").then(res=>{
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      this.dialogVisible = false
      this.loadSd()
      this.load()
    },
    handleSchedule(row){
      this.scheduleInfo.odId=JSON.parse(JSON.stringify(row)).orderId
      this.dialogVisible = true
      this.loadSd()
    },
    handleSchedule1(row){
      this.form = JSON.parse(JSON.stringify(row))
      request.put("/order/schedule1",this.form).then(res=>
      {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "完工成功"
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
    handleSchedule2(row){
      this.form = JSON.parse(JSON.stringify(row))
      request.put("/order/schedule2",this.form).then(res=>
      {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "发货成功"
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
    handleSchedule3(row){
      this.form = JSON.parse(JSON.stringify(row))
      request.put("/order/schedule3",this.form).then(res=>
      {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "完成成功"
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
    addScheduling(id){
      this.addInfo.odId=id;
      console.log(this.addInfo.odId)
      this.dialogVisible2=true;
      this.addInfo = {}
    },
    addSd(){
      request.post("/scheduling",this.addInfo).then(res => {
        // 点击后弹窗
        if(res.code=='0'){
          this.loadSd()
          this.dialogVisible2 = false
          this.$message({
            type:"success",
            message:"新增成功"
          })
        }else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }

      })
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