const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀원 조회----------------------------------
router.get('/', function (req, res) {
    console.log('<<Team/myteam_list_get>>');
    var result_code = 404;
    var team_name = req.query.team_name;
    var data_array = [];
    var sql=  'select id, name, age, location, phone, position from best_matching.user where team_name= ?';
    console.log('team_name = '+ team_name);
    data_array.push(team_name);

    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            result_code=200;
            res.json({ "result": result_code, member_info : rows});
        } else {
            console.log('Query Select Error : ' + err);
            result_code=404;
            res.json({ "result": result_code });
        }
    });
});
module.exports = router;