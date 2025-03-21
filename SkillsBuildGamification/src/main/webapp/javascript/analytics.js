// Uses google charts.
// Most of this code was copied from https://developers.google.com/chart/interactive/docs/gallery/piechart
// Alterations were made to fit it to our data and to format it nicely.

// Load google charts
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

// Draw the chart with data from goals
function drawChart() {
    var data = google.visualization.arrayToDataTable([
        ['Status', 'Count'],
        ['Active', goalsActive],
        ['Completed', goalsCompleted],
        ['Expired', goalsExpired]
    ]);

    // Options are used for formatting
    var options = {
        'width':600,
        'height':400,
        pieHole: 0.5,
        pieSliceText: 'value',
        fontSize: 16,
        colors: ['32afc2','5bc232','c2291b'],
        backgroundColor: {fill:'transparent'},
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