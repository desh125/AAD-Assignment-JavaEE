function checkPurchaseButtonStatus() {
    var isFormValid = true;

    var date = $('#orderDate').val();
    if (!date) {
        isFormValid = false;
    }

    var address = $('#customerAddress').val();
    if (!address) {
        isFormValid = false;
    }

    var cash = parseFloat($('#cash').val()) || 0;
    if (isNaN(cash) || cash <= 0) {
        isFormValid = false;
    }

    var discount = parseFloat($('#discount').val()) || 0;
    if (isNaN(discount) || discount < 0) {
        isFormValid = false;
    }

    var customerId = $('#selectCusID').val();
    if (!customerId) {
        isFormValid = false;
    }

    var itemCode = $('#selectItemCode').val();
    if (!itemCode) {
        isFormValid = false;
    }

    $('#purchaseButton').prop('disabled', !isFormValid);
}

$('#orderDate, #customerAddress, #cash, #discount, #selectCusID, #selectItemCode').on('change', checkPurchaseButtonStatus);

$('#purchaseButton').prop('disabled', true);
