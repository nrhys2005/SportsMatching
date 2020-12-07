const express = require('express'),
 sequelize = require("./models/index").sequelize,
 PORT = process.env.PORT || 3000,
 app = express(),
 cookieParser = require('cookie-parser')
 
//const http = require('http');

var bodyParser = require('body-parser')

app.set('port', process.env.PORT || PORT);

app.use(cookieParser())

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

sequelize.sync();

//라우팅 모듈 선언
app.use('/', require('./routes/index'));
app.use('/', require('./upload/index'));



app.listen(PORT, () => {
    console.log('Express server listening on port '+PORT);
});
