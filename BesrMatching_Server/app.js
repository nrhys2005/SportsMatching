const express = require('express');
const http = require('http');
const PORT = process.env.PORT || 3000;
const app = express();
const cookieParser = require('cookie-parser')
const session = require('express-session')
const FileStore = require('session-file-store')(session)
//express 서버 포트 설정(cafe24 호스팅 서버는 8001 포트 사용)
app.set('port', process.env.PORT || PORT);

app.use(cookieParser())
app.use(session({
    key: 'sid',
    secret: 'knucoin',
    resave: false,
    saveUninitialized: true,
    //store: new FileStore()
}))

// app.get('/', (req, res, next) => {  // 3
//     console.log(req.session);
//     if(!req.session.num){
//       req.session.num = 1;
//     } else {
//       req.session.num = req.session.num + 1;
//     }
//     res.send(`Number : ${req.session.num}`);
//   });
// //서버 생성
// http.createServer(app).listen(app.get('port'), function(){
//     console.log('Express server listening on port ' + app.get('port'));
// });

//라우팅 모듈 선언

app.use('/', require('./routes/index'));
app.use('/', require('./upload/index'));


app.listen(PORT, () => {
    console.log('Express server listening on port '+PORT);
});
