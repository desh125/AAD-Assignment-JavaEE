
$(document).ready(function () {
    bindRowClickEvents()
    getAllItems();
    $("#addButtonItem").click(function () {
        if(checkAllItemValidations()){
            saveItem();
        }

    });

    $("#btnGetAll").click(function () {
        getAllItems();
    });

    $("#deleteButtonItem").click(function () {
        let code = $("#itemCode").val();
        deleteItem(code);
    });

    $("#updateButtonItem").click(function () {
        let code = $("#itemCode").val();
        updateItem(code);
    });

    $("#clearButtonItem").click(function () {
        clearItemInputFields();
    });
});

function saveItem() {
    let itemCode = $("#itemCode").val().trim();
    let itemName = $("#itemName").val().trim();
    let price = $("#price").val().trim();
    let quantity = $("#quantity").val().trim();


    $.ajax({
        url: baseUrl + 'item',
        method: 'POST',
        data: {
            code: itemCode,
            name: itemName,
            price: price,
            qty: quantity
        },
        success: function (res) {
            alert("success");
            clearItemInputFields();
            getAllItems();
        },
        error: function (error) {
            alert("An error occurred while processing your request.");
        }
    });
}

function getAllItems() {
    $("#tb2").empty();

    $.ajax({
        url: baseUrl + 'item',
        dataType: "json",
        method: "GET",
        success: function (response) {
            let items = response.data;

            for (let i = 0; i < items.length; i++) {
                let cus = items[i];
                let code = cus.code;
                let name = cus.itemName;
                let qty = cus.qty;
                let price = cus.price
                let row = `<tr><td>${code}</td><td>${name}</td><td>${qty}</td><td>${price}</td></tr>`;
                $("#tb2").append(row);
            }
            // setTextFields("", "", "", "", "");
        },
        error: function (error) {
            alert(error.responseJSON.message);
            // setTextFields("", "", "", "", "");
        }
    });
}

function deleteItem(code) {
    let consent = confirm("Do you want to delete this item?");
    if (!consent) return;

    $.ajax({
        type: 'DELETE',
        url: baseUrl + 'item?code=' + code,

        success: function (res) {
            alert(res.message);
            clearItemInputFields();
            getAllItems();
        },
        error: function (error) {
            alert("An error occurred while deleting the item.");
        }
    });
}

function updateItem(code) {
    let itemName = $("#itemName").val().trim();
    let price = $("#price").val().trim();
    let quantity = $("#quantity").val().trim();

    let updatedItem = {
        "code": code,
        "name": itemName,
        "price": price,
        "qty": quantity
    };

    $.ajax({
        url: baseUrl + 'item',
        method: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(updatedItem),
        success: function (res) {
            alert("success");
            clearItemInputFields();
            getAllItems();
        },
        error: function (error) {
            alert("An error occurred while updating the item.");
        }
    });
}

function bindRowClickEvents() {
    $('#tb2').on('click', 'tr', function () {
        let id = $(this).find('td:eq(0)').text();
        let name = $(this).find('td:eq(1)').text();
let price = $(this).find('td:eq(2)').text();
let qty = $(this).find('td:eq(3)').text();
        setTextFields(id, name, price,qty);
    });
}

function setTextFields(id, name,price,qty) {
    $('#itemCode').val(id);
    $('#itemName').val(name);
    $('#price').val(price);
    $('#quantity').val(qty);
}

function clearItemInputFields() {
    $("#itemCode").val("");
    $("#itemName").val("");
    $("#price").val("");
    $("#quantity").val("");
}