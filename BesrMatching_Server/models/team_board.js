
var models = require('../models');
module.exports = (sequelize, DataTypes) => {
    const team_board = sequelize.define('team_board', {
        id : {
            type: DataTypes.INTEGER,
            allowNull: false,   
            primaryKey: true,
        },
        team_name : {
            type: DataTypes.STRING(45),
            allowNull: false,   
        },
        title:{
            type: DataTypes.STRING(20),
        },
        max_part_count:{
            type: DataTypes.INTEGER,
        },
        part_count:{
            type: DataTypes.INTEGER,
        },
        no_part_count:{
            type: DataTypes.INTEGER,
        }
    }, {
        timestamps: false,
        tableName: 'team_board'
    });
    team_board.associate = (models)=>{    
        models.Team_board.hasMany(models.team_board_part_list);
        models.Team_board.belongsTo(models.team, {
            foreignKey: 'team_name'
        });
    };
    return team_board;
};