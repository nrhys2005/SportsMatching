const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//내 팀 정보
router.get('/', function (req, res) {
    console.log('<<Team/myteam_get>>');

    let result_code = 404;
    let team_name = req.query.team_name;
    let data_array = [];
    let sql=  'select * from best_matching.team where team_name= ?';
    console.log('team_name = '+ team_name);
    data_array.push(team_name);
   
    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            result_code=200;
            res.json({ "result": result_code, myteam_info : rows});
        } else {
            console.log('Query Select Error : ' + err);
            result_code=404;
            res.json({ "result": result_code });
        }
    });
});
module.exports = router;