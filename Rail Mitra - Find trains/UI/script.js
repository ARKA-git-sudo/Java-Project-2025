
const API_BASE_URL = 'http://localhost:8080';

const elements = {
    searchForm: document.getElementById('searchForm'),
    sourceCode: document.getElementById('sourceCode'),
    destinationCode: document.getElementById('destinationCode'),
    sourceName: document.getElementById('sourceName'),
    destinationName: document.getElementById('destinationName'),
    searchBtn: document.getElementById('searchBtn'),
    loading: document.getElementById('loading'),
    error: document.getElementById('error'),
    results: document.getElementById('results'),
    resultsCount: document.getElementById('resultsCount'),
    trainsList: document.getElementById('trainsList'),
    noResults: document.getElementById('noResults'),
    codeTab: document.getElementById('codeTab'),
    nameTab: document.getElementById('nameTab'),
    codeSearch: document.getElementById('codeSearch'),
    nameSearch: document.getElementById('nameSearch')
};

let currentSearchMode = 'code';

// Tab switching functionality
elements.codeTab.addEventListener('click', () => {
    switchTab('code');
});

elements.nameTab.addEventListener('click', () => {
    switchTab('name');
});

function switchTab(mode) {
    currentSearchMode = mode;

    // Update active tab
    elements.codeTab.classList.toggle('active', mode === 'code');
    elements.nameTab.classList.toggle('active', mode === 'name');

    // Show/hide search modes
    elements.codeSearch.style.display = mode === 'code' ? 'flex' : 'none';
    elements.nameSearch.style.display = mode === 'name' ? 'flex' : 'none';

    // Update form validation
    updateFormValidation();
}

function updateFormValidation() {
    const codeInputs = [elements.sourceCode, elements.destinationCode];
    const nameInputs = [elements.sourceName, elements.destinationName];

    if (currentSearchMode === 'code') {
        codeInputs.forEach(input => input.required = true);
        nameInputs.forEach(input => input.required = false);
    } else {
        codeInputs.forEach(input => input.required = false);
        nameInputs.forEach(input => input.required = true);
    }
}

// Popular routes functionality
document.querySelectorAll('.route-chip').forEach(chip => {
    chip.addEventListener('click', () => {
        const source = chip.dataset.source;
        const dest = chip.dataset.dest;

        // Switch to code search and populate
        switchTab('code');
        elements.sourceCode.value = source;
        elements.destinationCode.value = dest;

        // Trigger search
        searchTrains(source, dest);
    });
});

function showLoading() {
    hideAllSections();
    elements.loading.style.display = 'block';
    elements.searchBtn.disabled = true;
    elements.searchBtn.textContent = 'Searching...';
}

function hideLoading() {
    elements.loading.style.display = 'none';
    elements.searchBtn.disabled = false;
    elements.searchBtn.textContent = 'Search Trains';
}

function hideAllSections() {
    elements.loading.style.display = 'none';
    elements.error.style.display = 'none';
    elements.results.style.display = 'none';
    elements.noResults.style.display = 'none';
}

function showError(message) {
    hideAllSections();
    elements.error.textContent = message;
    elements.error.style.display = 'block';
}

function showResults(trains) {
    hideAllSections();

    if (trains.length === 0) {
        elements.noResults.style.display = 'block';
        return;
    }

    elements.resultsCount.textContent = `${trains.length} train${trains.length > 1 ? 's' : ''} found`;
    elements.trainsList.innerHTML = trains.map(train => createTrainCard(train)).join('');
    elements.results.style.display = 'block';
}

function calculateDuration(departureTime, arrivalTime) {
    const [depHour, depMin] = departureTime.split(':').map(Number);
    const [arrHour, arrMin] = arrivalTime.split(':').map(Number);

    let depMinutes = depHour * 60 + depMin;
    let arrMinutes = arrHour * 60 + arrMin;

    // Handle next day arrival
    if (arrMinutes < depMinutes) {
        arrMinutes += 24 * 60;
    }

    const durationMinutes = arrMinutes - depMinutes;
    const hours = Math.floor(durationMinutes / 60);
    const minutes = durationMinutes % 60;

    return `${hours}h ${minutes}m`;
}

function createTrainCard(train) {
    const duration = calculateDuration(train.departureTime, train.arrivalTime);
    return `
                <div class="train-card">
                    <div class="train-header">
                        <div class="train-info">
                            <div class="train-name">${train.train.trainName}</div>
                            <div class="train-number">${train.train.trainNo}</div>
                        </div>
                        <div class="duration-info">
                            <div class="duration">${duration}</div>
                            <div class="duration-label">Duration</div>
                        </div>
                    </div>
                    <div class="route-info">
                        <div class="station-info source">
                            <div class="station-name">${train.source.stationName}</div>
                            <div class="station-code">${train.source.stationCode}</div>
                            <div class="time">${formatTime(train.departureTime)}</div>
                        </div>
                        <div class="route-arrow">
                            <div class="arrow-line"></div>
                        </div>
                        <div class="station-info destination">
                            <div class="station-name">${train.destination.stationName}</div>
                            <div class="station-code">${train.destination.stationCode}</div>
                            <div class="time">${formatTime(train.arrivalTime)}</div>
                        </div>
                    </div>
                </div>
            `;
}

function formatTime(time) {
    // Convert 24-hour format to 12-hour format with AM/PM
    const [hours, minutes] = time.split(':');
    const hour = parseInt(hours);
    const ampm = hour >= 12 ? 'PM' : 'AM';
    const displayHour = hour % 12 || 12;
    return `${displayHour}:${minutes} ${ampm}`;
}

async function searchTrains(sourceCode, destinationCode, sourceName, destinationName) {
    try {
        showLoading();

        let url;
        if (currentSearchMode === 'code') {
            url = `${API_BASE_URL}/search/by-code?sourceCode=${encodeURIComponent(sourceCode)}&destinationCode=${encodeURIComponent(destinationCode)}`;
        } else {
            url = `${API_BASE_URL}/search/by-name?sourceName=${encodeURIComponent(sourceName)}&destinationName=${encodeURIComponent(destinationName)}`;
        }

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const trains = await response.json();
        showResults(trains);

    } catch (error) {
        console.error('Search error:', error);
        showError(`Failed to search trains. Please check if the API server is running and try again.`);
    } finally {
        hideLoading();
    }
}

elements.searchForm.addEventListener('submit', (e) => {
    e.preventDefault();

    if (currentSearchMode === 'code') {
        const sourceCode = elements.sourceCode.value.trim().toUpperCase();
        const destinationCode = elements.destinationCode.value.trim().toUpperCase();

        if (!sourceCode || !destinationCode) {
            showError('Please enter both source and destination station codes');
            return;
        }

        if (sourceCode === destinationCode) {
            showError('Source and destination stations cannot be the same');
            return;
        }

        searchTrains(sourceCode, destinationCode);
    } else {
        const sourceName = elements.sourceName.value.trim();
        const destinationName = elements.destinationName.value.trim();

        if (!sourceName || !destinationName) {
            showError('Please enter both source and destination station names');
            return;
        }

        if (sourceName.toLowerCase() === destinationName.toLowerCase()) {
            showError('Source and destination stations cannot be the same');
            return;
        }

        searchTrains(null, null, sourceName, destinationName);
    }
});

// Auto-uppercase station codes as user types
elements.sourceCode.addEventListener('input', (e) => {
    e.target.value = e.target.value.toUpperCase();
});

elements.destinationCode.addEventListener('input', (e) => {
    e.target.value = e.target.value.toUpperCase();
});

// Initialize form validation
updateFormValidation();

// Pre-fill with example data for testing
elements.sourceCode.value = 'HWR';
elements.destinationCode.value = 'CSMT';
