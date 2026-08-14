import express, { Request, Response, NextFunction } from 'express';
import cors from 'cors';
import vehicleRoutes from './routes/vehicle.routes';
import { eurekaClient, PORT } from './config/eureka.config';
import { sequelize } from './config/db.config';

const app = express();

app.use(cors());
app.use(express.json());

// Health & Info Endpoint for Eureka / Gateway
app.get('/info', (_req: Request, res: Response) => {
    res.json({ status: 'UP', service: 'vehicle-service', timestamp: new Date() });
});

app.get('/health', (_req: Request, res: Response) => {
    res.json({ status: 'UP' });
});

// Main Vehicle Routes
app.use('/vehicles', vehicleRoutes);

// Global Error Handler
app.use((err: any, _req: Request, res: Response, _next: NextFunction) => {
    console.error('Unhandled error in vehicle-service:', err);
    res.status(500).json({ error: err.message || 'Internal Server Error' });
});

// Sync Database and start server
sequelize.sync({ alter: true }).then(() => {
    console.log('MySQL Database connected and synced successfully for vehicle-service!');
    
    app.listen(PORT, () => {
        console.log(`Vehicle Service running on port ${PORT}`);
        
        eurekaClient.start((error: any) => {
            if (error) {
                console.warn('Eureka registration error (Eureka might be offline):', error.message || error);
            } else {
                console.log('Node.js Vehicle Service successfully registered with Eureka!');
            }
        });
    });
}).catch((error) => {
    console.error('Unable to connect to the database:', error);
});