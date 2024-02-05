getAllItems();

$("#addButtonItem").click(function () {
        saveItem();
});

$("#btnGetAll").click(function () {
    getAllItems();
});

function bindTrEvents() {
    $('#tb2>tr').click(function () {
        let code = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let price = $(this).children().eq(2).text();
        let quantity = $(this).children().eq(3).text();

        //set the selected rows data to the input fields
        $("#itemCode").val(code);
        $("#itemName").val(name);
        $("#price").val(price);
        $("#quantity").val(quantity);
    })
}

//delete btn event
$("#deleteButtonItem").click(function () {
    let id = $("#itemCode").val();

    let consent = confirm("Do you want to delete.?");
    if (consent) {
        let response = deleteItem(id);
        if (response) {
            alert("Item Deleted");
            clearItemInputFields();
            getAllItems();
        } else {
            alert("Item Not Removed..!");
        }
    }


});

//update  btn event
$("#updateButtonItem").click(function () {
    let id = $("#itemCode").val();
    updateItem(id);
    clearItemInputFields();
});

//clear btn event
$("#clearButtonItem").click(function () {
    clearItemInputFields();
});


function saveItem() {
    if(checkAllItemValidations()){
        let itemCode = $("#itemCode").val();
        if (searchCustomer(itemCode.trim()) == undefined) {
    
            let itemName = $("#itemName").val();
            let price = $("#price").val();
            let quantity = $("#quantity").val();
    
            let newItem = Object.assign({}, item);
    
            newItem.code = itemCode;
            newItem.name = itemName;
            newItem.price = price;
            newItem.quantity = quantity;
    
            itemDB.push(newItem);
            clearItemInputFields();
            getAllItems();
            populateItemCodeDropdown();
        } else {
            alert("Item already exits.!");
            clearItemInputFields();
        }
    }else{
        alert("Please fix validation errors before saving.");
    }
   
}


function getAllItems() {
    $("#tb2").empty();

    for (let i = 0; i < itemDB.length; i++) {
        let code = itemDB[i].code;
        let name = itemDB[i].name;
        let price = itemDB[i].price;
        let quantity = itemDB[i].quantity;

        let row = `<tr>
                     <td>${code}</td>
                     <td>${name}</td>
                     <td>${price}</td>
                     <td>${quantity}</td>
                    </tr>`;

        $("#tb2").append(row);

        bindTrEvents();
    }
}

function deleteItem(code) {
    for (let i = 0; i < itemDB.length; i++) {
        if (itemDB[i].code == code) {
            itemDB.splice(i, 1);
            return true;
        }
    }
    return false;
}

function searchItem(code) {
    return itemDB.find(function (item) {

        return item.code == code;
    });
}

function updateItem(code) {
    if (searchItem(code) == undefined) {
        alert("No such Item..please check the ID");
    } else {
        let consent = confirm("Do you really want to update this Item.?");
        if (consent) {
            let item = searchItem(code);

            let itemCode = $("#itemCode").val();
            let itemName = $("#itemName").val();
            let price = $("#price").val();
            let quantity = $("#quantity").val();

            item.code = itemCode;
            item.name = itemName;
            item.price = price;
            item.quantity = quantity;

            getAllItems();
        }
    }

}