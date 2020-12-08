var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const book_list = sequelize.define('book_list',{
        phone :{
            type: DataTypes.STRING(20),          
        },
        start_time : {
            type: DataTypes.DATE,
        },
        end_time :{
            type: DataTypes.DATE,
        }
    }, {
        timestamps: false,
        tableName: 'book_list'
    });
    return book_list;
};