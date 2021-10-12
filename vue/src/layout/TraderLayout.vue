<template>
  <div>
    <!--  头部-->
    <Header />
    <!--  主体-->
    <!--  侧边栏-->
    <div style="display: flex">
      <TraderAside />
      <!--      内容-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import TraderAside from "@/components/TraderAside";

export default {
  name: "TraderLayout",
  components: {
    Header,
    TraderAside
  }
}
</script>

<style scoped>

</style>