const express = require('express');
const router = express.Router();

//라우터의 get()함수를 이용해 request URL('/')에 대한 업무처리 로직 정의
router.get('/', function(req, res, next) {
    res.send('index page');
});

//안쓰는듯
router.use('/', require('./ground'));
//구장 검색(가까운거리 순)
router.use('/search', require('./search'));
//구장 등록
router.use('/create', require('./create'));
//구장 예약
router.use('/book', require('./book'));
//예약 리스트
router.use('/booking_list', require('./booking_list'));

//구장 예약 현황 확인
router.use('/timecheck', require('./timecheck'));
module.exports = router;