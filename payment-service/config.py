import os
from dotenv import load_dotenv

# Load environment variables from the .env file
load_dotenv()

class Config:
    DB_USER = os.getenv('DB_USER', 'root')
    DB_PASS = os.getenv('DB_PASSWORD', 'root')
    DB_HOST = os.getenv('DB_HOST', 'localhost')
    DB_NAME = os.getenv('DB_NAME', 'spms_payment_db')
    
    SQLALCHEMY_DATABASE_URI = f'mysql+pymysql://{DB_USER}:{DB_PASS}@{DB_HOST}/{DB_NAME}'
    SQLALCHEMY_TRACK_MODIFICATIONS = False