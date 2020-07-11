const express = require("express");

const PORT = 7005;
const app = express();

let users = [
    { id: 1, email: 'admin', password: 'admin', firstName: 'Admin', lastName: 'Admin' },
    { id: 2, email: 'aaa@hotmail.com', password: 'passs', firstName: 'Dan', lastName: 'Dan' },
    { id: 3, email: 'bbb4@gmail.com', password: 'passs', firstName: 'Ana', lastName: 'Ana' }
];

let report = [
    { an_aparitie: 2000, nr_piese: 80 },
    { an_aparitie: 2010, nr_piese: 40 },
    { an_aparitie: 2019, nr_piese: 45 },
    { an_aparitie: 2014, nr_piese: 53 }
];

let songs = [];

app.post("/addSong", (req, res) => {
    req.on('data', data => {
        console.log(JSON.parse(data));
        songs.push(JSON.parse(data));

        res.send(JSON.parse(data));
        console.log(songs)
    })
});

app.get("/report", (req, res) => {
    console.log("Get Report");
    res.send(report);
});

app.post("/login", (req, res) => {
    req.on('data', data => {
        const params = JSON.parse(data);
        console.log(params)
        const user = users.filter(function(x) {
            return (x.email == params.email && x.password == params.password);
        });
        console.log(user)
        if (user == null) res.status(500).send('Username or password is incorrect');
        else {
            user.password = null;
            res.send(user);
        }
    })
});

app.listen(PORT, () => {
 console.log(`Server is listening on port: ${PORT}`);
});