const express = require('express');
const router = express.Router();

//라우터의 get()함수를 이용해 request URL('/')에 대한 업무처리 로직 정의
router.get('/', function(req, res, next) {
    res.send('index page');
});

//내정보 확인, 수정
router.use('/Myinfo',  require('./Myinfo'));
//공지사항
router.use('/Notice',  require('./Notice'));
//1:1문의 등록
router.use('/Question_Regist',  require('./Question_Regist'));
//1:1문의(리스트)
router.use('/Question',  require('./Question'));
//신고하기
router.use('/Report',  require('./Report'));
module.exports = router;