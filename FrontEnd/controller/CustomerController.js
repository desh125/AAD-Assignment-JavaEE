let baseUrl = 'http://localhost:8080/app/pages/';


$(document).ready(function () {
    bindRowClickEvents()
    getAllCustomers();
    $("#saveButtonCustomer").click(function () {
        console.log("add button clicked")
        saveCustomer();
        getAllCustomers();
    });

    $("#btnGetAll").click(function () {
        getAllCustomers();
    });


    function bindRowClickEvents() {
        $('#tb').on('click', 'tr', function () {
            let id = $(this).find('td:eq(0)').text();
            let name = $(this).find('td:eq(1)').text();
            let tp = $(this).find('td:eq(2)').text();
            let age = $(this).find('td:eq(3)').text();
            let salary = $(this).find('td:eq(4)').text();
            setTextFields(id, name, tp,age,salary);
        });
    }

// set text fields
    function setTextFields(id, name, tp,age,salary) {
        $('#cId').val(id);
        $('#cName').val(name);
        $('#cAge').val(age);
        $('#cTP').val(tp);
        $('#cSalary').val(salary);
    }


    // Delete button event
    $("#deleteButtonCustomer").click(function () {
        let id = $("#cId").val();
        $.ajax({

            type: "DELETE",
            url: baseUrl + 'customer?cusId=' + id,
            success: function (response) {
                alert(response.message);
                getAllCustomers();
            },
            error: function (xhr, status, error) {
                alert("Error: " + xhr.responseText);
            }
        });
        console.log(id);
    });

    // Update button event
    $("#updateButtonCustomer").click(function () {
        let id1 = $("#cId").val();
        let customerName = $("#cName").val();
        let customerTP = $("#cTP").val();
        let customerAge = parseInt($("#cAge").val()); // Ensure age is parsed as integer
        let customerSalary = parseFloat($("#cSalary").val()); // Ensure salary is parsed as float

        let customer = {
            "cusId": id1,
            "cusName": customerName,
            "cusTp": customerTP,
            "cusAge": customerAge,
            "cusSalary": customerSalary
        }


        let b = confirm("Do you want to Update " + id1 + " ?");

        if (b) {
            $.ajax({
                url: baseUrl + 'customer',
                method: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(customer),
                success: function (res) {
                    alert(res.message);
                    getAllCustomers();
                },
                error: function (error) {
                    if (error.responseJSON && error.responseJSON.message) {
                        alert(error.responseJSON.message);
                    } else {
                        alert("An error occurred while processing your request.");
                    }
                }
            });
        }
    });


    // Clear button event
    $("#clearButtonCustomer").click(function () {
        clearCustomerInputFields();
    });

    // CRUD operation functions

    function saveCustomer() {
        let customerID = $("#cId").val();
        let customerName = $("#cName").val();
        let customerTP = $("#cTP").val();
        let customerAge = $("#cAge").val();
        let customerSalary = $("#cSalary").val();

        $.ajax({
            type: "POST",
            url: baseUrl+"customer",
            data: {
                cusId: customerID,
                cusName: customerName,
                cusTp: customerTP,
                cusAge: customerAge,
                cusSalary: customerSalary
            },
            success: function (response) {
                alert(response.message);
                clearCustomerInputFields();
                getAllCustomers();
            },
            error: function (xhr, status, error) {
                alert("Error: " + xhr.responseText);
            }
        });
    }

    function getAllCustomers() {
        $("#tb").empty();

        $.ajax({
            url: baseUrl + 'customer',
            dataType: "json",
            method: "GET",
            success: function (response) {
                let customers = response.data;

                for (let i = 0; i < customers.length; i++) {
                    let cus = customers[i];
                    let id = cus.id;
                    let name = cus.name;
                    let tp = cus.tp;
                    let age = cus.age;
                    let salary = cus.salary;
                    let row = `<tr><td>${id}</td><td>${name}</td><td>${tp}</td><td>${age}</td><td>${salary}</td></tr>`;
                    $("#tb").append(row);
                }
               // setTextFields("", "", "", "", ""); // Assuming setTextFields takes 5 arguments
            },
            error: function (error) {
                alert(error.responseJSON.message);
               // setTextFields("", "", "", "", ""); // Assuming setTextFields takes 5 arguments
            }
        });
    }

    function deleteCustomer(id) {

    }

    function updateCustomer(id) {

    }
});
