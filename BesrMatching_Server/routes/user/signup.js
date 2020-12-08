const express = require('express');
const router = express.Router();
const crypto = require('crypto');
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const models = require("../../database");


router.post('/', function (req, res) {
    console.log('<<signup>>');
    crypto.randomBytes(64, (err, buf) => {
        crypto.pbkdf2(req.body.pw, buf.toString('base64'), 100000, 64, 'sha512', (err, key) => {
            salt = buf.toString('base64')
            pwd = key.toString('base64')
            models.User.create({
                id :req.body.id,
                name : req.body.name,
                salt : salt,
                pwd : pwd,
                email : req.body.email, 
              }).then(() => {
                console.log('Query insert success');
                res.json({ "result": "Success" });
              }).catch((error) => {
                console.log('Query Error : ' + error);
                res.json({ "result": err });
              });
        });
    });
    // crypto.pbkdf2("pass", "salt", 100000, 64, 'sha512', (err, key) => {
    //     console.log(key.toString('base64'));
    //     console.log(key.toString('base64') === "pass");
    // });
    //input_data_array.push(inputData.pw);


});

router.post('/check', function (req, res, next) {
    console.log('<<Signup/check>>');

    var find_id = req.body.id;
    models.User.findOne({
        where: {
            id: find_id,
        },
    }).then((result) => {
        if (result.length == 0) {
            req.session.login = false;
            res.json({
                'result': 'No find'
            });
        }
        else if (result[0].id == find_id) {
            console.log('duplication');
            res.json({ "result": "duplication" });
        }
        else {
            console.log('no duplication');
            res.json({ "result": "no duplication" });
        }
    }).catch((err) => {
        res.send(false);
        console.log(err);
    });
});

module.exports = router;