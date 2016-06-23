function validate(form) {
    var elems = form.elements;

    if (elems.name.disabled == false && elems.name.value.trim() == "") {
        alert("Name shouldn't  be empty or contain only spaces!");
        return false;
    }
    else if (elems.releaseYear.disabled == false && elems.releaseYear.value.trim() == "") {
        alert("Release year shouldn't  be empty or contain only spaces!");
        return false;
    }
    else if (elems.releaseYear.disabled == false && numberValidation(elems.releaseYear.value) == false) {
        alert("Release year should contain only numeric characters!");
        return false;
    }
    else if (elems.endYear.disabled == false && elems.endYear.value.trim() == "") {
        alert("End year shouldn't  be empty or contain only spaces!");
        return false;
    }
    else if (elems.endYear.disabled == false && numberValidation(elems.endYear.value) == false) {
        alert("End year should contain only numeric characters!");
        return false;
    }
    else if (elems.numOfSeasons.disabled == false && elems.numOfSeasons.value.trim() == "") {
        alert("Number of seasons shouldn't  be empty or contain only spaces!");
        return false;
    }
    else if (elems.numOfSeasons.disabled == false && numberValidation(elems.numOfSeasons.value) == false) {
        alert("Number of seasons should contain only numeric characters!");
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

function numberValidation(number) {
    var numberStr = number.toString();
    for (var i=0;i<numberStr.length;i++) {
        var ch = numberStr.charAt(i);
        if (ch < '0' || ch > '9') return false;
    }
    return true;
}
