$('#purchaseButton').click(function () {
    getAllOrderDetails();
   /* var orderId = generateOrderId();
    var customerId = $('#selectCusID').val();
    var address = $('#customerAddress').val();
    var date = $('#orderDate').val();

    var newRow = `
        <tr>
            <td>${orderId}</td>
            <td>${customerId}</td>
            <td>${address}</td>
            <td>${date}</td>
            <td>
                <button class="btn btn-warning updateOrder">Update</button>
                <button class="btn btn-danger deleteOrder">Delete</button>
            </td>
        </tr>`;

    $('#tb5').append(newRow);

    $('.updateOrder').click(function () {
//
    });

    $('.deleteOrder').click(function () {
        $(this).closest('tr').remove();
    });*/
    
});

getAllOrderDetails();
function getAllOrderDetails() {
    $("#tb5").empty();

    $.ajax({
        url: baseUrl + 'invoice',
        dataType: "json",
        method: "GET",
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let order = response[i];
                let id = order.orderId;
                let date = order.date;
                let customerId = order.customerId;
                let discount = order.discount;
                let total = order.total;
                let row = `<tr><td>${id}</td><td>${customerId}</td><td>${total}</td><td>${date}</td>            <td>
                <button class="btn btn-warning updateOrder">Update</button>
                <button class="btn btn-danger deleteOrder">Delete</button>
            </td></tr>`;
                $("#tb5").append(row);
            }
        },
        error: function (error) {
            if (error.responseJSON && error.responseJSON.message) {
                alert(error.responseJSON.message);
            } else {
                alert("An error occurred while fetching order details.");
            }
        }
    });
}


