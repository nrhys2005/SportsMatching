const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
//내 매칭 참가 리스트
router.get('/match/:user_id', function (req, res) {
    console.log('<<match/mymatching_list>>');
    var user_id = req.params.user_id;
    //var Data = JSON.parse(data); // JSON data 받음
    console.log('Search = '+ user_id);
    var sql = 'select * from best_matching.match, best_matching.matching_user where matching_user.match_id =match.id and matching_user.user_id= ?';

    dbconn.query(sql, user_id, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                console.log(rows);
                res.json({ "result": "Success",mymatch_list_info : rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});
router.get('/team_match/:user_id', function (req, res) {
    console.log('<<match/mymatching_list>>');
    var user_id = req.params.user_id;
    //var Data = JSON.parse(data); // JSON data 받음
    console.log('Search = '+ user_id);
    var sql = 'select * from best_matching.match, best_matching.matching_user where matching_user.match_id =match.id and matching_user.user_id= ?';

    dbconn.query(sql, user_id, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                console.log(rows);
                res.json({ "result": "Success",mymatch_list_info : rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});
module.exports = router;