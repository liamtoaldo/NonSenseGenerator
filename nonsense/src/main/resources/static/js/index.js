//Risolve il problema di importazione di Chart.js e ChartDataLabels
if (window.ChartDataLabels) {
    Chart.register(ChartDataLabels);
} else {
    console.error("ChartDataLabels plugin not found. Make sure it's loaded before index.js");
}

// Funzione che usa la ricerca ricorsiva per ottenere dati da un syntaxtree
function flattenTree(node, parentIndex = null, arr = [], labels = []) {
    const idx = arr.length;
    arr.push({
        name: node.word.text,
        parent: parentIndex,
        wordClassType: node.word.wordClassType,
    });
    text = node.word.text;
    if (node.word.tense) {
        text += ` (${node.word.tense})`;
    }

    text += ` [${node.dependencyLabel}]`;
    labels.push(text);
    if (node.children) {
        node.children.forEach(child =>
            flattenTree(child, idx, arr, labels)
        );
    }
    return {
        data: arr,
        labels: labels
    };
}

$(document).ready(function () {
    $('[data-bs-toggle="tooltip"]').tooltip();

    // Nasconde il tooltip dopo il click sul bottone
    $('[data-bs-toggle="tooltip"]').on('click', function () {
        $(this).blur();
    });

    const sidebarToggle = $('#sidebarToggle');
    const sidebar = $('#sidebar');
    if (sidebarToggle && sidebar) {
        sidebarToggle.on('click', function() {
            sidebar.toggleClass('collapsed');
        });
    }

    const analyzeButton = $('#analyzeSyntaxButton');
    const inputText = $('#inputText');
    const ctx = $('#syntaxTreeChart');
    let chart = null;

    if (analyzeButton && inputText && syntaxTreeChart) {
        analyzeButton.on('click', function () {
            const sentence = inputText.val();
            if (sentence.trim() === '') {
                //TODO: mostrare un messaggio di errore
                return;
            }

            // Mostra spinner
            $('#syntaxTreeSpinner').show();

            $.ajax({
                url: '/api/v1/nonsense/syntax-tree?sentenceText=' + encodeURIComponent(sentence),
                type: 'GET',
                success: function (data) {
                    // Elimina il vecchio grafico se esiste
                    if (chart) {
                        chart.destroy();
                    }

                    const flattenedResult = flattenTree(data.root);
                    const nodesFlattened = flattenedResult.data;
                    const labels = flattenedResult.labels;

                    chart = new Chart(ctx, {
                        type: 'tree',
                        data: {
                            labels,
                            datasets: [{
                                label: 'Syntax Tree Root',
                                pointBackgroundColor: function (context) {
                                    if (context.dataIndex >= nodesFlattened.length || context.dataIndex < 0) {
                                        console.warn(`Index ${context.dataIndex} is out of bounds for nodesFlattened. Length: ${nodesFlattened.length}. Returning steelblue.`);
                                        return 'steelblue'; // Indice non valido
                                    }

                                    if (context.dataIndex === 0) {
                                        return 'red'; // La radice è sempre rossa
                                    }

                                    const nodeDataFromScope = nodesFlattened[context.dataIndex];

                                    if (!nodeDataFromScope || !nodeDataFromScope.wordClassType) {
                                        return 'steelblue';
                                    }
                                    const wordType = nodeDataFromScope.wordClassType;

                                    switch (wordType) {
                                        case 'Noun':
                                            return 'purple'; //Nomi in viola
                                        case 'Verb':
                                            return 'green'; //Verbi in verde
                                        case 'Adjective':
                                            return 'orange'; //Aggettivi in arancione
                                        default:
                                            return 'steelblue'; // Colore predefinito per altri tipi
                                    }
                                },
                                pointRadius: 8,
                                data: nodesFlattened
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            tree: {
                                mode: 'tree',
                                orientation: 'vertical',
                            },
                            plugins: {
                                legend: {
                                    display: true,
                                },
                                datalabels: {
                                    display: true,
                                    color: 'white',
                                    align: 'top',
                                    anchor: 'end',
                                    offset: 6,
                                    font: {
                                        weight: 'normal',
                                        size: 12
                                    },
                                    backgroundColor: 'rgba(0, 0, 0, 0.5)',
                                    formatter: function (value, context) {
                                        return context.chart.data.labels[context.dataIndex];
                                    },
                                    borderRadius: 4,
                                }
                            }
                        }
                    });

                    // Nascondi spinner
                    $('#syntaxTreeSpinner').hide();
                },
            })
        });
    }

});