const express = require('express');
const http = require('http');
const PORT = process.env.PORT || 3000;
const app = express();
const cookieParser = require('cookie-parser')
const session = require('express-session')
//const FileStore = require('session-file-store')(session)
//express 서버 포트 설정(cafe24 호스팅 서버는 8001 포트 사용)
app.set('port', process.env.PORT || PORT);

app.use(cookieParser())
app.use(session({
    secret: 'knucoin',
    resave: false,
    saveUninitialized: true,
    //store: new FileStore()
}))


//라우팅 모듈 선언

app.use('/', require('./routes/index'));
app.use('/', require('./upload/index'));


app.listen(PORT, () => {
    console.log('Express server listening on port '+PORT);
});
