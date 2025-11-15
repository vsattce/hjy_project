const { defineConfig } = require('@vue/cli-service')

const isProduction = process.env.NODE_ENV === 'production'

module.exports = defineConfig({
  transpileDependencies: true,
  
  // 开发服务器配置
  devServer: {
    port: 8081,
    open: true,
    proxy: {
      '/api-proxy': {
        target: 'http://localhost:8090',
        changeOrigin: true,
        pathRewrite: {
          '^/api-proxy': ''
        }
      }
    }
  },
  
  // 生产环境配置
  productionSourceMap: false, // 生产环境不生成 source map
  
  // 公共路径
  publicPath: isProduction ? '/dist/' : '/',
  
  // 输出目录
  outputDir: 'dist',
  
  // 静态资源目录
  assetsDir: 'static',
  
  // 构建优化
  configureWebpack: {
    optimization: isProduction ? {
      splitChunks: {
        chunks: 'all',
        cacheGroups: {
          libs: {
            name: 'chunk-libs',
            test: /[\\/]node_modules[\\/]/,
            priority: 10,
            chunks: 'initial'
          },
          commons: {
            name: 'chunk-commons',
            test: /[\\/]src[\\/]/,
            minChunks: 2,
            priority: 5,
            reuseExistingChunk: true
          }
        }
      }
    } : {}
  }
})
