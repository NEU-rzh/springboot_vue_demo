<template>
  <div>
    <!--  头部-->
    <Header />
    <!--  主体-->
    <!--  侧边栏-->
    <div style="display: flex">
      <AdminAside />
      <!--      内容-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>


<script>
import Header from "@/components/Header";
import AdminAside from "@/components/AdminAside";


export default {
  name: "AdminLayout",
  components: {
    Header,
    AdminAside
  }
}
</script>

<style scoped>

</style>