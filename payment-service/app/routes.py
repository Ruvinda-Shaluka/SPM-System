from flask import Blueprint, request, jsonify
from app import db
from app.models import Payment
import uuid
from datetime import datetime

payment_bp = Blueprint('payments', __name__)

@payment_bp.route('/payments', methods=['POST'])
def process_payment():
    data = request.get_json()
    if not data:
        return jsonify({'error': 'Request body must be JSON'}), 400

    user_id = data.get('user_id')
    amount = data.get('amount')

    if not user_id:
        return jsonify({'error': 'Field "user_id" is required'}), 400
    
    if amount is None or not isinstance(amount, (int, float)) or amount <= 0:
        return jsonify({'error': 'Field "amount" must be a positive number'}), 400

    booking_id = data.get('booking_id')
    space_id = data.get('space_id')
    currency = data.get('currency', 'USD')
    payment_method = data.get('payment_method', 'CARD')
    description = data.get('description', 'Parking reservation payment')
    
    # Generate unique receipt number: RCP-YYYYMMDD-XXXX
    date_str = datetime.utcnow().strftime('%Y%m%d')
    unique_suffix = uuid.uuid4().hex[:6].upper()
    receipt_number = f"RCP-{date_str}-{unique_suffix}"

    new_payment = Payment(
        user_id=str(user_id),
        booking_id=str(booking_id) if booking_id else None,
        space_id=str(space_id) if space_id else None,
        amount=float(amount),
        currency=currency.upper(),
        payment_method=payment_method.upper(),
        status='SUCCESS',
        receipt_number=receipt_number,
        description=description,
        created_at=datetime.utcnow()
    )

    try:
        db.session.add(new_payment)
        db.session.commit()
        return jsonify(new_payment.to_dict()), 201
    except Exception as e:
        db.session.rollback()
        return jsonify({'error': f'Failed to process payment: {str(e)}'}), 500

@payment_bp.route('/payments', methods=['GET'])
def get_all_payments():
    payments = Payment.query.order_by(Payment.created_at.desc()).all()
    return jsonify([p.to_dict() for p in payments]), 200

@payment_bp.route('/payments/<int:payment_id>', methods=['GET'])
def get_payment(payment_id):
    payment = db.session.get(Payment, payment_id)
    if not payment:
        return jsonify({'error': f'Payment with id {payment_id} not found'}), 404
    return jsonify(payment.to_dict()), 200

@payment_bp.route('/payments/<int:payment_id>/receipt', methods=['GET'])
def get_payment_receipt(payment_id):
    payment = db.session.get(Payment, payment_id)
    if not payment:
        return jsonify({'error': f'Payment with id {payment_id} not found'}), 404
    return jsonify(payment.generate_receipt()), 200

@payment_bp.route('/payments/user/<user_id>', methods=['GET'])
def get_user_payments(user_id):
    payments = Payment.query.filter_by(user_id=str(user_id)).order_by(Payment.created_at.desc()).all()
    return jsonify([p.to_dict() for p in payments]), 200

@payment_bp.route('/payments/booking/<booking_id>', methods=['GET'])
def get_booking_payment(booking_id):
    payment = Payment.query.filter_by(booking_id=str(booking_id)).first()
    if not payment:
        return jsonify({'error': f'No payment found for booking {booking_id}'}), 404
    return jsonify(payment.to_dict()), 200

@payment_bp.route('/payments/<int:payment_id>/refund', methods=['PUT'])
def refund_payment(payment_id):
    payment = db.session.get(Payment, payment_id)
    if not payment:
        return jsonify({'error': f'Payment with id {payment_id} not found'}), 404

    if payment.status == 'REFUNDED':
        return jsonify({'error': 'Payment has already been refunded'}), 400

    payment.status = 'REFUNDED'
    try:
        db.session.commit()
        return jsonify({'message': 'Payment refunded successfully', 'payment': payment.to_dict()}), 200
    except Exception as e:
        db.session.rollback()
        return jsonify({'error': f'Failed to refund payment: {str(e)}'}), 500