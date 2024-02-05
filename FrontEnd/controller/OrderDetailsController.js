$('#purchaseButton').click(function () {
    var orderId = generateOrderId(); 
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
    });
    
});



