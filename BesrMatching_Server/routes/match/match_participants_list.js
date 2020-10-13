const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
//매칭 참여자 리스트
router.get('/:match_id', function (req, res) {
    console.log('<<match/match_participants_list>>');
    var match_id = req.params.match_id;
    //var Data = JSON.parse(data); // JSON data 받음
    console.log('Search = '+ match_id);
    var sql = 'select * from best_matching.user, best_matching.matching_user where user.id = matching_user.user_id and matching_user.match_id =?';
    dbconn.query(sql, match_id, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": 404 });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                console.log(rows);
                res.json({ "result": "Success",mymatch_info : rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": 404,"err": err });
        }
    });
});
module.exports = router;