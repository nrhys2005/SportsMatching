const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

router.get('/', function (req, res) {
    console.log('<<ground/booking_list>>');
    
    var sql = "select ground.name, ground.price, book_list.ground_id, book_list.user_id, book_list.phone, DATE_FORMAT(book_list.start_time,'%Y-%m-%d %H:%i') as start_time, DATE_FORMAT(book_list.end_time,'%Y-%m-%d %H:%i') as end_time from best_matching.book_list,best_matching.ground where book_list.ground_id = ground.id and book_list.user_id = ?";
    dbconn.query(sql, req.query.user_id, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log("select Success")
            res.json({ "result": 'Success', rows });
        } else {
            console.log('' + err);
            res.json({ "result": 'fail'});
        }
    });
});
module.exports = router;

