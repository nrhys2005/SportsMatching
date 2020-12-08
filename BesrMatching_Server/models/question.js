var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const question = sequelize.define('question',{
        id :{
            type: DataTypes.INTEGER,
            allowNull: false,      
            primaryKey: true,
        },
        user_id:{
            type: DataTypes.STRING(14),
        },
        title :{
            type: DataTypes.STRING(20),
        },
        category : {
            type: DataTypes.STRING(45),
        },        
        content :{
            type: DataTypes.STRING(2048),
        },
    }, {
        timestamps: false,
        tableName: 'question'
    });
  
    return question;
};
