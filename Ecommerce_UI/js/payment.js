// payment.js - Add this file to your js folder

const baseUrl = "http://localhost:8080";

async function initiatePayment() {
  const cart = JSON.parse(localStorage.getItem("cart")) || [];
  
  // Validation
  if (cart.length === 0) {
    alert('Your cart is empty!');
    return;
  }

  const userId = sessionStorage.getItem("userId");
  if (!userId) {
    alert('Please login to proceed with payment');
    window.location.href = 'login.html';
    return;
  }

  // Calculate total
  let totalAmount = 0;
  cart.forEach(item => {
    totalAmount += item.price * item.quantity;
  });

  // Get customer info
  const name = document.getElementById('customerName').value;
  const email = document.getElementById('customerEmail').value;
  const phone = document.getElementById('customerPhone').value;

  // Validation
  if (!name || !email || !phone) {
    alert('Please fill in all required fields');
    return;
  }

  if (!/^[0-9]{10}$/.test(phone)) {
    alert('Please enter a valid 10-digit phone number');
    return;
  }

  // Show loading
  document.getElementById('paymentForm').style.display = 'none';
  document.getElementById('loadingSpinner').style.display = 'block';

  try {
    // Create order on backend
    const paymentData = {
      name: name,
      email: email,
      phone: phone,
      description: `Cart Items (${cart.length} products)`,
      amount: totalAmount
    };

    console.log("Creating payment order...", paymentData);

    const response = await fetch(`${baseUrl}/api/payment/create-order`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(paymentData)
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const orderData = await response.json();
    console.log("Order created:", orderData);

    // Initialize Razorpay
    const options = {
      key: 'rzp_test_RThiWSoGmpw1VN', // Replace with your Razorpay key_id
      amount: totalAmount * 100, // Amount in paise
      currency: "INR",
      name: "Sasta Bazar",
      description: `Cart Items (${cart.length} products)`,
      order_id: orderData.id,
      handler: async function (response) {
        console.log("Payment successful:", response);
        
        try {
          // Update payment status on backend
          const updateResponse = await fetch(`${baseUrl}/api/payment/update-order`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
              paymentId: response.razorpay_payment_id,
              orderId: response.razorpay_order_id,
              status: 'SUCCESS'
            })
          });

          if (updateResponse.ok) {
            console.log("Payment status updated");
          }

          // Clear cart
          localStorage.removeItem('cart');

          // Show success message
          alert(`✓ Payment Successful!\nPayment ID: ${response.razorpay_payment_id}\nA confirmation email has been sent to ${email}`);

          // Redirect to home
          closePaymentModal();
          window.location.href = 'index.html';
        } catch (error) {
          console.error('Error updating payment status:', error);
          alert('Payment received but could not update status. Please contact support.');
        }
      },
      prefill: {
        name: name,
        email: email,
        contact: phone
      },
      theme: {
        color: "#667eea"
      },
      modal: {
        ondismiss: function() {
          document.getElementById('paymentForm').style.display = 'block';
          document.getElementById('loadingSpinner').style.display = 'none';
        }
      }
    };

    const razorpay = new Razorpay(options);
    razorpay.open();

    // Hide loading
    document.getElementById('paymentForm').style.display = 'block';
    document.getElementById('loadingSpinner').style.display = 'none';

  } catch (error) {
    console.error('Payment error:', error);
    alert('Error processing payment. Please try again.');
    document.getElementById('paymentForm').style.display = 'block';
    document.getElementById('loadingSpinner').style.display = 'none';
  }
}

// Auto-fill customer info from session storage
document.addEventListener('DOMContentLoaded', function() {
  const name = sessionStorage.getItem('userName');
  const email = sessionStorage.getItem('userEmail');
  const phone = sessionStorage.getItem('userPhone');

  if (name) document.getElementById('customerName').value = name;
  if (email) document.getElementById('customerEmail').value = email;
  if (phone) document.getElementById('customerPhone').value = phone;

  console.log('Payment form pre-filled with user data');
});