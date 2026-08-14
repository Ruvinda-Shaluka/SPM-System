from app import create_app, db
import py_eureka_client.eureka_client as eureka_client
import os

app = create_app()
PORT = int(os.getenv('PORT', 8084))
EUREKA_SERVER = os.getenv('EUREKA_SERVER_URL', 'http://localhost:8761/eureka')

if __name__ == '__main__':
    with app.app_context():
        try:
            db.create_all()
            print("MySQL Database connected and synced successfully for payment-service!")
        except Exception as e:
            print(f"Warning: Database sync failed (check MySQL connection): {e}")

    try:
        eureka_client.init(
            eureka_server=EUREKA_SERVER,
            app_name="payment-service",
            instance_port=PORT
        )
        print(f"Payment service registered with Eureka at {EUREKA_SERVER}")
    except Exception as e:
        print(f"Warning: Could not register with Eureka: {e}")

    app.run(host='0.0.0.0', port=PORT)