import express from 'express';
import vehicleRoutes from './routes/vehicle.routes';
import { eurekaClient, PORT } from './config/eureka.config';
import { sequelize } from './config/db.config';

const app = express();
app.use(express.json());

app.use('/vehicles', vehicleRoutes);

// Sync Database, then start server
sequelize.sync({ alter: true }).then(() => {
    console.log('MySQL Database connected and synced successfully!');
    
    app.listen(PORT, () => {
        console.log(`Vehicle Service running on port ${PORT}`);
        
        eurekaClient.start((error: any) => {
            console.log(error || 'Node.js Vehicle Service successfully registered with Eureka!');
        });
    });
}).catch((error) => {
    console.error('Unable to connect to the database:', error);
});