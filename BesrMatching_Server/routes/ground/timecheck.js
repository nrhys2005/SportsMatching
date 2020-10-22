const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

router.get('/:date', function (req, res) {
    console.log('<<ground/timecheck>>');
    var date = req.params.date;
    console.log(date);
    var timecheck_='%' + date + '%';
    var sql = "select DATE_FORMAT(start_time,'%Y-%m-%d %H:%i') as start_time, DATE_FORMAT(end_time,'%Y-%m-%d %H:%i') as end_time from book_list where start_time like ?";
    
    dbconn.query(sql, timecheck_, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log("time "+rows[1].end_time)
            res.json({ "result": 'Success', time_info : rows });
        } else {
            console.log('' + err);
            res.json({ "result": 'fail'});
        }
    });
});
module.exports = router;

