const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


//공지사항--------------------------------------- 
router.get('/', function (req, res) {
    console.log('<<Help/Notice_Get>>');

    var sql = 'select title,create_time, content from best_matching.notice';
    
    var result_code=404;
    dbconn.query(sql, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            result_code=200;
            res.json({ "result": result_code, notice_info : rows});
        } else {
            console.log('Query Select Error : ' + err);
            result_code=404;
            res.json({ "result": result_code });
        }
    });
});