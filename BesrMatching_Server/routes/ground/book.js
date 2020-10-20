const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

router.post('/', function (req, res) {
    console.log('<<ground/book>>');
    let start_time = new Date((req.body.date+ " " +req.body.start_time+":00"));
    let end_time = new Date(req.body.date+ " " +req.body.end_time+":00");
    
    var book_list = [];
    book_list.push(req.body.ground_id);
    book_list.push(req.body.user_id);
    book_list.push(req.body.phone);
    book_list.push(start_time);
    book_list.push(end_time);
    console.log('input_data : ' + book_list);
    insert_sql = "INSERT INTO best_matching.book_list(ground_id,user_id,phone, start_time, end_time) values(?,?,?,?,?)";
    dbconn.query(insert_sql, book_list, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query insert success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query insert success(result": "Success)');
                res.json({ "result": "Success" });
            }

        } else {
            console.log('Query insert error : ' + err);
            res.json({ "result": err });
        }
    });
});

router.get('/check', function (req, res) {
    console.log('<<ground/book/check>>');
    var input_data_array = [];
    input_data_array.push(req.query.ground_id);// json->array
    input_data_array.push(req.query.date + " " + "00:00:00");
    input_data_array.push(req.query.date + " " + "23:59:59");
    
    var check_sql = 'select * from best_matching.book_list where book_list.ground_id = ? and book_list.start_time >= ? and book_list.end_time <= ?';
    dbconn.query(check_sql, input_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log("match "+req.query.ground_id)
            res.json({ "result": 'ok', rows });
        } else {
            console.log('' + err);
        }
    });
});
module.exports = router;

