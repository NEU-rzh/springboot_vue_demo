<template>
  <div style="padding: 10px">
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
          prop="factoryId"
          label="ID"
          sortable>
      </el-table-column>
      <el-table-column
          prop="factoryName"
          label="工厂名称">
      </el-table-column>
      <el-table-column
          prop="factoryIntroduction"
          label="工厂简介">
      </el-table-column>
      <el-table-column
          prop="tel"
          label="联系人电话">
      </el-table-column>
      <el-table-column
          prop="usercode"
          label="登陆账号">
      </el-table-column>
      <el-table-column
          prop="loginState"
          label="登陆状态">
      </el-table-column>
      <el-table-column
          prop="type"
          label="种类">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" size="small" type="primary" plain>开启/关闭</el-button>
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

  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "AdminFactory",
  components: {},
  data() {
    return {
      form: {},
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get("/factory", {
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
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      request.put("/factory",this.form).then(res => {
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