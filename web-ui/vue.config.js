/**
 * Vue CLI 配置文件
 * 配置开发服务器、构建选项、代理等
 * 文档：https://cli.vuejs.org/config/
 */
const { defineConfig } = require('@vue/cli-service')

const isProduction = process.env.NODE_ENV === 'production'

module.exports = defineConfig({
  // 需要转译的依赖包
  transpileDependencies: true,
  lintOnSave: false,
  /**
   * 开发服务器配置
   */
  devServer: {
    port: 8081,              // 开发服务器端口
    open: true,              // 启动时自动打开浏览器
    proxy: {
      // API 代理配置，解决跨域问题
      // 前端请求 /api-proxy/xxx 会被代理到 http://localhost:8090/xxx
      '/api-proxy': {
        target: 'http://localhost:8090',  // 后端服务地址
        changeOrigin: true,                // 改变请求源
        pathRewrite: {
          '^/api-proxy': ''                // 重写路径，去掉 /api-proxy 前缀
        }
      }
    }
  },
  
  /**
   * 生产环境配置
   */
  productionSourceMap: false,  // 不生成 source map，减小打包体积，提升构建速度
  
  /**
   * 部署配置
   */
  publicPath: isProduction ? '/dist/' : '/',  // 生产环境资源路径前缀
  outputDir: 'dist',                          // 打包输出目录
  assetsDir: 'static',                        // 静态资源目录（js、css、img、fonts）
  
  /**
   * Webpack 配置
   * 生产环境启用代码分割优化
   */
  configureWebpack: {
    optimization: isProduction ? {
      splitChunks: {
        chunks: 'all',
        cacheGroups: {
          // 第三方库单独打包
          libs: {
            name: 'chunk-libs',
            test: /[\\/]node_modules[\\/]/,
            priority: 10,
            chunks: 'initial'
          },
          // 公共代码提取
          commons: {
            name: 'chunk-commons',
            test: /[\\/]src[\\/]/,
            minChunks: 2,              // 至少被 2 个模块引用
            priority: 5,
            reuseExistingChunk: true   // 复用已存在的 chunk
          }
        }
      }
    } : {}
  }
})
