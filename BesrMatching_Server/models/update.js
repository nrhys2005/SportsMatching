var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const update = sequelize.define('update',{
        id :{
            type: DataTypes.INTEGER,
            allowNull: false,      
            primaryKey: true,
        },
        category : {
            type: DataTypes.STRING(10),
        },
        title :{
            type: DataTypes.STRING(20),
        },
        content :{
            type: DataTypes.STRING(2048),
        },
     
    }, {
        timestamps: false,
        tableName: 'update'
    });
  
    return update;
};
