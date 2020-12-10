const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const models = require("../../database");

//내 팀 정보
router.get('/', function (req, res) {
    console.log('<<Team/myteam_get>>');
    //select * from best_matching.team where team_name= ?
    models.team.findOne({
        where: {
            team_name: req.query.team_name,
        },
    }).then((result) => {
        console.log('Query Select Success(result: Success)');
        res.json({ "result": 200, myteam_info: result });
    }).catch((err) => {
        console.log('Query Select Error : ' + err);
        res.json({ "result": 404 });
    });
});
module.exports = router;