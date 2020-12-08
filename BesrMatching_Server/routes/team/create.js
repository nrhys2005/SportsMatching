const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const models = require("../../database");

//팀 생성
router.post('/', function (req, res) {
    console.log('<<Team/create>>');
    //INSERT INTO best_matching.team (team_name,master_id, phonenumber, age_avg, level, location,week,comment,member_count) VALUES(?, ?, ?, ?, ?, ?, ?, ?)
    models.Team.create({
        team_name: req.body.team_name,
        master_id: req.body.master_id,
        phonenumber: req.body.phonenumber,
        age_avg: req.body.age_avg,
        level: req.body.level,
        location: req.body.location,
        week: req.body.week,
        comment: req.body.comment,
        member_count: 1
    }).then(() => {
        console.log('Query insert success');
    }).catch((error) => {
        console.log('Query Error : ' + error);
    });
    //'update best_matching.user set team_name = ? where id = ? ';
    models.User.update({
        team_name: req.body.team_name,
    }, {
        where: {
            id: req.body.master_id
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