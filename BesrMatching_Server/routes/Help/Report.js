const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//신고하기----------------------------------------
router.post('/', function (req, res) {
    console.log('<<Help/Report>>');

        var input_data_array= [];
        var inputData = req.body; // JSON data 받음

        var result_code =404;
        input_data_array.push(inputData.report_title);
        input_data_array.push(inputData.report_id);
        input_data_array.push(inputData.report_category);
        input_data_array.push(inputData.report_target);
        input_data_array.push(inputData.report_content);

        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'INSERT INTO best_matching.report ( title, user_id, category,target_id, content) values(?,?,?,?,?)';
        dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Insert success');
                console.log(rows);
                result_code=200;
                res.json({ "result": result_code });
            } else {
                console.log('Query Error : ' + err);
                result_code=404;
                res.json({ "result": result_code });
            }
        });

});
module.exports = router;