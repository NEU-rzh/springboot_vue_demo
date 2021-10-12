<template>
  <div>
    <!--  头部-->
    <Header />
    <!--  主体-->
    <!--  侧边栏-->
    <div style="display: flex">
      <FadminAside />
      <!--      内容-->
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import FadminAside from "@/components/FadminAside";

export default {
  name: "FadminLayout",
  components: {
    Header,
    FadminAside
  }
}
</script>

<style scoped>

</style>