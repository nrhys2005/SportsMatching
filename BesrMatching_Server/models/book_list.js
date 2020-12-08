var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const book_list = sequelize.define('book_list',{
        ground_id:{
            type: DataTypes.INTEGER,
            allowNull: false,    
        },
        user_id:{
            type: DataTypes.STRING(14), 
            allowNull: false,    
        },        
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
    book_list.associate = function(models){
        book_list.belongsTo(models.user, {
          foreignKey: "user_id"
        })      
      book_list.belongsTo(models.Ground, {
        foreignKey: "ground_id"
      })
    };
    return book_list;
};