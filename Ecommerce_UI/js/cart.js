let cart = JSON.parse(localStorage.getItem("cart")) || [];

// Check authentication before allowing cart access
function checkCartAuthentication() {
  const userId = sessionStorage.getItem("userId");
  const userName = sessionStorage.getItem("userName");
  
  if (!userId || !userName) {
    // User not logged in - redirect to login
    alert('Please login first to access your cart');
    window.location.href = 'login.html';
    return false;
  }
  return true;
}

function addToCart(id, name, price, imageUrl) {
  const userId = sessionStorage.getItem("userId");
  const userName = sessionStorage.getItem("userName");
  
  if (!userId || !userName) {
    alert('Please login to add items to cart');
    window.location.href = 'login.html';
    return;
  }
  
  console.log("Adding product to cart:", id, name, price, imageUrl);
  price = parseFloat(price);

  let itemIndex = cart.findIndex((item) => item.id === id);

  if (itemIndex !== -1) {
    cart[itemIndex].quantity += 1;
  } else {
    cart.push({ id, name, price, imageUrl, quantity: 1 });
  }

  localStorage.setItem("cart", JSON.stringify(cart));
  updateCounter();
  
  alert(`✓ Product added to cart!\nTotal items: ${cart.length}`);
}

function loadCart() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let cartItems = document.getElementById("cart-items");
  
  if (!cartItems) {
    console.log("Not on cart page, skipping loadCart");
    return;
  }
  
  let totalAmount = 0;
  cartItems.innerHTML = "";

  if (cart.length === 0) {
    updateCartVisibility();
    return;
  }

  cart.forEach((items, index) => {
    let itemTotal = items.price * items.quantity;
    totalAmount += itemTotal;

    cartItems.innerHTML += `
      <tr>
        <td><img src="${items.imageUrl}" class="product-img" alt="${items.name}"></td>
        <td>${items.name}</td>
        <td>₹${items.price.toFixed(2)}</td>
        <td>
          <div class="qty-controls">
            <button class="qty-btn" onclick="changeQuantity(${index},-1)">-</button>
            <span style="padding: 0 15px; font-weight: bold;">${items.quantity}</span>
            <button class="qty-btn" onclick="changeQuantity(${index},1)">+</button>
          </div>
        </td>
        <td>₹${itemTotal.toFixed(2)}</td>
        <td>
          <button class="remove-btn" onclick="removeItem(${index})">
            <i class="fas fa-trash"></i> Remove
          </button>
        </td>
      </tr>`;
  });

  const totalAmountElement = document.getElementById("total-amount");
  const finalTotalElement = document.getElementById("final-total");
  
  if (totalAmountElement) {
    totalAmountElement.innerText = totalAmount.toFixed(2);
  }
  if (finalTotalElement) {
    finalTotalElement.innerText = totalAmount.toFixed(2);
  }
  
  updateCartVisibility();
}

function updateCounter() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  const cartBadges = document.querySelectorAll(".cart-badge");
  
  cartBadges.forEach(badge => {
    badge.innerText = cart.length;
  });
  
  console.log("Cart counter updated:", cart.length);
}

function changeQuantity(index, change) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  cart[index].quantity += change;
  
  if (cart[index].quantity <= 0) {
    cart.splice(index, 1);
  }

  localStorage.setItem("cart", JSON.stringify(cart));
  loadCart();
  updateCounter();
}

function removeItem(index) {
  if (confirm('Remove this item from cart?')) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    cart.splice(index, 1);
    localStorage.setItem("cart", JSON.stringify(cart));
    loadCart();
    updateCounter();
  }
}

function clearCart() {
  if (confirm('Clear entire cart?')) {
    localStorage.removeItem("cart");
    cart = [];
    loadCart();
    updateCounter();
    updateCartVisibility();
  }
}

function clearCartAndRefresh() {
  clearCart();
}

function updateCartVisibility() {
  const cart = JSON.parse(localStorage.getItem("cart")) || [];
  const emptyMessage = document.getElementById('emptyCartMessage');
  const cartTable = document.getElementById('cartTableSection');
  const cartSummary = document.getElementById('cartSummarySection');

  if (!emptyMessage || !cartTable || !cartSummary) {
    return; 
  }

  if (cart.length === 0) {
    emptyMessage.style.display = 'block';
    cartTable.style.display = 'none';
    cartSummary.style.display = 'none';
  } else {
    emptyMessage.style.display = 'none';
    cartTable.style.display = 'block';
    cartSummary.style.display = 'block';
  }
}

// Logout function for cart page
async function logoutFromCart() {
  const baseUrl = "http://localhost:8080";
  try {
    await fetch(`${baseUrl}/users/logout`, {
      method: 'POST',
      credentials: 'include'
    });
  } catch (error) {
    console.error('Logout error:', error);
  } finally {
    sessionStorage.clear();
    localStorage.removeItem("cart");
    alert('✓ Logged out successfully!');
    window.location.href = 'login.html';
  }
}

// Update navbar with user info and logout option in cart.html
function updateCartPageNavbar() {
  const loginButton = document.getElementById('loginButton');
  const userId = sessionStorage.getItem('userId');
  const userName = sessionStorage.getItem('userName');
  
  if (loginButton && userId && userName) {
    loginButton.innerHTML = `
      <a class="nav-link" href="#" onclick="logoutFromCart(); return false;">
        <i class="fas fa-user"></i> ${userName} | <i class="fas fa-sign-out-alt"></i> Logout
      </a>
    `;
  }
}

document.addEventListener("DOMContentLoaded", function() {
  // Check if user is authenticated before loading cart page
  if (window.location.pathname.includes('cart.html')) {
    if (!checkCartAuthentication()) {
      return; // Stop execution if not authenticated
    }
    updateCartPageNavbar();
  }
  
  loadCart();
  updateCounter();
  updateCartVisibility();
  console.log("✓ Cart initialized");
});