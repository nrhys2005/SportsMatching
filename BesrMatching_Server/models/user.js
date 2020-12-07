var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    return sequelize.define('user',{
        id: {
            type: DataTypes.STRING(14),
            allowNull: false,      
            primaryKey: true,
        },
        name: {
            type: DataTypes.STRING(20),
            allowNull: false,
        },
        salt:{
            type: DataTypes.STRING(10000),
            allowNull: false
        },
        pw:{
            type: DataTypes.STRING(20),
            allowNull: false
        },
        email:{
            type: DataTypes.STRING(24),
            allowNull: false
        },
        phone:{
            type: DataTypes.STRING(45),            
        },
        location:{
            type: DataTypes.STRING(45),            
        },
        age:{
            type: DataTypes.INTEGER,
        },
        tall:{
            type: DataTypes.INTEGER,
        },
        position:{
            type: DataTypes.STRING(12),            
        },
        pro:{
            type: DataTypes.STRING(4),            
        },
        left_foot:{
            type: DataTypes.STRING(5),        
        },
        // team_name:{       
        //     type: DataTypes.STRING(10),               
        // },
        wait_state:{
            type: DataTypes.STRING(20),
        }
    },{
        timestamps: false,
        tableName: 'user'
    });
    user.associate = (models)=>{
        models.User.hasMany(models.book_list,{
            foreignKey: 'fk_book_list_user'
        });
        models.User.hasMany(models.team_waiting,{
            foreignKey: 'fk_team_wating_user'
        });
        models.User.hasMany(models.matching_user,{
            foreignKey: 'fk_matching_user_user'
        });
        models.User.hasMany(models.team_matching_user,{
            foreignKey: 'fk_team_matching_user_user'
        });
        models.User.hasMany(models.team_board_part_list,{
            foreignKey: 'fk_team_board_part_list_user'
        });
    };    
    
};