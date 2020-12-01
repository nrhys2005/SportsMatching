const redis  = require('redis');
const config = require('./config.js').redis.development
const redisClient  = redis.createClient(config.port, config.host); //연결

redisClient .auth(config.password); //인증
redisClient .on('error', (err) => {
  //에러 핸들링
  console.log('redis connection error !!!' + err)
});


module.exports = redisClient;