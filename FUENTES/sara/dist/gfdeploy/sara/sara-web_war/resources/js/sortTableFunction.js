var arrayOrder = [true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true];
function sort_table(tbody, nColumn, tipoColumna, orderAsc) {
    var table, rows, switching, i, x, y, shouldSwitchM
    table = document.getElementById(tbody).tBodies[0];
    switching = true;
    while (switching) {
        switching = false;
        rows = table.getElementsByTagName("TR");
        loop: for (i = 0; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[nColumn];
            y = rows[i + 1].getElementsByTagName("TD")[nColumn];

            xDate = new Date(x.innerHTML);
            yDate = new Date(y.innerHTML);

            switch (tipoColumna) {
                case 'numero':
                    if (orderAsc) {
                        if (parseInt(x.innerHTML.toLowerCase().replace(/,/g, '')) > parseInt(y.innerHTML.toLowerCase().replace(/,/g, ''))) {
                            shouldSwitch = true;
                            break loop;
                        }
                    } else {
                        if (parseInt(x.innerHTML.toLowerCase().replace(/,/g, '')) < parseInt(y.innerHTML.toLowerCase().replace(/,/g, ''))) {
                            shouldSwitch = true;
                            break loop;
                        }
                    }
                    break;
                case 'texto':
                    if (orderAsc) {
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            shouldSwitch = true;
                            break loop;
                        }
                    } else {
                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                            shouldSwitch = true;
                            break loop;
                        }
                    }

                    break;
                case 'fecha':
                    if (orderAsc) {
                        if (xDate > yDate) {
                            shouldSwitch = true;
                            break loop;
                        }
                    } else {
                        if (xDate < yDate) {
                            shouldSwitch = true;
                            break loop;
                        }
                    }
                    break;
                default:
                    shouldSwitch = false;
                    break;
            }
        }

        if (shouldSwitch) {
            try {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            } catch (err) {
                console.log(err);
            }
        }
    }

    for (var indexArr in arrayOrder) {
        arrayOrder[indexArr] = true;
    }
    arrayOrder[nColumn] = !orderAsc;
}