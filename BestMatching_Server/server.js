const PORT = process.env.PORT || 3000;
const express = require('express');
const server = express();


//GET www.facebook.com/
//POST ID: abc, PW : 1234
//
server.get("/",(req,res) => {
    res.send("<h1>Sever Test</h1>");
});

server.get('/bye', function (req, res) {
    res.send('Bye World!');
});
/*
server.set('views', __dirname + '/views');
server.set('view engine', 'ejs');
server.use(express.static(__dirname + "/public"));
*/

server.listen(PORT, (err) => {
    if(err) return console.log(err);
    console.log("The server is listening on port ",PORT);
});