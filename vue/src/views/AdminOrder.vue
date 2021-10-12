<template>
  <div style="padding: 10px">
<!--    &lt;!&ndash;    功能区域&ndash;&gt;-->
<!--    <div style="margin: 10px">-->
<!--      <el-button type="primary" @click="add">新建</el-button>-->
<!--    </div>-->
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
          label="数量">
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
<!--      <el-table-column-->
<!--          fixed="right"-->
<!--          label="操作">-->
<!--        <template #default="scope">-->
<!--          <el-button @click="handleEdit(scope.row)" size="small" >编辑</el-button>-->
<!--          <el-popconfirm-->
<!--              title="确定删除吗？" @confirm="handleDelete(scope.row.orderId)"-->
<!--          >-->
<!--            <template #reference>-->
<!--              <el-button type="danger" size="small">删除</el-button>-->
<!--            </template>-->
<!--          </el-popconfirm>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
<!--    <el-dialog-->
<!--        title="提示"-->
<!--        v-model="dialogVisible"-->
<!--        width="40%">-->
<!--      <el-form :model="form" label-width="120px">-->
<!--        <el-form-item label="产品名称">-->
<!--          <el-input v-model="form.productName"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="数量">-->
<!--          <el-input v-model="form.quantity"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="交付日期">-->
<!--          <el-input v-model="form.deliveryTime"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="收货人信息ID">-->
<!--          <el-input v-model="form.receiverId"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="投标截止日期">-->
<!--          <el-input v-model="form.bidDeadline"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="订单状态">-->
<!--          <el-radio-group v-model="form.orderState">-->
<!--            <el-radio label="发布"></el-radio>-->
<!--            <el-radio label="投标结束"></el-radio>-->
<!--            <el-radio label="已排产"></el-radio>-->
<!--            <el-radio label="完工"></el-radio>-->
<!--            <el-radio label="已发货"></el-radio>-->
<!--            <el-radio label="已收货"></el-radio>-->
<!--      </el-radio-group>-->
<!--      </el-form-item>-->
<!--      </el-form>-->
<!--      <template #footer>-->
<!--      <span class="dialog-footer">-->
<!--        <el-button @click="dialogVisible = false">取 消</el-button>-->
<!--        <el-button type="primary" @click="save">确 定</el-button>-->
<!--      </span>-->
<!--      </template>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "AdminOrder",
  components: {},
  data() {
    return {
      form: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      tableData: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    // add() {
    //   this.dialogVisible = true;
    //   // 清空表单域
    //   this.form = {};
    // },
    load() {
      request.get("/order", {
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
    // save() {
    //   if (this.form.orderId){
    //     request.put("/order", this.form).then(res => {
    //       console.log(res)
    //       if (res.code === '0') {
    //         this.$message({
    //           type: "success",
    //           message: "更新成功"
    //         })
    //       } else {
    //         this.$message({
    //           type: "error",
    //           message: res.msg
    //         })
    //       }
    //       this.load() // 刷新表格的数据
    //       this.dialogVisible = false  // 关闭弹窗
    //     })
    //   }else {
    //     request.post("/order", this.form).then(res => {
    //       console.log(res)
    //       if (res.code === '0') {
    //         this.$message({
    //           type: "success",
    //           message: "新增成功"
    //         })
    //       } else {
    //         this.$message({
    //           type: "error",
    //           message: res.msg
    //         })
    //       }
    //       this.load() // 刷新表格的数据
    //       this.dialogVisible = false  // 关闭弹窗
    //     })
    //   }
    // },
    // handleEdit(row){
    //   this.form = JSON.parse(JSON.stringify(row))
    //   this.dialogVisible = true
    // },
    // handleDelete(id){
    //   request.delete("/order/"+id).then(res=>{
    //     if (res.code === '0') {
    //       this.$message({
    //         type: "success",
    //         message: "删除成功"
    //       })
    //     } else {
    //       this.$message({
    //         type: "error",
    //         message: res.msg
    //       })
    //     }
    //   })
    //   this.load()
    // },
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