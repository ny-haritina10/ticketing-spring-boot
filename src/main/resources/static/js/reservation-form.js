document.addEventListener('DOMContentLoaded', function() {
  // Validation for the main reservation form
  const mainForm = document.querySelector('#reservationForm');
  if (mainForm) {
      mainForm.addEventListener('submit', function(event) {
          const totalTickets = parseInt(document.getElementById('totalTickets').value);
          const adultTickets = parseInt(document.getElementById('adultTickets').value);
          const childTickets = parseInt(document.getElementById('childTickets').value);
          
          if (totalTickets !== (adultTickets + childTickets)) {
              event.preventDefault();
              const errorMessage = document.getElementById('ticketCountError');
              errorMessage.textContent = 'Total tickets must equal the sum of adult and child tickets.';
              errorMessage.classList.remove('d-none');
              
              setTimeout(function() {
                  errorMessage.classList.add('d-none');
              }, 5000);
          }
      });
      
      // Auto-calculate total tickets
      const adultTicketsInput = document.getElementById('adultTickets');
      const childTicketsInput = document.getElementById('childTickets');
      const totalTicketsInput = document.getElementById('totalTickets');
      
      function updateTotalTickets() {
          const adultTickets = parseInt(adultTicketsInput.value) || 0;
          const childTickets = parseInt(childTicketsInput.value) || 0;
          totalTicketsInput.value = adultTickets + childTickets;
      }
      
      adultTicketsInput.addEventListener('input', updateTotalTickets);
      childTicketsInput.addEventListener('input', updateTotalTickets);
  }
  
  // Price calculation based on seat category
  const detailsForm = document.querySelector('#reservationDetailsForm');
  if (detailsForm) {
      const seatCategories = document.querySelectorAll('[name="seatCategory"]');
      const priceInputs = document.querySelectorAll('[name="price"]');
      
      seatCategories.forEach((select, index) => {
          select.addEventListener('change', function() {
              let basePrice = 0;
              switch(this.value) {
                  case 'ECONOMY':
                      basePrice = 100;
                      break;
                  case 'BUSINESS':
                      basePrice = 300;
                      break;
                  case 'FIRST_CLASS':
                      basePrice = 500;
                      break;
              }
              priceInputs[index].value = basePrice;
          });
      });
      
      // Toggle promotional checkbox effects
      const promotionalCheckboxes = document.querySelectorAll('[name="promotional"][type="checkbox"]');
      promotionalCheckboxes.forEach((checkbox, index) => {
          checkbox.addEventListener('change', function() {
              if (this.checked) {
                  const currentPrice = parseFloat(priceInputs[index].value);
                  priceInputs[index].value = (currentPrice * 0.9).toFixed(2); // 10% discount
              } else {
                  const category = seatCategories[index].value;
                  let basePrice = 0;
                  switch(category) {
                      case 'ECONOMY':
                          basePrice = 100;
                          break;
                      case 'BUSINESS':
                          basePrice = 300;
                          break;
                      case 'FIRST_CLASS':
                          basePrice = 500;
                          break;
                  }
                  priceInputs[index].value = basePrice;
              }
          });
      });
  }
});