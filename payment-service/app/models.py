from app import db
from datetime import datetime
import uuid

class Payment(db.Model):
    __tablename__ = 'payments'
    
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    user_id = db.Column(db.String(50), nullable=False)
    booking_id = db.Column(db.String(50), nullable=True)
    space_id = db.Column(db.String(50), nullable=True)
    amount = db.Column(db.Float, nullable=False)
    currency = db.Column(db.String(10), default='USD')
    payment_method = db.Column(db.String(50), default='CARD') # 'CARD', 'CASH', 'WALLET', 'APPLE_PAY'
    status = db.Column(db.String(20), default='SUCCESS') # 'SUCCESS', 'PENDING', 'FAILED', 'REFUNDED'
    receipt_number = db.Column(db.String(100), unique=True, nullable=False)
    description = db.Column(db.String(255), nullable=True)
    created_at = db.Column(db.DateTime, default=datetime.utcnow)

    def to_dict(self):
        return {
            'id': self.id,
            'user_id': self.user_id,
            'booking_id': self.booking_id,
            'space_id': self.space_id,
            'amount': self.amount,
            'currency': self.currency,
            'payment_method': self.payment_method,
            'status': self.status,
            'receipt_number': self.receipt_number,
            'description': self.description,
            'created_at': self.created_at.isoformat() if self.created_at else None,
        }

    def generate_receipt(self):
        return {
            'receipt_number': self.receipt_number,
            'payment_id': self.id,
            'user_id': self.user_id,
            'booking_id': self.booking_id,
            'space_id': self.space_id,
            'amount_paid': f"{self.currency} {self.amount:.2f}",
            'payment_method': self.payment_method,
            'status': self.status,
            'issued_at': self.created_at.strftime('%Y-%m-%d %H:%M:%S') if self.created_at else None,
            'notes': self.description or 'Thank you for using Smart Parking Management System (SPMS).'
        }