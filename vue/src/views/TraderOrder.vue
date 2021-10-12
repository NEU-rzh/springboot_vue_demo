<template>
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" @click="add">新建</el-button>
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
          <el-button @click="show(scope.row)" size="small" type="primary" plain v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='发布'">投标详情</el-button>
          <el-button @click="handleEdit(scope.row)" size="small" type="primary" plain v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='已保存'">修改</el-button>
          <el-popconfirm
              title="确定删除吗？" @confirm="handleDelete(scope.row.orderId)"  v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='已保存'"
          >
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
          <el-button @click="publish(scope.row)" size="small" type="primary" plain v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='已保存'">发布</el-button>
          <el-popconfirm
              title="确定收货吗？" @confirm="ensure(scope.row)"  v-if="JSON.parse(JSON.stringify(scope.row)).orderState=='已发货'"
          >
            <template #reference>
              <el-button type="danger" size="small">确认收货</el-button>
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

<!--    新建订单-->
    <el-dialog
        title="新建"
        v-model="newOrder"
        width="30%">
      <el-form :model="form" label-width="120px">
          <el-form-item label="产品名称">
            <el-input v-model="form.productName"></el-input>
          </el-form-item>
          <el-form-item label="数量">
            <el-input v-model="form.quantity"></el-input>
          </el-form-item>
  <!--        <el-form-item la  bel="交付日期">-->
  <!--          <el-input v-model="form.deliveryTime"></el-input>-->
  <!--        </el-form-item>-->
          交付日期：<el-date-picker  v-model="form.deliveryTime" type="datetime" placeholder="选择时间" ></el-date-picker>
          <el-form-item label="收货人信息ID">
            <el-input v-model="form.receiverId"></el-input>
          </el-form-item>
  <!--        <el-form-item label="投标截止日期">-->
  <!--          <el-input v-model="form.bidDeadline"></el-input>-->
  <!--        </el-form-item>-->
          投标截止日期：<el-date-picker  v-model="form.bidDeadline" type="datetime" placeholder="选择时间" ></el-date-picker>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="newOrder = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>

    <!--    输入信息对话框-->
    <el-dialog
        title="投标"
        v-model="dialogVisible"
        width="40%">
      <el-table
          :data="bidData"
          stripe
          style="width: 100%">
        <el-table-column
            prop="bidId"
            label="投标ID"
            sortable>
        </el-table-column>
        <el-table-column
            prop="factoryId"
            label="投标工厂Id">
        </el-table-column>
        <el-table-column
            prop="orderId"
            label="订单Id">
        </el-table-column>
        <el-table-column
            prop="factoryName"
            label="投标工厂">
        </el-table-column>
        <el-table-column
            prop="uname"
            label="工厂负责人">
        </el-table-column>
        <el-table-column
            prop="tel"
            label="联系方式">
        </el-table-column>
        <el-table-column
            prop="bidPrice"
            label="投标价格">
        </el-table-column>
        <el-table-column
            prop="bidStatus"
            label="投标状态">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作">
          <template #default="scope">
            <el-popconfirm
                title="确定选标吗？" @confirm="handleBid(scope.row)"
            >
              <template #reference>
                <el-button size="small" type="success">选标</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin: 10px 0">
        <el-pagination
            @size-change="handleSizeChangeBid"
            @current-change="handleCurrentChangeBid"
            :current-page="currentPageBid"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSizeBid"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalBid">
        </el-pagination>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "TraderOrder",
  components: {},
  data() {
    return {
      form: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      currentPageBid:1,
      pageSizeBid: 10,
      totalBid: 0,
      dialogVisible: false,
      newOrder: false,
      tableData: [],
      bidData: [],
      uId: JSON.parse(sessionStorage.getItem("user")).userId,
      uName: JSON.parse(sessionStorage.getItem("user")).uname,
      oId: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    add(){
      this.newOrder = true;
      // 清空表单域
      this.form={};
    },
    load() {
      request.get("/order/trader", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          uName: this.uName
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save(){
      if (this.form.orderId){
        request.put("/order", this.form).then(res => {
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
          this.newOrder = false  // 关闭弹窗
        })
      }else {
        this.form.orderState="已保存"
        request.post("/order", this.form).then(res => {
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
          this.newOrder = false  // 关闭弹窗
        })
      }
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.newOrder = true
    },
    publish(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.orderState = "发布"
      request.put("/order",this.form).then(res=>
      {
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
    ensure(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.form.orderState = "已收货"
      request.put("/order",this.form).then(res=>
      {
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
    handleDelete(id){
      request.delete("/order/"+id).then(res=>{
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
    show(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
      request.get("/bid",{
        params:{
          pageNum: this.currentPageBid,
          pageSize: this.pageSizeBid,
          oId: this.form.orderId
        }
      }).then(res=>{
        console.log(res)
        this.bidData = res.data.records
        this.total = res.data.totalBid
      })
    },
    handleBid(row){
      this.form.bid = JSON.parse(JSON.stringify(row)).bid
      this.form.factoryId = JSON.parse(JSON.stringify(row)).factoryId
      this.form.bidPrice = JSON.parse(JSON.stringify(row)).bidPrice
      request.put("/bid/choosebid",this.form).then(res => {
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
      })
      request.put("/order/choosebid",this.form).then(res => {
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
      })
      this.dialogVisible = false
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum
      this.load()
    },
    handleSizeChangeBid(pageSize){
      this.pageSizeBid = pageSize
      this.show()
    },
    handleCurrentChangeBid(pageNum){
      this.currentPageBid = pageNum
      this.show()
    }
  }
}
</script>

<style scoped>

</style>