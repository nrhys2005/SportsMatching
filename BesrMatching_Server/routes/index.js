const express = require('express');
const router = express.Router();

//라우터의 get()함수를 이용해 request URL('/')에 대한 업무처리 로직 정의
router.get('/', function(req, res, next) {
    res.send('index page');
});


//회원가입 라우팅
router.use('/signup', require('./signup/signup'));

//로그인 라우팅
router.use('/login',  require('./login'));


router.use('/team',  require('./team'));

router.use('/match',  require('./match'));
router.use('/ground',  require('./ground'));

router.use('/Help',  require('./Help'));
//모듈에 등록해야 web.js에서 app.use 함수를 통해서 사용 가능


module.exports = router;