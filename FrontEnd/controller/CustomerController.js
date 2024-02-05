getAllCustomers();

$("#saveButtonCustomer").click(function () {
        saveCustomer();
});

$("#btnGetAll").click(function () {
    getAllCustomers();
});

function bindTrEvents2() {
    $('#tb>tr').click(function () {
        let id = $(this).children().eq(0).text();
        let name = $(this).children().eq(1).text();
        let tp = $(this).children().eq(2).text();
        let age = $(this).children().eq(3).text();
        let salary = $(this).children().eq(4).text();

        $("#cId").val(id);
        $("#cName").val(name);
        $("#cTP").val(tp);
        $("#cAge").val(age);
        $("#cSalary").val(salary);
    })
}

//delete btn event
$("#deleteButtonCustomer").click(function () {
    let id = $("#cId").val();

    let consent = confirm("Do you want to delete.?");
    if (consent) {
        let response = deleteCustomer(id);
        if (response) {
            alert("Customer Deleted");
            clearCustomerInputFields();
            getAllCustomers();
        } else {
            alert("Customer Not Removed..!");
        }
    }


});

//update  btn event
$("#updateButtonCustomer").click(function () {
    let id = $("#cId").val();
    updateCustomer(id);
   clearCustomerInputFields();
});

//clear btn event
$("#clearButtonCustomer").click(function () {
    clearCustomerInputFields();
});



// CRUD operation Functions
function saveCustomer() {
    if (checkAllValidations()) {
        let customerID = $("#cId").val();
        if (searchCustomer(customerID.trim()) == undefined) {
            let customerName = $("#cName").val();
            let customerTP = $("#cTP").val();
            let customerAge = $("#cAge").val();
            let customerSalary = $("#cSalary").val();

            let newCustomer = Object.assign({}, customer);

            newCustomer.id = customerID;
            newCustomer.name = customerName;
            newCustomer.tp = customerTP;
            newCustomer.age = customerAge;
            newCustomer.salary = customerSalary;

            customerDB.push(newCustomer);
            clearCustomerInputFields();
            getAllCustomers();
            populateCustomerDropdown();
        } else {
            alert("Customer already exists!");
            clearCustomerInputFields();
        }
    } else {
        alert("Please fix validation errors before saving.");
    }
}

function getAllCustomers() {
    $("#tb").empty();

    //get all customers
    for (let i = 0; i < customerDB.length; i++) {
        let id = customerDB[i].id;
        let name = customerDB[i].name;
        let tp = customerDB[i].tp;
        let age = customerDB[i].age;
        let salary = customerDB[i].salary

        let row = `<tr>
                     <td>${id}</td>
                     <td>${name}</td>
                     <td>${tp}</td>
                     <td>${age}</td>
                     <td>${salary}</td>
                    </tr>`;

        $("#tb").append(row);

        bindTrEvents2();
    }
}

function deleteCustomer(id) {
    for (let i = 0; i < customerDB.length; i++) {
        if (customerDB[i].id == id) {
            customerDB.splice(i, 1);
            return true;
        }
    }
    return false;
}

function searchCustomer(id) {
    return customerDB.find(function (customer) {

        return customer.id == id;
    });
}

function updateCustomer(id) {
    if (searchCustomer(id) == undefined) {
        alert("No such Customer..please check the ID");
    } else {
        let consent = confirm("Do you really want to update this customer.?");
        if (consent) {
            let customer = searchCustomer(id);

            let customerName = $("#cName").val();
            let customerTP = $("#cTP").val();
            let customerAge = $("#cAge").val();
            let customerSalary = $("#cSalary").val();

            customer.name = customerName;
            customer.tp = customerTP;
            customer.age = customerAge;
            customer.salary = customerSalary;

            getAllCustomers();
        }
    }

}