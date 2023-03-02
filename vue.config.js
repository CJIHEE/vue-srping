const path = require('path');
const devServerOptions = require('./config');

module.exports = {
  lintOnSave: false,
  devServer: {
    ...devServerOptions,
    overlay: false,
  },
  runtimeCompiler: true,
  publicPath: process.env.NODE_ENV === 'production' ? '/production/' : '/',
  outputDir: path.resolve(__dirname, '../service/src/main/webapp/production'),
  indexPath: path.resolve(__dirname, '../service/src/main/webapp/index.html'),
  assetsDir: 'app',
  lintOnSave: process.env.NODE_ENV !== 'production',
  configureWebpack: {
    resolve: {
      extensions: ['.js', '.vue', '.json'],
      alias: {
        '@': path.join(__dirname, 'src'),
      },
    },
  },
  transpileDependencies: [
    'element-ui/packages/tabs/src',
    'element-ui/src/utils',
    'element-ui/src/transitions',
    'element-ui/src/mixins',
    'element-ui/src/locale',
    'element-ui/packages/tree/src',
    'element-ui/packages/checkbox/src',
  ],
  chainWebpack: (config) => {
    config.module
      .rule('images')
      .test(/\.(png|jpe?g|gif|svg|cur)(\?.*)?$/)
      .use('url-loader')
      .loader('url-loader')
      .end();
    config.module
      .rule('file')
      .test(/\.ico$/)
      .use('file-loader')
      .loader('file-loader')
      .options({ name: 'img/[name].[ext]' })
      .end();
    if (process.env.NODE_ENV === 'production') {
      config.optimization.minimizer('terser').tap((args) => {
        args[0].terserOptions.compress.drop_console = true;
        args[0].terserOptions.compress.drop_debugger = true;
        return args;
      });
    }
  },
};
