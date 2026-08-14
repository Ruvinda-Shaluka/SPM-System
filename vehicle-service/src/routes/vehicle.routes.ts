import { Router } from 'express';
import { registerVehicle, getVehicle, updateVehicleStatus } from '../controllers/vehicle.controller';

const router = Router();

router.post('/', registerVehicle);
router.get('/:id', getVehicle);
router.put('/:id/status', updateVehicleStatus);

export default router;