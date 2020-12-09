const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const models = require("../../database");

//팀 탈퇴 -------------------
router.post('/', function (req, res) {
    console.log('<<Team/myteam_drop>>');
    //'update best_matching.user set team_name = null where id=?';
    models.User.update({
        team_name : null,
    }, {
        where: {
            id: req.body.id
        }
    }).then(() => {
        console.log('Query Update Success');
        res.json({ "result": 200 });

    }).catch((err) => {
        console.log('Query Update Error : ' + err);
        res.json({ "result": 404 });
    });
});
module.exports = router;