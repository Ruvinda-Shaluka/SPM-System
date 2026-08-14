from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from config import Config

# Create the database instance (un-configured for now)
db = SQLAlchemy()

def create_app():
    # Initialize Flask
    app = Flask(__name__)
    
    # Apply configurations
    app.config.from_object(Config)
    
    # Initialize the database with the app
    db.init_app(app)
    
    # Register the routes (Blueprints)
    # We import this down here to prevent circular dependency errors
    from app.routes import payment_bp
    app.register_blueprint(payment_bp)
    
    return app