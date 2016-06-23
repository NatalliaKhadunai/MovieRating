function validate(form) {
    var elems = form.elements;

    if (elems.name.disabled == false && elems.name.value.trim() == "") {
        alert("Name shouldn't  be empty or contain only spaces!");
        return false;
    }

    else if (elems.description.disabled == false && elems.description.value.trim() == "") {
        alert("Description shouldn't  be empty or contain only spaces!");
        return false;
    }

    else if (elems.rating.disabled == false && elems.rating.value.trim() == "") {
        alert("Rating shouldn't  be empty or contain only spaces!");
        return false;
    }
    else if (elems.rating.disabled == false && ratingValidation(elems.rating.value) == false) {
        alert("Rating should contain only numeric symbols and .");
        return false;
    }
    return true;
}

function ratingValidation(rating) {
    var ratingStr = rating.toString();
    for (var i=0;i<ratingStr.length;i++) {
        var ch = ratingStr.charAt(i);
        if (ch < '0' && ch != '.') return false;
        if (ch > '9' && ch != '.') return false;
    }
    return true;
}
