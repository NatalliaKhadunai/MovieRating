function validate(form) {
    var elems = form.elements;

    if (elems.videoProductName.value.trim() == "") {
        alert("Video product name shouldn't  be empty or contain only spaces!");
        return false;
    }
    return true;
}
