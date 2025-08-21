document.addEventListener("DOMContentLoaded", function () {
  // Price constants
  const ORIGINAL_PRICE = 100000.00;
  const DISCOUNT_AMOUNT = 5000.00;
  const DISCOUNTED_PRICE = ORIGINAL_PRICE - DISCOUNT_AMOUNT;

  // Price display elements
  const discountElement = document.querySelector(
    ".price-row:nth-child(2) span:last-child"
  );
  const totalElement = document.querySelector(".total-row span:last-child");
  const payButtonElement = document.querySelector(".pay-button");

  // Coupon code elements
  const clearButton = document.querySelector(".clear-coupon");
  const couponInput = document.getElementById("coupon");
  const successMessage = document.querySelector(".success-message");

  // Initially set pricing based on coupon
  updatePricing(couponInput.value === "MCA25");

  // Coupon code functionality
  clearButton.addEventListener("click", function () {
    couponInput.value = "";
    successMessage.style.display = "none";
    updatePricing(false);

    // Visual feedback on click
    clearButton.style.backgroundColor = "#e5e7eb";
    setTimeout(() => {
      clearButton.style.backgroundColor = "transparent";
    }, 300);
  });

  // Listen for changes to the coupon code input
  couponInput.addEventListener("input", function () {
    if (this.value.toUpperCase() === "MCA25") {
      successMessage.style.display = "flex";
      updatePricing(true);
    } else {
      successMessage.style.display = "none";
      updatePricing(false);
    }
  });

  // Function to update pricing based on coupon status
  function updatePricing(hasDiscount) {
    if (hasDiscount) {
      discountElement.textContent = `-₹${DISCOUNT_AMOUNT.toFixed(2)}`;
      totalElement.textContent = `₹${DISCOUNTED_PRICE.toFixed(2)}`;
      payButtonElement.innerHTML = `<i class="fas fa-lock"></i> Pay ₹${Math.round(
        DISCOUNTED_PRICE
      )}`;
    } else {
      discountElement.textContent = `₹0.00`;
      totalElement.textContent = `₹${ORIGINAL_PRICE.toFixed(2)}`;
      payButtonElement.innerHTML = `<i class="fas fa-lock"></i> Pay ₹${Math.round(
        ORIGINAL_PRICE
      )}`;
    }
  }

  // Input field focus effects
  const inputFields = document.querySelectorAll("input");
  inputFields.forEach((input) => {
    input.addEventListener("focus", function () {
      this.parentElement.classList.add("focused");
    });

    input.addEventListener("blur", function () {
      this.parentElement.classList.remove("focused");
    });
  });

  // Show more functionality with toggle
  const showMoreButton = document.querySelector(".show-more");
  const showMoreText = document.getElementById("show-more-text");
  const showMoreIcon = showMoreButton.querySelector("i");
  let expanded = false;

  showMoreButton.addEventListener("click", function () {
    expanded = !expanded;

    if (expanded) {
      showMoreText.textContent = "Show less";
      showMoreIcon.classList.remove("fa-chevron-down");
      showMoreIcon.classList.add("fa-chevron-up");

      // Create and show additional course features
      const additionalFeatures = [
        "All time support",
        "Only 200 seats available",
        "Cheapest rate"
        
      ];

      const courseFeatures = document.querySelector(".course-features");
      const showMoreElement = document.querySelector(".show-more");

      additionalFeatures.forEach((feature) => {
        const featureItem = document.createElement("div");
        featureItem.className = "feature-item additional-feature";
        featureItem.innerHTML = `
                  
                  <span>${feature}</span>
              `;
        courseFeatures.insertBefore(featureItem, showMoreElement);
      });

      // Animate the appearance of new features
      const newFeatures = document.querySelectorAll(".additional-feature");
      newFeatures.forEach((feature, index) => {
        feature.style.opacity = "0";
        feature.style.transform = "translateY(10px)";
        feature.style.transition = "all 0.3s ease";

        setTimeout(() => {
          feature.style.opacity = "1";
          feature.style.transform = "translateY(0)";
        }, 100 * index);
      });
    } else {
      showMoreText.textContent = "Show more";
      showMoreIcon.classList.remove("fa-chevron-up");
      showMoreIcon.classList.add("fa-chevron-down");

      // Remove additional features
      const additionalFeatures = document.querySelectorAll(
        ".additional-feature"
      );
      additionalFeatures.forEach((feature) => {
        feature.style.opacity = "0";
        feature.style.transform = "translateY(10px)";

        setTimeout(() => {
          feature.remove();
        }, 300);
      });
    }
  });

  // Pay button click handler with improved validation
  const payButton = document.querySelector(".pay-button");
  payButton.addEventListener("click", function () {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    const couponInput = document.getElementById("coupon"); // If you have a coupon input

    // Reset validation
    document.querySelectorAll(".form-group").forEach((group) => {
      group.classList.remove("error");
    });

    let hasError = false;
    if (!name) {
      document.getElementById("name").parentElement.classList.add("error");
      hasError = true;
    }
    if (!email || !isValidEmail(email)) {
      document.getElementById("email").parentElement.classList.add("error");
      hasError = true;
    }
    if (!phone || !isValidPhone(phone)) {
      document
        .getElementById("phone")
        .parentElement.parentElement.classList.add("error");
      hasError = true;
    }

    if (hasError) {
      shakeButton();
      alert("Please fill all required fields correctly.");
      return;
    }

    // Payment button state
    const currentPrice =
      couponInput.value.toUpperCase() === "MCA25"
        ? Math.round(DISCOUNTED_PRICE)
        : Math.round(ORIGINAL_PRICE);

    payButton.innerHTML =
      '<i class="fas fa-spinner fa-spin"></i> Processing...';
    payButton.disabled = true;

    // Create Razorpay order via backend
    fetch("http://localhost:8080/api/payment/create-order", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: name,
        email: email,
        phone: phone,
        courseName: "Best Mca online course",
        amount: currentPrice,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        const razorpayOrderId = data.id;

        const options = {
          key: "rzp_test_R6MkiyDOiye1ma",
          amount: currentPrice * 100,
          currency: "INR",
          name: "Check our MCA course",
          description: "Best Mca online course",
          image:
            "Online-MCA.jpg", // Add your logo here
          order_id: razorpayOrderId,
          theme: {
            color: "#46e576ff", // Match your button color
          },
          prefill: {
            name: name,
            email: email,
            contact: phone,
          },
          notes: {
            course: "Buy our products",
          },
          modal: {
            backdropclose: false,
            escape: false,
            ondismiss: function () {
              payButton.innerHTML = "Pay ₹" + currentPrice;
              payButton.disabled = false;
            },
          },
          handler: function (response) {
            // On success: update backend
            fetch("http://localhost:8080/api/payment/update-order", {
              method: "POST",
              headers: {
                "Content-Type": "application/x-www-form-urlencoded",
              },
              body: new URLSearchParams({
                paymentId: response.razorpay_payment_id,
                orderId: response.razorpay_order_id,
                status: "SUCCESS",
              }),
            })
              .then(() => {
                payButton.innerHTML =
                  '<i class="fas fa-check"></i> Payment Successful!';
                payButton.style.background =
                  "linear-gradient(to right, #2410b9ff, #547fdaff)";
                alert(
                  `🎉 Thanks for puchasing \nYour payment of ₹${currentPrice} was successful. A confirmation email has been sent.`
                );
              })
              .catch(() => {
                alert("Payment succeeded but failed to update backend.");
              });
          },
          theme: {
            color: "#0c9337ff",
          },
        };

        const rzp = new Razorpay(options);
        rzp.open();
      })
      .catch((err) => {
        console.error("Error:", err);
        alert("Something went wrong. Please try again.");
        payButton.disabled = false;
        payButton.innerHTML = "Pay Now";
      });
  });

  function isValidEmail(email) {
    return /\S+@\S+\.\S+/.test(email);
  }

  function isValidPhone(phone) {
    return /^\d{10}$/.test(phone);
  }

  function shakeButton() {
    payButton.classList.add("shake");
    setTimeout(() => payButton.classList.remove("shake"), 500);
  }

  // Helper functions
  function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }

  function isValidPhone(phone) {
    return /^\d{10}$/.test(phone);
  }

  function shakeButton() {
    payButton.classList.add("shake");
    setTimeout(() => {
      payButton.classList.remove("shake");
    }, 500);
  }

  // Add CSS for validation and animations
  const style = document.createElement("style");
  style.textContent = `
      .form-group.error input {
          border-color: #7d15f4ff;
          background-color: #fef2f2;
      }
      
      .form-group.focused input {
          border-color: #7d15f4ff;
          box-shadow: 0 0 0 3px rgba(23, 22, 26, 0.44);
      }
      
      .shake {
          animation: shake 0.5s cubic-bezier(.36,.07,.19,.97) both;
      }
      
      @keyframes shake {
          10%, 90% { transform: translate3d(-1px, 0, 0); }
          20%, 80% { transform: translate3d(2px, 0, 0); }
          30%, 50%, 70% { transform: translate3d(-4px, 0, 0); }
          40%, 60% { transform: translate3d(4px, 0, 0); }
      }
      
      .price-row span:last-child {
          transition: all 0.3s ease;
      }
  `;
  document.head.appendChild(style);
});
