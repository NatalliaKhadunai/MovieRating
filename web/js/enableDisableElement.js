function enable(elementName) {
    var element = document.getElementsByName(elementName)[0];
    if (element.disabled == true) {
        element.disabled = false;
        element.required = true;
    }
    else{
        element.disabled = true;
        element.required = false;
    }
}
