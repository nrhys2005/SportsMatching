const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀 정보 수정
router.post('/', function (req, res) {
    console.log('<<Team/myteam_update>>');

    var result_code = 404;
    var update_data_array = [];

    console.log('input_data : ' + req.body);
    var sql_update = 'update best_matching.team set phonenumber = ?, age_avg = ?, level=?, location=?, week=?, comment=? where team_name = ? ';
    update_data_array.push(req.body.phonenumber);
    update_data_array.push(req.body.age_avg);
    update_data_array.push(req.body.level);
    update_data_array.push(req.body.location);
    update_data_array.push(req.body.week);
    update_data_array.push(req.body.comment);
    update_data_array.push(req.body.team_name);
    dbconn.query(sql_update, update_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Update Success');
            console.log(rows);
            result_code = 200;
            res.json({ "result": result_code });

        } else {
            console.log('Query Update Error : ' + err);
            result_code = 404;
            res.json({ "result": result_code });
        }
    });
});
module.exports = router;