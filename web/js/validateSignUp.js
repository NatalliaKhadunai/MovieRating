function validate(form) {
    var elems = form.elements;

    if (elems.login.value.trim() == "") {
        alert("Login shouldn't  be empty or contain only spaces!");
        return false;
    }

    else if (elems.email.value.trim() == "") {
        alert("Email shouldn't  be empty or contain only spaces!");
        return false;
    }

    else if (elems.password.value.trim() == "") {
        alert("Password shouldn't  be empty or contain only spaces!");
        return false;
    }
    else form.submit();
}