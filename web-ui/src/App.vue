<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script setup>
/**
 * 根组件
 * 包含全局导航和路由视图
 */
</script>

<style lang="less">
@primary-color: #409EFF;
@text-color: #2c3e50;

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  width: 100%;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: @text-color;
  height: 100%;
  width: 100%;
}

nav {
  padding: 20px;

  a {
    font-weight: bold;
    color: @text-color;
    text-decoration: none;
    margin: 0 10px;

    &.router-link-exact-active {
      color: @primary-color;
    }
  }
}
</style>
