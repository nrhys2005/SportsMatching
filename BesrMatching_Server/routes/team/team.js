const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀 버튼
router.get('/', function (req, res) {
    console.log('<<Team/team_get>>');
    var result_code = 404;

    var user_id = req.query.id;
    var data_array = [];
    var sql = 'SELECT id,master_id,user.team_name,wait_state FROM best_matching.user left join team using (team_name) where id =?';
    
    console.log('id = '+ user_id);
    data_array.push(user_id);
   
    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            result_code=200;
            res.json({ "result": result_code, team_main : rows});
        } else {
            console.log('Query Select Error : ' + err);
            result_code=404;
            res.json({ "result": result_code });
        }
    });

});
