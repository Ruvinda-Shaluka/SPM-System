import { Sequelize } from 'sequelize';
import dotenv from 'dotenv';

// Load the environment variables from the .env file
dotenv.config();

// Initialize Sequelize using process.env
export const sequelize = new Sequelize(
    process.env.DB_NAME || 'spms_vehicle_db', 
    process.env.DB_USER || 'root',            
    process.env.DB_PASSWORD || 'root',        
    {
        host: process.env.DB_HOST || 'localhost',
        dialect: 'mysql',
        logging: false, 
    }
);