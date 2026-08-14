import { DataTypes, Model } from 'sequelize';
import { sequelize } from '../config/db.config';

export class Vehicle extends Model {
    public id!: string;
    public licensePlate!: string;
    public make!: string;
    public model!: string;
    public ownerId!: string;
    public status!: 'IN' | 'OUT';
}

// Initialize the model schema
Vehicle.init({
    id: { 
        type: DataTypes.STRING, 
        primaryKey: true 
    },
    licensePlate: { 
        type: DataTypes.STRING, 
        allowNull: false 
    },
    make: { 
        type: DataTypes.STRING, 
        allowNull: false 
    },
    model: { 
        type: DataTypes.STRING, 
        allowNull: false 
    },
    ownerId: { 
        type: DataTypes.STRING, 
        allowNull: false 
    },
    status: { 
        type: DataTypes.ENUM('IN', 'OUT'), 
        defaultValue: 'OUT' 
    }
}, {
    sequelize,
    tableName: 'vehicles',
    timestamps: true // Automatically adds createdAt and updatedAt columns
});