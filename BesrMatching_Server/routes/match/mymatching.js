const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const cookieParser = require('cookie-parser');
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
//내 매칭 정보
router.get('/:match_id', function (req, res) {
    console.log('<<match/mymatching>>');
    var search_match_id = req.params.match_id;
    //var Data = JSON.parse(data); // JSON data 받음
    console.log('Search = '+ search_match_id);
    var sql = 'select * from best_matching.match where match.id = ?';

    dbconn.query(sql, search_match_id, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success",mymatch_info : rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});
module.exports = router;