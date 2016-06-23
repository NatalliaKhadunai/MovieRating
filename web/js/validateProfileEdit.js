function validate(form) {
    var elems = form.elements;

    if (elems.email.disabled == false && elems.email.value.trim() == "") {
        alert("Name shouldn't  be empty or contain only spaces!");
        return false;
    }
    else if (elems.fullName.disabled == false && elems.fullName.value.trim() == "") {
        alert("End year shouldn't  be empty or contain only spaces!");
        return false;
    }
    return true;
}
