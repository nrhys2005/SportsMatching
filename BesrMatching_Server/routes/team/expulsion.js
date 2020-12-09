const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const models = require("../../database");

//팀원 추방
router.post('/expulsion', function (req, res) {
    console.log('<<Team/expulsion>>');
    //update best_matching.user set team_name = ?, wait_state= ?  where user.id = ?';
    models.User.update({
        team_name: null,
        wait_state: null,
    }, {
        where: {
            id: req.body.id
        }
    }).then(() => {
        console.log('Query update success');
        models.team_waiting.destroy({
            where: { 
                user_id: req.body.id
            },
        }).then((result) => {
            console.log('Query delete success')
            res.json({ "result": 200 });
        }).catch((error) => {
            console.log('Query delete error : ' + err);
            res.json({ "result": 404 });
        });

    }).catch((err) => {
        console.log('Query Update Error : ' + err);
        res.json({ "result": 404 });
    });
});
module.exports = router;