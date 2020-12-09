const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const models = require("../../database");

//팀 가입신청
router.post('/', function (req, res) {
    console.log('<<Team/join>>');
    //select * from best_matching.team_waiting where team_name = ? and user_id = ?'
    models.team_waiting.findOne({
        where: {
            team_name: req.body.team_name,
            user_id : req.body.user_id
        },
    }).then((result) => {
        //'update best_matching.user set wait_state = ? where id = ?';
        models.User.update({
            wait_state: "Waiting"
        }, {
            where: {
                id: req.body.user_id
            }
        }).then(() => {
            console.log('Query Update Success');
        }).catch((err) => {
            console.log('Query Update Error : ' + err);
        });
        if (result.length == 0) {
            //'insert into best_matching.team_waiting(team_name,user_id) values(?,?)';
            models.team_waiting.create({
                team_name: req.body.team_name,
                user_id: req.body.user_id
            }).then(() => {
                console.log('Query insert success');
                res.json({ "result": 200 });
            }).catch((error) => {
                console.log('Query insert error : ' + err);
                res.json({ "result": 404, "err": err });
            });
        } else {
            console.log('Query select success(Duplicate application)');
            res.json({ "result": 202 });
        }
    }).catch((err) => {
        console.log('Query update error : ' + err);
        res.json({ "result": 404, "err": err });
    });
});


//팀 가입신청 승인
router.post('/agreement', function (req, res) {
    console.log('<<Team/agreement>>');
    //'update best_matching.user set team_name = ?,wait_state = ? where user.id = ?';
    models.User.update({
        team_name: req.body.team_name,
        wait_state : null
    }, {
        where: {
            id: req.body.user_id
        }
    }).then(() => {
        //'update best_matching.team set member_count = member_count + 1 where team_name = ?';
        models.Team.update({
            member_count: member_count+1
        }, {
            where: {team_name: req.body.team_name}
        }).then(() => {
            console.log('Query update success');
        }).catch((err) => {
            console.log('Query Update Error : ' + err);
        });
        //'DELETE from team_waiting where user_id = ?';
        models.team_waiting.destroy({
            where: { user_id: req.body.user_id },
          })
            .then(() => {
                console.log('Query delete success');
                res.json({ "result": 200 });
            })
            .catch((err) => {
                console.log('Query Update Error : ' + err);
                res.json({ "result": 404 });
            });
    }).catch((err) => {
        console.log('Query Update Error : ' + err);
        res.json({ "result": 404 });
    });
});
module.exports = router;