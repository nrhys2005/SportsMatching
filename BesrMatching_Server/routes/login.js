const express = require('express');
const router = express.Router();
const crypto = require('crypto');
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const moment = require('moment')

require('moment-timezone')
moment.tz.setDefault('Asia/Seoul')

router.post('/', function (req, res) {
    console.log('<<Login>>');

    req.on('data', (data) => {
        inputData = JSON.parse(data);
        var login_id = inputData.id;
        var login_pw = inputData.pw;
        console.log('로그인 시도 아이디 : ' + login_id);
        var sql = 'select * from user where id=?';
        var result = 'error';
        var param = [login_id];

        dbconn.query(sql, param, function (err, rows) {
            var find_pw = false;
            if (err) {
                console.log(err);
                res.json({
                    'result': 'No find'
                });
            } 
            else {
                find_pw = true;
                if(rows.length!=1){
                    console.log('존재하지 않는 계정입니다!');
                    res.json({
                        'result': 'No find'
                    });
                }
                else {
                    var salt = rows[0].salt;
                    //console.log('salt ' + rows[i].salt);
                    //console.log('pw ' + inputData.pw);
                    //console.log('row_pw ' + rows[i].pw);
                    var pw = rows[0].pw;
                    // var code;
                    //let hashPassword = crypto.createHash("sha512").update(login_pw + salt).digest("hex");
                    
                    crypto.randomBytes(64, (err, buf) => {
                        crypto.pbkdf2(login_pw, salt, 100000, 64, 'sha512', (err, key) => {
                            //crypto.pbkdf2(inputData.pw, buf.toString('base64'), 9000, 64, 'sha512', (err, key) => {
                            var code;
                            if (key.toString('base64') === pw) {
                                code = 'Success';
                                //console.log(key.toString('base64'));
                                //console.log(salt);
                                //console.log(pw);
                                console.log('로그인 성공! ' + login_id + '님 환영합니다!');



                                req.session.user =
                                {
                                    user_id: login_id,
                                    authorized: true
                                };
                                //res.cookie('user_id', login_id)
                                var session_id = req.session.id
                                console.log("세션 "+ req.session.id)
                                console.log("세션 아이디"+req.session.user.user_id);


                                req.session.id = login_id;


                                var select_sql = 'select * FROM session WHERE user_id = ?';
                                dbconn.query(select_sql, login_id, function (err, rows, fields) {
                                    if (!err) {
                                        var date = moment().format('YYYY-MM-DD HH:mm:ss')
                                        if(rows.length==0){
                                            var insert_sql = 'insert into session(session_id, user_id, date) values(?,?,?)';
                                            var input_data_array= [];
                                            input_data_array.push(session_id);
                                            input_data_array.push(login_id);
                                            input_data_array.push(date);
                                            dbconn.query(insert_sql, input_data_array, function (err, rows, fields) {
                                                if (!err) {
                                                    console.log("insert session");
                                                } 
                                                else {
                                                    console.log(err);
                                                }
                                            });
                                        }
                                        else if (rows[0].user_id == login_id) {
                                            var update_sql = 'update session set session_id = ?, date = ? where user_id = ?';
                                            var input_data_array= [];
                                            input_data_array.push(session_id);
                                            input_data_array.push(date);
                                            input_data_array.push(login_id);
                                            dbconn.query(update_sql, input_data_array, function (err, rows, fields) {
                                                if (!err) {
                                                    console.log("update session");
                                                } 
                                                else {
                                                    console.log(err);
                                                }
                                            });
                                        }
                                        else {
                                            console.log('session error');
                                            console.log(err);
                                        }
                                    } else {
                                        res.send(err);
                                        console.log(err);
                                    }
                                });
                            }
                            else {
                                //console.log(key);
                                //console.log(key.toString('base64'));
                                //console.log(salt);
                                console.log(pw);
                                code = 'No find'
                                console.log('비밀번호가 틀렸습니다!');
                            }
                            res.json({
                                'result': code
                            });
                        });
                    });          
                }
            }
        })

    });
});


module.exports = router;