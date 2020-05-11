const express = require('express');
const http = require('http');
const PORT = process.env.PORT || 3000;
const app = express();

//express 서버 포트 설정(cafe24 호스팅 서버는 8001 포트 사용)
app.set('port', process.env.PORT || PORT);

//서버 생성
// http.createServer(app).listen(app.get('port'), function(){
//     console.log('Express server listening on port ' + app.get('port'));
// });

//라우팅 모듈 선언

app.use('/', require('./routes/index'));


app.listen(PORT, () => {
    console.log('Express server listening on port '+PORT);
});
