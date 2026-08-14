from flask import Flask, jsonify
from flask_sqlalchemy import SQLAlchemy
from config import Config

db = SQLAlchemy()

def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)
    
    db.init_app(app)
    
    # Health check endpoints for Eureka and Gateway
    @app.route('/health')
    def health():
        return jsonify({'status': 'UP', 'service': 'payment-service'})

    @app.route('/info')
    def info():
        return jsonify({'service': 'payment-service', 'version': '1.0.0'})

    from app.routes import payment_bp
    app.register_blueprint(payment_bp)
    
    return app