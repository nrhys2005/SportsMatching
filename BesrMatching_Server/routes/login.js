const express = require('express');
const router = express.Router();
const crypto = require('crypto');
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


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