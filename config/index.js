// Webpack Dev Server 설정
module.exports = {
  host: '192.168.143.87',
  port: 3333,
  proxy: {
    '^/app': {
      target: 'http://192.168.143.87:8080',
      changeOrigin: true,
      pathRewrite: {
        '^/app': 'http://192.168.143.87:8080',
      },
    },
  },
};

