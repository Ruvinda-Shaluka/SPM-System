from app import create_app, db
import py_eureka_client.eureka_client as eureka_client

# Create the configured Flask app
app = create_app()
PORT = 8084

if __name__ == '__main__':
    # Generate the database tables if they don't exist
    with app.app_context():
        db.create_all()
        print("MySQL Database connected and synced successfully!")

    # Register with Spring Cloud Eureka
    eureka_client.init(
        eureka_server="http://localhost:8761/eureka",
        app_name="payment-service",
        instance_port=PORT
    )
    
    # Start the Flask server
    app.run(port=PORT)