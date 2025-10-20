let sessionTimer;
let sessionWarningShown = false;

async function checkAuthentication() {
  try {
    const response = await fetch(`${baseUrl}/users/check`, {
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json'
      }
    });
    
    const data = await response.json();
    
    if (!data.success) {
      console.log('User not authenticated');
      return false;
    } else {
      updateUIForLoggedInUser(data.data);
      startSessionTimer();
      return true;
    }
  } catch (error) {
    console.error('Auth check error:', error);
    return false;
  }
}


function updateUIForLoggedInUser(userData) {
  const loginButton = document.getElementById('loginButton');
  if (loginButton && userData) {
    loginButton.innerHTML = `
      <a class="nav-link" href="#" onclick="logout(); return false;">
        <i class="fas fa-user"></i> ${userData.name} | <i class="fas fa-sign-out-alt"></i> Logout
      </a>
    `;
    
    sessionStorage.setItem('userName', userData.name);
    sessionStorage.setItem('userEmail', userData.email);
    sessionStorage.setItem('userPhone', userData.phone);
    sessionStorage.setItem('userId', userData.id);
  }
}


function startSessionTimer() {
  const SESSION_DURATION = 1800000; 
  const WARNING_TIME = 1620000; 
  
  if (sessionTimer) {
    clearTimeout(sessionTimer);
  }
  
  
  setTimeout(() => {
    if (!sessionWarningShown) {
      showSessionWarning();
      sessionWarningShown = true;
    }
  }, WARNING_TIME);
  
 
  
  sessionTimer = setTimeout(() => {
    handleSessionExpiry();
  }, SESSION_DURATION);
}



function showSessionWarning() {
  const warningDiv = document.createElement('div');
  warningDiv.id = 'session-warning';
  warningDiv.style.cssText = `
    position: fixed;
    top: 80px;
    right: 20px;
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
    color: white;
    padding: 20px 25px;
    border-radius: 12px;
    box-shadow: 0 8px 20px rgba(0,0,0,0.3);
    z-index: 9999;
    animation: slideIn 0.3s ease-out;
    min-width: 300px;
  `;
  warningDiv.innerHTML = `
    <div style="display: flex; align-items: center; gap: 15px;">
      <i class="fas fa-exclamation-triangle" style="font-size: 24px;"></i>
      <div>
        <strong style="font-size: 16px; display: block; margin-bottom: 5px;">Session Expiring Soon!</strong>
        <p style="margin: 0; font-size: 14px; opacity: 0.9;">Your session will expire in 3 minutes</p>
      </div>
      <button onclick="dismissWarning()" style="background: rgba(255,255,255,0.2); border: none; color: white; padding: 5px 10px; border-radius: 5px; cursor: pointer;">Dismiss</button>
    </div>
  `;
  document.body.appendChild(warningDiv);
}



function dismissWarning() {
  const warning = document.getElementById('session-warning');
  if (warning) {
    warning.remove();
  }
}


function handleSessionExpiry() {
  sessionWarningShown = false;
  
  const warning = document.getElementById('session-warning');
  if (warning) {
    warning.remove();
  }
  
  alert('⏰ Your session has expired due to inactivity. Please login again.');
  logout();
}


async function logout() {
  try {
    await fetch(`${baseUrl}/users/logout`, {
      method: 'POST',
      credentials: 'include'
    });
  } catch (error) {
    console.error('Logout error:', error);
  } finally {
    sessionStorage.clear();
    localStorage.removeItem('cart');
    
    if (sessionTimer) {
      clearTimeout(sessionTimer);
    }
    
    window.location.href = 'login.html';
  }
}


function resetSessionTimer() {
  sessionWarningShown = false;
  
  const warning = document.getElementById('session-warning');
  if (warning) {
    warning.remove();
  }
  
  startSessionTimer();
}

let activityTimeout;
['click', 'mousemove', 'keypress', 'scroll', 'touchstart'].forEach(event => {
  document.addEventListener(event, () => {
    if (sessionTimer) {
      clearTimeout(activityTimeout);
      activityTimeout = setTimeout(() => {
        resetSessionTimer();
      }, 500); 
    }
  });
});


const style = document.createElement('style');
style.textContent = `
  @keyframes slideIn {
    from {
      transform: translateX(100%);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }
`;
document.head.appendChild(style);


document.addEventListener('DOMContentLoaded', () => {
  const currentPage = window.location.pathname;
  if (!currentPage.includes('login.html') && !currentPage.includes('register.html')) {
    checkAuthentication();
  }
  
  updateCounter();
});