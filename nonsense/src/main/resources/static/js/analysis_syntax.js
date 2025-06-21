// Funzione che usa la ricerca ricorsiva per ottenere dati da un syntaxtree
const flattenTree = (node, parentIndex = null, arr = [], labels = []) => {
    if (!node) {
        return {
            data: arr,
            labels: labels
        };
    }
    const idx = arr.length;
    arr.push({
        name: node.word.text,
        parent: parentIndex,
        wordClassType: node.word.wordClassType,
        root: node.dependencyLabel === 'ROOT',
    });
    let text = node.word.text;
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


// Funzione per creare l'albero sintattico
const analyzesyntax = (inputText) => {
    const sentence = inputText.val();
    if (sentence.trim() === '') {
        showAlert("Input text cannot be empty.", "danger");
        return;
    }

    // Mostra spinner
    $('#syntaxTreeSpinner').show();

    // Variabili per il grafico
    const ctx = $('#syntaxTreeChart');
    let chart = null;

    $.ajax({
        url: '/api/v1/nonsense/sentence/syntax?sentenceText=' + encodeURIComponent(sentence),
        type: 'GET',
        success: function (data) {
            // Elimina il vecchio grafico se esiste
            if (chart) {
                chart.destroy();
            }

            if (!data.roots || data.roots.length === 0) {
                $('#syntaxTreeSpinner').hide();
                showAlert("The syntax tree is empty or not available.", "danger");
                return;
            }

            let rootNode;
            // Se c'è più di una radice, crea una radice fittizia
            if (data.roots.length > 1) {
                rootNode = {
                    word: { text: "Multiple Roots", wordClassType: "Root" },
                    dependencyLabel: "ROOT",
                    children: data.roots
                };
                showAlert("Multiple roots detected. A synthetic root has been created to visualize the tree.", "warning");
            } else {
                // Altrimenti, usa l'unica radice disponibile
                rootNode = data.roots[0];
            }

            const flattenedResult = flattenTree(rootNode);
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

                            const nodeDataFromScope = nodesFlattened[context.dataIndex];

                            console.log(`Processing node at index ${context.dataIndex}:`, nodeDataFromScope);
                            if(nodeDataFromScope.root) {
                                return 'red'; // Colore rosso per la radice
                            }

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
        error: function (xhr, status, error) {
            $('#syntaxTreeSpinner').hide();
            console.error('Error:', error, 'Status:', status, 'Response:', xhr.responseText);
            let errorMessage = "An unexpected error occurred.";

            if (status === 'timeout') {
                errorMessage = "API call failed or timed out.";
            } else if (status === 'error') {
                errorMessage = "API call failed. Check network connection.";
            }

            showAlert(errorMessage, "danger");
        }
    })
}