const express = require('express');
const router = express.Router();

//라우터의 get()함수를 이용해 request URL('/')에 대한 업무처리 로직 정의
router.get('/', function(req, res, next) {
    res.send('index page');
});

//매치 생성
router.use('/create',  require('./create'));
//매치 참가
router.use('/join',  require('./join'));
//매치 검색
router.use('/search',  require('./search'));
//내 매치리스트
router.use('/mymatching_list',  require('./mymatching_list'));
//매칭 취소
router.use('/mymatching_cancel',  require('./mymatching_cancel'));
//매치 참여자리스트 
router.use('/match_participants_list',  require('./match_participants_list'));
//내 매칭정보
router.use('/mymatching',  require('./mymatching'));
//팀매칭
router.use('/create_team_match', require('./create_team_match'));

module.exports = router;