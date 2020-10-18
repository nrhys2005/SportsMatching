const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
//매칭 취소
router.post('/', function (req, res) {
    console.log('<<match/mymatching_cancel>>');
    req.on('data', (data) => {
        var inputData = JSON.parse(data); // JSON data 받음
        var user_id = inputData.user_id;
        var match_id = inputData.match_id;
        var delete_data_array = [];
        delete_data_array.push(user_id);
        delete_data_array.push(match_id);
        console.log('cancel id, match_id= ' + user_id, match_id);
        var sql = "DELETE FROM matching_user WHERE user_id = ? and match_id = ?";

        dbconn.query(sql, delete_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                if (rows.length == 0) {
                    console.log('Query delete success("result": "no find")');
                    res.json({ "result": "no find" });
                }
                else {
                    console.log('Query delete success(result": "Success)');
                    var update_sql = "UPDATE best_matching.match SET participants = participants - 1 where id = ?";
                    dbconn.query(update_sql, match_id, function (err, rows, fields) {//DB connect
                        if (!err) {
                            if (rows.length == 0) {
                                console.log('Query update success("result": "no find")');
                                res.json({ "result": "no find" });
                            }
                            else {
                                console.log('Query update success(result": "Success)');
                                res.json({ "result": "Success" });
                            }

                        } else {
                            console.log('Query update error : ' + err);
                            res.json({ "result": err });
                        }
                    });
                }

            } else {
                console.log('Query delete error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});
module.exports = router;