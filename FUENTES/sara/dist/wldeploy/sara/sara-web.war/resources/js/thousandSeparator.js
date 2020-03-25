function formatInput(event) {

    var keysCode = [38, 40, 37, 39];
    // When user select text in the document, also abort.
    var selection = window.getSelection().toString();
    if (selection !== '') {
        return false;
    }

    // When the arrow keys are pressed, abort.
    if (keysCode.indexOf(event.keyCode) !== -1) {
        return false;
    }

    // Get the value.
    var input = event.target.value;
    input = input.replace(/[\D\s\._\-]+/g, "");
    input = input ? parseInt(input, 10) : 0;

    event.target.value = (input === 0) ? "0" : input.toLocaleString("en-US");
    return false;
}