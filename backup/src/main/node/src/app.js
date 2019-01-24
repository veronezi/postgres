const express = require("express");
const multer  = require('multer');
const os = require("os");
const fs = require("fs");
const mkdirp = require('mkdirp');

const baseDir = process.env.BASE_DIR ? process.env.BASE_DIR : os.tmpdir();
const upload = multer({ dest: `${baseDir}/uploads/` });
const uploaded = `${baseDir}/uploaded/`;
const basebackup = `${baseDir}/basebackup/`;
mkdirp(uploaded);

const app = express();
const port = 3000;

const start = () => {
    app.post('/upload/:filename', upload.single('upload'), function (req, res, next) {
        fs.copyFileSync(`${baseDir}/uploads/${req.file.filename}`, `${uploaded}/${req.file.originalname}`);
        res.sendStatus(200);
        console.log(`[backup] ${req.file.originalname} archived.`);
    });
    app.post('/basebackup', upload.single('upload'), function (req, res, next) {
        fs.copyFileSync(`${baseDir}/uploads/${req.file.filename}`, `${basebackup}${req.file.originalname}`);
        res.sendStatus(200);
        console.log(`[backup] basebackup ${req.file.originalname} archived.`);
    });
    app.use('/basebackup', express.static(basebackup));
    app.use('/uploaded', express.static(uploaded));
    app.listen(port, () => console.log(`App listening on port ${port}!`));
};

module.exports = {
    start
};
