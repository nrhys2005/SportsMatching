const express = require('express');
const router = express.Router();

//팀 버튼
router.use('/team',  require('./team'));
//내팀 정보
router.use('/myteam',  require('./myteam'));
//팀 정보 수정
router.use('/team_update',  require('./team_update'));
//내 팀원 리스트
router.use('/myteam_list',  require('./myteam_list'));
//팀 가입신청
router.use('/join',  require('./join'));
//팀 찬가신청 리스트(팀장)
router.use('/team_waiting_list',  require('./team_waiting_list'));
//팀원 추방(팀장)
router.use('/expulsion',  require('./expulsion'));
//팀 생성 
router.use('/create',  require('./create'));
//팀 검색 
router.use('/search',  require('./search'));


module.exports = router;