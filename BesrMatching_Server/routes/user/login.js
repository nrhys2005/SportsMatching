const express = require('express');
const router = express.Router();
const crypto = require('crypto');
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const moment = require('moment');
const models = require("../../database");

require('moment-timezone')
moment.tz.setDefault('Asia/Seoul')

router.post('/', function (req, res) {
    console.log('<<Login>>');
    if (!req.session.login) {
        var login_id = req.body.id;
        var login_pw = req.body.pw;
        console.log('로그인 시도 아이디 : ' + login_id);
        models.User.findOne({
            where: {
                id: login_id,
            },
        }).then((result) => {
            if (result.length == 0) {
                req.session.login = false;
                res.json({
                    'result': 'No find'
                });
            }
            else {
                var salt = rows[0].salt;
                var pw = rows[0].pw;
                crypto.randomBytes(64, (err, buf) => {
                    crypto.pbkdf2(login_pw, salt, 100000, 64, 'sha512', (err, key) => {
                        var code;
                        if (key.toString('base64') === pw) {
                            code = 'Success';
                            console.log('로그인 성공! ' + login_id + '님 환영합니다!');
                            req.session.user_id = login_id;
                        }
                        else {
                            code = 'No find'
                            console.log('비밀번호가 틀렸습니다!');
                        }
                        res.json({
                            'result': code
                        });
                    });
                });
            }
        }).catch((err) => {
            console.log(err);
            res.send(false);
        });
    };
});

router.post('/auto', function (req, res) {
    if (req.session.user_id) {
        res.send(true);
    } else {
        //없으면
        res.send(false);
    }
});
module.exports = router;