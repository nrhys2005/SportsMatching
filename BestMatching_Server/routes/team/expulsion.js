const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀원 추방
router.post('/expulsion', function (req, res) {
    console.log('<<Team/expulsion>>');
        var result_code =404;
        var data_array= [];
        var user_id = req.body.id;
        var update_sql = 'update best_matching.user set team_name = ?, wait_state= ?  where user.id = ?';
        data_array.push(null);
        data_array.push(null);
        data_array.push(user_id);

        dbconn.query(update_sql, data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query update success');
                var delete_sql = 'DELETE from best_matching.team_waiting where user_id = ?';
                dbconn.query(delete_sql,user_id, function (err, rows, fields) {//DB connect
                    if (!err) {
                        console.log('Query delete success');
                        result_code=200;
                        res.json( {"result": result_code});
                        
                    } else {
                        console.log('Query Update Error : ' + err);
                        result_code=404;
                        res.json({ "result": result_code });
                    }
                });
                
            } else {
                console.log('Query Update Error : ' + err);
                result_code=404;
                res.json( {"result": result_code});
            }
        });
});
module.exports = router;