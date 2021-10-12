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
          <el-button @click="handleBid(scope.row)" size="small" type="primary" plain>投标</el-button>
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
        <el-form-item label="订单ID">
          <el-input v-model="form.orderId" disabled></el-input>
        </el-form-item>
        <el-form-item label="投标价格">
          <el-input v-model="form.bidPrice"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="bid">确 定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "FadminOrder",
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
    load() {
      // request.get("/factory/"+this.uId).then(res=>{
      //   console.log(res.data)
      //   this.facId = res.data
      // })
      request.get("/order/bid", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleBid(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    bid(){
      // this.form.factoryId = 1
      this.form.bidStatus = 0
      request.post("/bid",this.form,{
        params:{
          uId: this.uId
        }
      }).then(res=>{
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "投标成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
        this.load() // 刷新表格的数据
        this.dialogVisible = false  // 关闭弹窗
      }
    ,
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