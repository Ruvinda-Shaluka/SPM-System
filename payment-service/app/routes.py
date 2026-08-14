from flask import Blueprint, request, jsonify
from app import db
from app.models import Payment

# Create a Blueprint named 'payments'
payment_bp = Blueprint('payments', __name__)

@payment_bp.route('/payments', methods=['POST'])
def process_payment():
    data = request.json
    new_payment = Payment(user_id=data['user_id'], amount=data['amount'])
    
    db.session.add(new_payment)
    db.session.commit()
    
    return jsonify({
        'id': new_payment.id, 
        'user_id': new_payment.user_id, 
        'amount': new_payment.amount, 
        'status': new_payment.status
    }), 201

@payment_bp.route('/payments/<int:payment_id>', methods=['GET'])
def get_payment(payment_id):
    payment = Payment.query.get_or_404(payment_id)
    return jsonify({
        'id': payment.id, 
        'user_id': payment.user_id, 
        'amount': payment.amount, 
        'status': payment.status
    })