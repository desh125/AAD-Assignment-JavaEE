const CUS_ID_REGEX = /^C00-\d{3}$/; 
const CUS_NAME_REGEX = /^[A-Za-z ]{5,}$/;
const CUS_TP_REGEX = /^\d{10}$/; 
const CUS_ADDRESS_REGEX = /^[A-Za-z0-9 ]{8,}$/; 
const CUS_SALARY_REGEX = /^\d+(\.\d{2})?$/; 

let c_vArray = new Array();
c_vArray.push({ field: $("#cId"), regEx: CUS_ID_REGEX });
c_vArray.push({ field: $("#cName"), regEx: CUS_NAME_REGEX });
c_vArray.push({ field: $("#cTP"), regEx: CUS_TP_REGEX });
c_vArray.push({ field: $("#cAddress"), regEx: CUS_ADDRESS_REGEX });
c_vArray.push({ field: $("#cSalary"), regEx: CUS_SALARY_REGEX });
function clearCustomerInputFields() {
    $("#cId, #cName, #cAge,#cTP, #cSalary").val("");
    $("#cId, #cName, #cAge,#cTP, #cSalary").css("border", "1px solid #ced4da");
    $("#cId").focus();
    //setBtn();
}


function checkValidations(object) {
    if (object.regEx.test(object.field.val())) {
        setBorder(true, object);
        return true;
    } else {
        setBorder(false, object);

        // Provide a custom message based on the field
        let fieldName = object.field.attr('id');

        let errorMessage;
        switch (fieldName) {
            case 'cId':
                errorMessage = 'Invalid Customer ID. It should match the format C00-XXX';
                break;
            case 'cName':
                errorMessage = 'Invalid Customer Name. It should be at least 5 characters long and contain only letters and spaces.';
                break;
            case 'cTP':
                errorMessage = 'Invalid Phone Number. It should be a 10-digit number.';
                break;
            case 'cAddress':
                errorMessage = 'Invalid Address. It should be at least 8 characters long and contain only letters, numbers, and spaces.';
                break;
            case 'cSalary':
                errorMessage = 'Invalid Salary. It should be a numeric value with an optional 2 decimal places.';
                break;
            default:
                errorMessage = 'Invalid Input';
        }

        alert(errorMessage);

        return false;
    }
}

function setBorder(bol, ob) {
    if (!bol) {
        ob.field.css("border", "2px solid red");
    } else {
        ob.field.css("border", "2px solid green");
    }
}

function checkAll() {
    for (let i = 0; i < c_vArray.length; i++) {
        if (!checkValidations(c_vArray[i])) return false;
    }
    return true;
}

function checkAllValidations() {
    for (let i = 0; i < c_vArray.length; i++) {
        if (!checkValidations(c_vArray[i])) return false;
    }
    return true;
}
/*
function setBtn() {
    $("#deleteButtonCustomer").prop("disabled", true);
    $("#updateButtonCustomer").prop("disabled", true);

    if (checkAll()) {
        $("#saveButtonCustomer").prop("disabled", false);
    } else {
        $("#saveButtonCustomer").prop("disabled", true);
    }

    let id = $("#cId").val();
    if (searchCustomer(id) == undefined) {
        $("#deleteButtonCustomer").prop("disabled", true);
        $("#updateButtonCustomer").prop("disabled", true);
    } else {
        $("#deleteButtonCustomer").prop("disabled", false);
        $("#updateButtonCustomer").prop("disabled", false);
    }
}
*/