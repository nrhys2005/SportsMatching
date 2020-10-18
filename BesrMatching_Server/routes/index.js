const express = require('express');
const router = express.Router();

//라우터의 get()함수를 이용해 request URL('/')에 대한 업무처리 로직 정의
router.get('/', function(req, res, next) {
    res.send('index page');
});

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