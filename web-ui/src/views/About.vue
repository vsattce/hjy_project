<template>
  <div class="about">
    <h1>关于页面</h1>
    <p>这是关于页面的内容</p>
    <router-link to="/">返回首页</router-link>
  </div>
</template>

<script>
/**
 * 关于页面组件
 */
export default {
  name: 'About'
}
</script>

<style scoped>
.about {
  padding: 20px;
}
</style>
