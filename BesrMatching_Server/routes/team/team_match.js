const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

router.post('/', function (req, res) {
    console.log('<<team/team_match>>');
});

router.get('/booking_list', function (req, res) {  
    console.log('<<team/team_match/booking_list>>');

    var sql = '     ;
    dbconn.query(sql, req.query.user_id, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log("match "+req.query.ground_id)
            res.json({ "result": 'Success', rows });
        } else {
            console.log('' + err);
            res.json({ "result":  'fail'});
        }
    });
});
router.get('/member_list', function (req, res) {  
    console.log('<<team/team_match/member_list>>');
    var sql=  'select * from best_matching.user where team_name= ?';

    dbconn.query(sql, req.query.team_name, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            res.json({ "result": 'Success', member_info : rows});
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result":  'fail'});
        }
    });
});
module.exports = router;

