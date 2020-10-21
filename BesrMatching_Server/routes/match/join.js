const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//매치 참가
router.post('/', function (req, res) {
    console.log('<<match/join>>');
    var inputData = req.body;
        var user_id = inputData.user_id;
        var match_id = inputData.match_id;
        console.log("cancel id, match_id= " + user_id+ match_id);
        var sql = 'select * from  best_matching.matching_user where user_id = ? and match_id= ?';
        var params = [ user_id,match_id]
        dbconn.query(sql, params, function (err, rows, fields) {//DB connect
            if (!err) {
                if (rows.length == 0) {
                    var count_sql = 'select * from best_matching.match where match.id =?';
                    dbconn.query(count_sql, match_id, function (err, rows, fields) {//DB connect
                        if (!err) {
                            if (rows.length == 0) {
                                console.log('Query Select Success("result": "no find")');
                                res.json({ "result": "no find" });
                            }
                            else {
                                var max_user = rows[0].max_user;
                                if (rows[0].participants >= max_user) {
                                    console.log('Query Select Success(result": "matching is full")');
                                    res.json({ "result": "full" });
                                }
                                else {
                                    var insert_data_array = [];
                                    insert_data_array.push(user_id);
                                    insert_data_array.push(match_id);
                                    var insert_sql = "INSERT INTO best_matching.matching_user(user_id,match_id) values(?,?)";
                                    dbconn.query(insert_sql, insert_data_array, function (err, rows, fields) {//DB connect
                                        if (!err) {
                                            if (rows.length == 0) {
                                                console.log('Query insert success("result": "no find")');
                                                res.json({ "result": "no find" });
                                            }
                                            else {
                                                console.log('Query insert success(result": "Success)');
                                            }

                                        } else {
                                            console.log('Query insert error : ' + err);
                                            res.json({ "result": err });
                                        }
                                    });
                                    var update_sql = "UPDATE best_matching.match SET participants = participants + 1 where id = ?";
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
                            }

                        } else {
                            console.log('Query Select Error : ' + err);
                            res.json({ "result": err });
                        }
                    });
                }
                else {
                    console.log('Query Select Success("result": "Already participating")');
                    res.json({ "result": "Already participating"});
                }

            } else {
                console.log('Query Select Error : ' + err);
                res.json({ "result": err });
            }
        });
});
module.exports = router;