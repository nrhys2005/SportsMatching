var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const user = sequelize.define('user',{
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
         team_name:{       
            type: DataTypes.STRING(10),               
         },
        wait_state:{
            type: DataTypes.STRING(20),
        }
    },{
        timestamps: false,
        tableName: 'user'
    });
    user.associate = (models)=>{
        models.User.belongsTo(models.team, {
            foreignKey: 'team_name'
        });
        
        models.User.hasMany(models.book_list);//
        models.User.hasMany(models.team_waiting);//
        models.User.hasMany(models.matching_user);//
        models.User.hasMany(models.team_matching_user);
        models.User.hasMany(models.team_board_part_list);//
    };    
    return user;
};