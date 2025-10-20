// api.js - Complete working version with database search

const baseUrl = "http://localhost:8080";
let allProducts = []; // Store all products for reference

async function loadProduct() {
  console.log("Loading products from database...");
  
  try {
    const response = await fetch(`${baseUrl}/products`);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const products = await response.json();
    console.log("✅ Products loaded:", products.length);

    // Store products globally
    allProducts = products;

    displayProducts(products);

  } catch (error) {
    console.error("❌ Error loading products:", error);
    showError("Error loading products. Please refresh the page.");
  }
}

function displayProducts(products) {
  let trendingList = document.getElementById("trending-products");
  let clothingList = document.getElementById("clothing-products");
  let electronicsList = document.getElementById("electronics-products");

  // Check if elements exist
  if (!trendingList || !clothingList || !electronicsList) {
    console.error("❌ Product container elements not found!");
    return;
  }

  // Clear existing content
  trendingList.innerHTML = "";
  clothingList.innerHTML = "";
  electronicsList.innerHTML = "";

  // Check if we have products
  if (products.length === 0) {
    console.warn("⚠️ No products found");
    trendingList.innerHTML = '<div class="col-12"><div class="alert alert-info">No products available</div></div>';
    return;
  }

  // Render products
  products.forEach((product) => {
    let productCard = createProductCard(product);

    // Categorize products
    if (product.category === "Clothing") {
      clothingList.innerHTML += productCard;
    } else if (product.category === "Electronics") {
      electronicsList.innerHTML += productCard;
    } else if (product.category === "Gadgets") {
      trendingList.innerHTML += productCard;
    } else {
      trendingList.innerHTML += productCard;
    }
  });

  console.log("✅ Products displayed successfully!");
}

function createProductCard(product) {
  return `
    <div class="col-lg-4 col-md-6 mb-4">
      <div class="card h-100">
        <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}" style="height: 250px; object-fit: cover;">
        <div class="card-body d-flex flex-column">
          <h5 class="card-title">${product.name}</h5>
          <p class="card-text">${product.description}</p>
          <p class="price"><strong>₹${product.price.toLocaleString()}</strong></p>
          <span class="badge bg-info mb-2">${product.category}</span>
          <button class="btn btn-primary mt-auto" onclick="addToCart(${product.id}, '${product.name.replace(/'/g, "\\'")}', ${product.price}, '${product.imageUrl}')">
            <i class="fas fa-shopping-cart"></i> Add to cart
          </button>
        </div>
      </div>
    </div>
  `;
}

// Search functionality - NOW USES DATABASE
async function searchProducts() {
  const searchInput = document.getElementById('searchInput');
  const searchBtn = document.getElementById('searchBtn');
  const searchTerm = searchInput.value.trim();
  
  if (!searchTerm) {
    alert('⚠️ Please enter a search term');
    return;
  }
  
  console.log("🔍 Searching database for:", searchTerm);
  
  // Show loading state
  const originalBtnText = searchBtn.innerHTML;
  searchBtn.disabled = true;
  searchBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Searching...';
  
  try {
    // Call backend search API
    const response = await fetch(`${baseUrl}/products/search?q=${encodeURIComponent(searchTerm)}`);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const products = await response.json();
    console.log("✅ Found", products.length, "products from database");
    
    // Display search results
    displaySearchResults(products, searchTerm);
    
  } catch (error) {
    console.error("❌ Search error:", error);
    alert('Search failed. Please try again.');
  } finally {
    // Restore button
    searchBtn.disabled = false;
    searchBtn.innerHTML = originalBtnText;
  }
}

function displaySearchResults(products, searchTerm) {
  console.log("📊 Displaying search results for:", searchTerm);
  
  const searchModal = document.getElementById('searchModal');
  const searchProductsDiv = document.getElementById('search-products');
  const searchTermSpan = document.getElementById('searchTerm');
  const resultCount = document.getElementById('resultCount');
  
  // Check if elements exist
  if (!searchModal || !searchProductsDiv || !searchTermSpan) {
    console.error("❌ Search modal elements not found!");
    alert("Error displaying results. Please refresh the page.");
    return;
  }
  
  // Update search term and count
  searchTermSpan.textContent = searchTerm;
  resultCount.textContent = products.length;
  
  // Clear previous results
  searchProductsDiv.innerHTML = '';
  
  if (products.length === 0) {
    searchProductsDiv.innerHTML = `
      <div class="col-12 no-results">
        <i class="fas fa-search"></i>
        <h3>No products found</h3>
        <p class="text-muted">We couldn't find any products matching "${searchTerm}"</p>
        <p class="text-muted">Try searching with different keywords</p>
      </div>
    `;
  } else {
    // Display filtered products
    products.forEach(product => {
      searchProductsDiv.innerHTML += createProductCard(product);
    });
  }
  
  // Show modal with animation
  searchModal.style.display = 'block';
  document.body.style.overflow = 'hidden'; // Prevent background scroll
  
  console.log("✅ Search modal opened with", products.length, "products");
}

function closeSearchModal() {
  const searchModal = document.getElementById('searchModal');
  const searchInput = document.getElementById('searchInput');
  
  if (searchModal) {
    searchModal.style.display = 'none';
    document.body.style.overflow = 'auto'; // Restore scroll
  }
  
  if (searchInput) {
    searchInput.value = ''; // Clear search input
  }
  
  console.log("✅ Search modal closed");
}

// Close modal when clicking outside
document.addEventListener('DOMContentLoaded', function() {
  const searchModal = document.getElementById('searchModal');
  if (searchModal) {
    // Click outside to close
    searchModal.addEventListener('click', function(e) {
      if (e.target === searchModal) {
        closeSearchModal();
      }
    });
  }
  
  // ESC key to close modal
  document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
      const modal = document.getElementById('searchModal');
      if (modal && modal.style.display === 'block') {
        closeSearchModal();
      }
    }
  });
});

function showError(message) {
  const trendingList = document.getElementById("trending-products");
  if (trendingList) {
    trendingList.innerHTML = `
      <div class="col-12">
        <div class="alert alert-danger" role="alert">
          <i class="fas fa-exclamation-triangle"></i> ${message}
        </div>
      </div>
    `;
  }
}

// Search on Enter key
document.addEventListener('DOMContentLoaded', function() {
  console.log("🚀 API.js initialized");
  
  const searchInput = document.getElementById('searchInput');
  if (searchInput) {
    searchInput.addEventListener('keypress', function(e) {
      if (e.key === 'Enter') {
        e.preventDefault();
        searchProducts();
      }
    });
    console.log("✅ Search Enter key listener attached");
  }
});