function validate(form) {
    var elems = form.elements;

    if (elems.email.disabled == false && elems.email.value.trim() == "") {
        alert("Name shouldn't  be empty or contain only spaces!");
        return false;
    }
    else if (elems.email.disabled == false && validateEmail(elems.email.value)==false) {
        alert("Email is not valid!");
        return false;
    }
    else if (elems.fullName.disabled == false && elems.fullName.value.trim() == "") {
        alert("End year shouldn't  be empty or contain only spaces!");
        return false;
    }
    return true;
}

function validateEmail(email)
{
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}