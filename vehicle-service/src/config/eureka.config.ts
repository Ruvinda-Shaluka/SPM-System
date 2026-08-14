// @ts-ignore - Ignoring types because eureka-js-client does not have official TS definitions
import { Eureka } from 'eureka-js-client';

export const PORT = 8083;

export const eurekaClient = new Eureka({
    instance: {
        app: 'vehicle-service',
        hostName: 'localhost',
        ipAddr: '127.0.0.1',
        statusPageUrl: `http://localhost:${PORT}/info`,
        port: {
            '$': PORT,
            '@enabled': 'true',
        },
        vipAddress: 'vehicle-service',
        dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },
    },
    eureka: {
        host: 'localhost',
        port: 8761,
        servicePath: '/eureka/apps/',
    },
});