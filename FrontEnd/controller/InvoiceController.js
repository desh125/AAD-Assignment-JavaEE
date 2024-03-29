$('#orderId, #customerName, #customerTP, #customerSalary').prop('disabled', true);
$('#itemName2, #itemPrice2, #qtyOnHand, #qtyAfter').prop('disabled', true);
populateCustomerDropdown();
populateItemCodeDropdown()
var totalPrice=0;
function populateCustomerDropdown() {
    var selectCusID = $('#selectCusID');

    selectCusID.empty();

    selectCusID.append($('<option>', {
        value: '',
        text: 'Select Customer ID'
    }));

    $.ajax({
        url: baseUrl+'customer',
        dataType: "json",
        method: 'GET',
        success: function (response) {
            let items = response.data;

            for (let i = 0; i < items.length; i++) {
                let cus = items[i];
                selectCusID.append($('<option>', {
                    value: cus.id,
                    text: cus.id,
                    "data-id": JSON.stringify(cus)
                }));
            }
        },
        error: function (error) {
            console.error('Error fetching customer IDs:', error);
        }
    });
}


    function populateItemCodeDropdown() {
        var selectItemCode = $('#selectItemCode');

        selectItemCode.empty();

        selectItemCode.append($('<option>', {
            value: '',
            text: 'Select Item Code'
        }));

        $.ajax({
            url: baseUrl+'item',
            dataType: "json",
            method: 'GET',
            success: function (response) {
                let items = response.data;

                for (let i = 0; i < items.length; i++) {
                    let cus = items[i];
                    selectItemCode.append($('<option>', {
                        value: cus.code,
                        text: cus.code,
                        "data-id": JSON.stringify(cus)
                    }));
                }
            },
            error: function (error) {
                console.error('Error fetching customer IDs:', error);
            }
        });
    }


$('#selectCusID').change(function () {
    var selectedCustomerId = $(this).val();

    var selectedOption = $(this).find("option:selected");

    var customerId = selectedOption.text();
    var customerDetails = selectedOption.data("id");

    // Update the customer details in the form fields
    $('#orderId').val(generateOrderId());
    $('#customerName').val(customerDetails.name);
    $('#customerTP').val(customerDetails.tp);
    $('#customerSalary').val(customerDetails.salary);
   // $('#customerAddress').val(customerDetails.address);
});

    $('#selectItemCode').change(function () {
        var selectedOption = $(this).find("option:selected");
        var itemDetails = selectedOption.data("id");

        $('#itemName2').val(itemDetails.itemName);
        $('#itemPrice2').val(itemDetails.price);
        $('#qtyOnHand').val(itemDetails.qty);
        let qtyAfter = $('#qty').val();
        $('#qtyAfter').val(itemDetails.qty-qtyAfter);

        
    });

    $('#qty, #itemPrice2').on('input', function () {
        var qty = parseFloat($('#qty').val()) || 0;
        var price = parseFloat($('#itemPrice2').val()) || 0;
        // totalPrice = qty * price;
       // $('#totalPrice').val(totalPrice);
    });

    function generateOrderId() {
        return 'ORD' + Math.floor(Math.random() * 10000);
    }

    $('#purchaseButton').click(function () {

        alert('Purchase button clicked');
    });

    $('#addItemButton2').click(function () {
        var itemCode = $('#selectItemCode').val();
        var itemName = $('#itemName2').val();
        var itemPrice = parseFloat($('#itemPrice2').val()) || 0;
        var qty = parseInt($('#qty').val()) || 0;
        var qtyAfter = parseInt($('#qtyAfter').val()) || 0;
        totalPrice +=  itemPrice * qty;
        $('#totalPrice').val(totalPrice);
        var totalPrice1 = itemPrice * qty;
        var newRow = $('<tr>');
        newRow.append($('<td>').text(itemCode));
        newRow.append($('<td>').text(itemName));
        newRow.append($('<td>').text(itemPrice));
        newRow.append($('<td>').text(qty));
        newRow.append($('<td>').text(totalPrice1));

        $('#tb3').append(newRow);

        $('#qtyAfter').val(qtyAfter - qty);

        $('#selectItemCode').val('');
        $('#itemName2').val('');
        $('#itemPrice2').val('');
        $('#qty').val('');


    });

$('#purchaseButton').click(function () {

    var orderId = $('#orderId').val();
    var date = $('#orderDate').val();
    var customerId = $('#selectCusID').val();
    var discount = $('#discount').val();
    var total = $('#totalPrice').val();
    var cart = [];

    $('#tb3 tr').each(function () {
        var itemCode = $(this).find('td:eq(0)').text();
        cart.push({ "item": { "code": itemCode } });
    });

    var requestData = {
        "orderId": orderId,
        "date": date,
        "customerId": customerId,
        "discount": discount,
        "total": total,
        "cart": cart
    };

    $.ajax({
        url: baseUrl + 'invoice',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(requestData),
        success: function (response) {
            if (response.state === 'Success') {
                alert("Invoice saved successfully.");
                getAllOrderDetails();
            } else {
                alert("invoice saved: ");
            }
        },
        error: function (xhr, status, error) {
            //alert("An error occurred while processing the request.");
            console.error(xhr.responseText);
        }
    });
});

function calculateBalanceAndDiscount() {
        var updatedQty = parseInt($('#qty').val()) || 0;
        var updatedPrice = parseFloat($('#itemPrice2').val()) || 0;
        var updatedTotalPrice = updatedQty * updatedPrice;
        var cash = parseFloat($('#cash').val()) || 0;
        var discountPercentage = parseFloat($('#discount').val()) || 0;
        var discountAmount = (totalPrice * discountPercentage) / 100;
    
        var discountedTotal = totalPrice - discountAmount;
        $('#totalPrice').val(discountedTotal);
    
        var balance = cash - discountedTotal; 
        $('#balance').val(balance);
    }
    
    $('#cash').on('input', calculateBalanceAndDiscount);
    $('#discount').on('input', calculateBalanceAndDiscount);
    