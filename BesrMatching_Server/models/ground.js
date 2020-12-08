var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const ground = sequelize.define('gruond',{
        id :{
            type: DataTypes.INTEGER,
            allowNull: false,      
            primaryKey: true,
        },
        name : {
            type: DataTypes.STRING(45),
        },
        latitude :{
            type: DataTypes.DOUBLE,
        },
        longtitude :{
            type: DataTypes.DOUBLE,
        },
        price: {
            type: DataTypes.STRING(11),
        }
    }, {
        timestamps: false,
        tableName: 'ground'
    });
     ground.associate = (models)=>{
        models.Ground.hasMany(models.book_list);//
    };
    return ground;
};
