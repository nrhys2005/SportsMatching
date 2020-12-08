const express = require('express');
const router = express.Router();
const config = require('../config/config.js').redis.development
var session = require('express-session');
//let RedisStore = require('connect-redis')(session)
//var redisClient = require('../config/redis_connection.js');
//라우터의 get()함수를 이용해 request URL('/')에 대한 업무처리 로직 정의
router.get('/', function(req, res, next) {
    res.send('index page');
});
//session setting
// router.use(session({
//     store: new RedisStore({
//         client: redisClient,
//         host: config.host,
//         port: config.port,
//         //prefix : "session:",
//         ttl : 260 //세션만료기한
//     }),
//     secret : config.secret,
//     //cookie : { maxAge: 2592000000 },
//     saveUninitialized: false,
//     resave: false
// }));

//유저 라우팅
router.use('/user', require('./user/index'));
//매치 라우팅
router.use('/match',  require('./match/index'));
//팀 라우팅
router.use('/team',  require('./team/index'));
//구장 라우팅
router.use('/ground',  require('./ground/index'));
//고객게시판 라우팅
router.use('/Help',  require('./Help/index'));

module.exports = router;