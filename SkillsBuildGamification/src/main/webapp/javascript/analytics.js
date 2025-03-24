// Uses google charts.
// Most of this code was copied from https://developers.google.com/chart/interactive/docs/gallery/piechart
// Alterations were made to fit it to our data and to format it nicely.

// Load google charts
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(prepareChart);

// Checks if there is any data to display, displays it if there is and displays info text if there's not
function prepareChart() {
    if (goalsCompleted + goalsExpired + goalsActive === 0) {
        // If there is no data to show, state so.
        const messageNode= document.createElement("span");
        messageNode.innerText = "There is no data to show at the moment. When users complete goals, a pie chart will be shown here."
        document.getElementById("piechart").appendChild(messageNode);
    }
    else {
        drawChart();
    }
}

// Draw the chart with data from goals
function drawChart() {
    const data = google.visualization.arrayToDataTable([
        ['Status', 'Count'],
        ['Active', goalsActive],
        ['Completed', goalsCompleted],
        ['Expired', goalsExpired]
    ]);

    // Options are used for formatting
    const options = {
        'width': 600,
        'height': 400,
        pieHole: 0.5,
        pieSliceText: 'value',
        fontSize: 16,
        colors: ['32afc2', '5bc232', 'c2291b'],
        backgroundColor: {fill: 'transparent'},
        chartArea: {
            height: '100%',
            width: '100%',
            top: 48,
            left: 48,
            right: 16,
            bottom: 48
        }
    };

    // Display the chart
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(data, options);
}